package ku.cs.service;

import com.jrj.csv.CsvDocument;
import com.jrj.csv.CsvReader;
import ku.cs.models.*;
import ku.cs.models.Admin;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.models.CategoryList;
import ku.cs.models.ProductReport;
import ku.cs.models.ReportList;
import ku.cs.models.ReviewReport;
import ku.cs.models.coupon.CouponList;
import ku.cs.models.utils.Observer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataSource {
    private final String ACCOUNT_FILE_NAME  = "accounts.csv";
    private final String PRODUCT_FILE_NAME  = "products.csv";
    private final String ORDER_FILE_NAME    = "orders.csv";
    private final String REVIEW_FILE_NAME   = "reviews.csv";
    private final String STORE_FILE_NAME    = "stores.csv";
    private final String REPORT_FILE_NAME   = "reports.csv";
    private final String CATEGORY_FILE_NAME = "categories.csv";
    private final String COUPON_FILE_NAME = "coupons.csv";

    private final UserList userList;
    private final ProductList products;
    private final ReviewList reviews;
    private final ReportList reports;
    private String directoryPath;
    private final CategoryList categories;
    private final StoreList stores;
    private final OrderList orders;
    private final CouponList coupons;

    public DataSource() {
        this("data");
    }

    public DataSource(String directoryPath){
        this.directoryPath = directoryPath;
        userList = new UserList();
        products = new ProductList();
        reviews = new ReviewList();
        reports = new ReportList();
        categories = new CategoryList();
        stores = new StoreList();
        orders = new OrderList();
        coupons = new CouponList();

        coupons.addObserver(this::saveCoupon);
    }

    private void initFile(String filename) {
        File file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directoryPath + File.separator + filename;
        file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseProduct() {
        parseAccount();
        parseCategory();
        parseStore();

        initFile(PRODUCT_FILE_NAME);

        try {
            InputStream inputStream = new FileInputStream(directoryPath + File.separator + PRODUCT_FILE_NAME);
            CsvReader reader = new CsvReader(inputStream);
            CsvDocument doc = reader.parse();
            for (int i = 1; i < doc.size(); i++) {
                List<String> nextLine = doc.getRow(i);
                int entry_len = nextLine.size();
                String name = nextLine.get(0);
                String id = nextLine.get(1);

                if (products.containsId(id)) continue;

                double price = Double.parseDouble(nextLine.get(2));
                Store store = stores.findStoreByName(nextLine.get(3));
                int stock = Integer.parseInt(nextLine.get(4));
                String details = nextLine.get(5);
                String picturePath = nextLine.get(8);
                LocalDateTime rolloutDate = nextLine.get(9).equals("null") ? null : LocalDateTime.parse(nextLine.get(9), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Product newProduct =
                        new Product(name, details, id, rolloutDate, store);

                if (!newProduct.setPictureName(picturePath))
                    throw new NullPointerException("no picture found " + picturePath);
                if (!newProduct.setPrice(price))
                    throw new NumberFormatException("price number " + price);
                if (!newProduct.setStock(stock))
                    throw new NumberFormatException("stock number");
                if (store == null)
                    throw new NullPointerException("no store found");

                for (int idx = 10; idx < entry_len; idx++) {
                    String[] col = nextLine.get(idx).split(":");
                    if (col.length == 3) {
                        newProduct.addSubCategory(col[0], col[1], col[2]);
                        addCategory(col);
                    }
                }

                products.addProduct(newProduct);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveProduct();
    }

    private void addCategory(String[] entry) {
        if (categories.containsKey(entry[0])) {
            categories.addSubCategory(entry[0], entry[1]);
        } else {
            categories.addCategory(entry[0]);
        }
    }

    public void parseReview() {
        parseProduct();
        initFile(REVIEW_FILE_NAME);

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + REVIEW_FILE_NAME);
            CsvDocument doc = reader.parse();
            for (int i = 1; i < doc.size(); i++) {
                List<String> nextLine = doc.getRow(i);
                String id = nextLine.get(0);
                String productId = nextLine.get(1);
                String title = nextLine.get(2);
                String detail = nextLine.get(3);
                int rating = Integer.parseInt(nextLine.get(4));
                String reviewerUsername = nextLine.get(5);
                User reviewerUser = userList.getUser(reviewerUsername);

                Product product = products.getProduct(productId);

                if (product == null)
                    throw new NullPointerException("Product " + productId + " is null");

                Review review = new Review(id ,title, detail, reviewerUser);

                if (!review.setRating(rating))
                    continue;

                product.addReview(review);
                reviews.addReview(review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseAccount() {
        initFile(ACCOUNT_FILE_NAME);

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + ACCOUNT_FILE_NAME);
            CsvDocument doc = reader.parse();
            for (int i = 1; i < doc.size(); i++) {
                List<String> entry = doc.getRow(i);
                String username = entry.get(0);
                User.Role role = entry.get(1).equalsIgnoreCase("USER") ? User.Role.USER : User.Role.ADMIN;
                String name = entry.get(2);
                String password = entry.get(3);
                String pictureName = entry.get(4).equals("null") ? null : entry.get(4);

                LocalDateTime localDateTime =
                        entry.get(5).equals("null") ? null : LocalDateTime.parse(entry.get(5), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                boolean isBanned = entry.get(6).toLowerCase(Locale.ROOT).equals("true");
                int loginAttempt = Integer.parseInt(entry.get(7));

                User newUser;
                if(User.Role.USER == role)
                    newUser = new User(username, role, name, password, pictureName, localDateTime, isBanned, loginAttempt);
                else
                    newUser = new Admin(username, role, name, password, pictureName, localDateTime, isBanned, loginAttempt);
                userList.addUser(newUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseReport() {
        parseProduct();
        parseReview();
        initFile(REPORT_FILE_NAME);

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + REPORT_FILE_NAME);
            CsvDocument doc = reader.parse();
            for (int i = 1; i < doc.size(); i++){
                List<String> entry = doc.getRow(i);
                String id = entry.get(0);
                String reportType = entry.get(1).equalsIgnoreCase("null") ? null : entry.get(1);
                LocalDateTime localDateTime = entry.get(3).equalsIgnoreCase("null") ? null :
                                LocalDateTime.parse(entry.get(3), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Review review = entry.get(4).equalsIgnoreCase("null") ? null : reviews.getReviewByID(entry.get(4));
                Product product = entry.get(5).equalsIgnoreCase("null") ? null : products.getProduct(entry.get(5));

                String detail = entry.get(6);

                if (review != null){
                    reports.addReport(new ReviewReport(id, reportType, review, localDateTime, detail));
                } else if (product != null){
                    reports.addReport(new ProductReport(id, reportType, product, localDateTime, detail));
                } else
                    throw new NullPointerException("Both review and report are null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
              
    public void parseStore(){
        parseAccount();
        initFile(STORE_FILE_NAME);

        try{
            CsvReader reader = new CsvReader(directoryPath + File.separator + STORE_FILE_NAME);
            CsvDocument doc = reader.parse();
            String [] entry;
            for (int i = 1; i < doc.size(); i++){
                entry = doc.getRow(i).toArray(new String[0]);
                User owner = userList.getUser(entry[0]);

                if (owner == null)
                    throw new NullPointerException("Store Owner not found");

                String nameStore = entry[1];
                int stockLower = Integer.parseInt(entry[2]);
                owner.createStore(nameStore);
                owner.getStore().setStockLowerBound(stockLower);
                stores.addStore(owner.getStore());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void parseCategory() {
        initFile(CATEGORY_FILE_NAME);

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + CATEGORY_FILE_NAME);
            CsvDocument doc = reader.parse();
            String[] entry;
            for (int i = 1; i < doc.size(); i++){
                entry = doc.getRow(i).toArray(new String[0]);
                addCategory(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseOrder(){
        initFile(ORDER_FILE_NAME);

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + ORDER_FILE_NAME);
            CsvDocument doc = reader.parse();
            String[] entry;
            for (int i = 1; i<doc.size(); i++){
                entry = doc.getRow(i).toArray(new String[0]);
                String id = entry[0];
                Product product = products.getProduct(entry[1]);
                int amount = Integer.parseInt(entry[2]);
                boolean status = Boolean.parseBoolean(entry[3]);
                String tracking = entry[4];
                User buyer = userList.getUser(entry[5]);
                if(buyer == null || product == null) continue;
                LocalDateTime localDateTime = LocalDateTime.parse(entry[6], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                orders.addOrder(new Order(id, product, amount, status, tracking, buyer, localDateTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseCoupon(){
        initFile(COUPON_FILE_NAME);
        parseStore();

        try {
            CsvReader reader = new CsvReader(directoryPath + File.separator + COUPON_FILE_NAME);
            CsvDocument doc = reader.parse();
            String[] entry;
            for (int i = 1; i<doc.size(); i++){
                entry = doc.getRow(i).toArray(new String[0]);
                String code = entry[0];
                Store owner = stores.findStoreByName(entry[1]);
                boolean status = entry[2].equals("true");
                Double percentDiscount = entry[3].equals("null") ? null : Double.parseDouble(entry[3]);
                Double discount = entry[4].equals("null") ? null : Double.parseDouble(entry[4]);
                Integer minimumQuantity = entry[5].equals("null") ? null : Integer.parseInt(entry[5]);
                Double minimumValue = entry[6].equals("null") ? null : Double.parseDouble(entry[6]);
                if (!coupons.checkCouponCode(code))
                    coupons.addCoupon(code,owner,status,minimumValue,minimumQuantity,discount,percentDiscount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDirectoryPath(String directoryPath) { this.directoryPath = directoryPath; }

    public UserList getUserList()       { return userList; }
    public ProductList getProducts()    { return products; }
    public ReviewList getReviews()      { return reviews; }
    public CategoryList getCategories() { return categories; }
    public OrderList getOrders()        { return orders; }
    public ReportList getReports()      { return reports; }
    public StoreList getStores()        { return stores; }
    public CouponList getCoupons()      { return coupons; }
    public String getDirectoryPath()    { return directoryPath; }

    public void save(String string, String fileName){
        File file = new File(
                directoryPath + File.separator + fileName);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveReport()   { save(reports.toCsv(), REPORT_FILE_NAME); }
    public void saveReview()   { save(reviews.toCsv(), REVIEW_FILE_NAME); }
    public void saveAccount()  { save(userList.toCsv(), ACCOUNT_FILE_NAME); }
    public void saveProduct()  { save(products.toCsv(), PRODUCT_FILE_NAME); }
    public void saveOrder()    { save(orders.toCsv(), ORDER_FILE_NAME); }
    public void saveStore()    { save(stores.toCsv(), STORE_FILE_NAME); }
    public void saveCategory() { save(categories.toCsv(), CATEGORY_FILE_NAME); }
    public void saveCoupon()   { save(coupons.toCsv(), COUPON_FILE_NAME); }
}
