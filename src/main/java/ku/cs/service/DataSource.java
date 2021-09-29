package ku.cs.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ku.cs.models.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataSource {
    private UserList userList;
    private ProductList products;
    private ReviewList reviews;
    private ReportList reports;
    private String directoryPath;
    private Map<String, ArrayList<String>> categories;
    private StoreList stores;
    private OrderList orders;

    public DataSource(String directoryPath){
        this.directoryPath = directoryPath;
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

    public void parseAll() {
        parseProduct();
        parseAccount();
        parseReview();
        parseOrder();
        parseStore();
        parseReport();
    }

    public void parseProduct() {
        products = new ProductList();
        CSVReader reader;
        if (userList == null)
            parseAccount();
        if (categories == null)
            parseCategory();
        if (reviews == null)
            parseReview();
        if (stores == null)
            parseStore();

        String FILE_NAME = "products.csv";
        initFile(FILE_NAME);

        try {
            reader = new CSVReader(
                    new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                int entry_len = nextLine.length;

                String name = nextLine[0];
                String id = nextLine[1];
                double price = Double.parseDouble(nextLine[2]);
                Store store = stores.findStoreByName(nextLine[3]);
                int stock = Integer.parseInt(nextLine[4]);
                String details = nextLine[5];
                String picturePath = nextLine[8];
                LocalDateTime rolloutDate = nextLine[9].equals("null") ? null : LocalDateTime.parse(nextLine[9], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Product newProduct =
                        new Product(name, details, id, rolloutDate, store);

                if (!newProduct.setPictureName(picturePath)
                        || !newProduct.setPrice(price)
                        || !newProduct.setStock(stock) || store == null)
                    continue;

                for (int idx = 10; idx < entry_len; idx++) {
                    String[] col = nextLine[idx].split(":");
                    if (col.length == 3) {
                        newProduct.addSubCategory(col[0], col[1], col[2]);
                        addCategory(col);
                    }
                }

                Iterator<Review> iterator = reviews.iterator(id);
                while (iterator.hasNext()){
                    newProduct.addReview(iterator.next());
                }
                products.addProduct(newProduct);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addCategory(String[] entry) {
        if (categories.containsKey(entry[0])) {
            ArrayList<String> list = categories.get(entry[0]);
            if (!list.contains(entry[1]))
                list.add(entry[1]);
        } else{
            ArrayList<String> list = new ArrayList<>();
            list.add(entry[1]);
            categories.put(entry[0], list);
        }
    }

    public void parseReview() {
        reviews = new ReviewList();
        String FILE_NAME = "reviews.csv";
        initFile(FILE_NAME);

        try {
            CSVReader reader = new CSVReader(
                    new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String [] entry;
            while ((entry = reader.readNext()) != null) {
                String id = entry[0];
                String productId = entry[1];
                String title = entry[2];
                String detail = entry[3];
                int rating = Integer.parseInt(entry[4]);
                String reviewerUsername = entry[5];
                User reviewerUser = userList.getUser(reviewerUsername);

                Review review = new Review(id ,title, detail, reviewerUser, productId);

                if (review.setRating(rating))
                    reviews.addReview(review);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void parseAccount() {
        userList = new UserList();

        String FILE_NAME = "accounts.csv";
        initFile(FILE_NAME);

        try {
            CSVReader reader = new CSVReader(new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String [] entry;
            while ((entry = reader.readNext()) != null) {
                String username = entry[0];
                User.Role role = entry[1].toUpperCase(Locale.ROOT).equals("USER") ? User.Role.USER : User.Role.ADMIN;
                String name = entry[2];
                String password = entry[3];
                String pictureName = entry[4];

                LocalDateTime localDateTime =
                        entry[5].equals("null") ? null : LocalDateTime.parse(entry[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                boolean isBanned = entry[6].toLowerCase(Locale.ROOT).equals("true");
                int loginAttempt = Integer.parseInt(entry[7]);

                User newUser = null;
                if(User.Role.USER == role)
                    newUser = new User(username, role, name, password, pictureName, localDateTime, isBanned, loginAttempt);
                else
                    newUser = new Admin(username, role, name, password, pictureName, localDateTime, isBanned, loginAttempt);
                userList.addUser(newUser);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void parseReport() {
        if(userList.toList() == null) parseAccount();

        if(products == null) parseProduct();

        if(reviews == null) parseReview();

        reports = new ReportList();

        String FILE_NAME = "reports.csv";
        initFile(FILE_NAME);

        try {
            CSVReader reader = new CSVReader(
                    new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String [] entry;
            while ((entry = reader.readNext()) != null) {
                String reportType = entry[0].toLowerCase().equals("null") ? null : entry[0];
                User suspectedPerson = userList.getUser(entry[1]);
                User reporter = userList.getUser(entry[2]);
                LocalDateTime localDateTime =
                        entry[2].equalsIgnoreCase("null") ? null :
                                LocalDateTime.parse(entry[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Review review = entry[3].equalsIgnoreCase("null") ? null : reviews.getReviewByID(entry[3]);
                Product product = entry[4].equalsIgnoreCase("null") ? null : products.getProduct(entry[4]);
                String detail = entry[5];
                if (review != null){
                    reports.addReport(new ReviewReport(reportType, review, localDateTime, detail));
                } else if (product != null){
                    reports.addReport(new ProductReport(reportType, product, localDateTime, detail));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
              
    public void parseStore(){
        if (userList == null) parseAccount();

        stores = new StoreList();

        String FILE_NAME = "store.csv";
        initFile(FILE_NAME);

        try{
            CSVReader reader = new CSVReader(new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String [] entry;
            while ((entry = reader.readNext()) != null){
                User owner = userList.getUser(entry[0]);
                String nameStore = entry[1];
                int stockLower = Integer.parseInt(entry[2]);
                owner.createStore(nameStore);
                owner.getStore().setStockLowerBound(stockLower);
                stores.addStore(owner.getStore());
            }
        }catch (IOException | CsvValidationException e){
            e.printStackTrace();
        }
    }

    public void parseCategory() {
        categories = new HashMap<>();

        String FILE_NAME = "categories.csv";
        initFile(FILE_NAME);

        try {
            CSVReader reader = new CSVReader(new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String[] entry;
            while ((entry = reader.readNext()) != null) {
                addCategory(entry);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void parseOrder(){
        orders = new OrderList();

        String FILE_NAME = "orders.csv";
        initFile(FILE_NAME);

        try {
            CSVReader reader = new CSVReader(new FileReader(directoryPath + File.separator + FILE_NAME));
            reader.readNext();
            String[] entry;
            while((entry = reader.readNext()) != null){
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

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public UserList getUserList() {
        return userList;
    }

    public ProductList getProducts() {
        return products;
    }

    public ReviewList getReviews() {
        return reviews;
    }

    public Set<String> getCategories() {
        return categories.keySet();
    }

    public OrderList getOrders(){return orders;}


    public ArrayList<String> getSubCategory(String key) {return categories.get(key);}
    public Map<String, ArrayList<String>> getMapCategories(){
        return categories;
    }

    public ReportList getReports() {
        return reports;
    }

    public StoreList getStores() { return stores; }

    public String getDirectoryPath() {
        return directoryPath;
    }

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

    public void saveReport() {
        save(reports.toCsv(), "reports.csv");
    }

    public void saveReview(){
        save(reviews.toCsv(), "reviews.csv");
    }

    public void saveAccount(){
        save(userList.toCsv(), "accounts.csv");
    }

    public void saveProduct(){
        int numCategory = 0;
        for(String key: categories.keySet())
            numCategory += categories.get(key).size();
        save(products.toCsv(numCategory), "products.csv");
    }

    public void saveOrder(){
        save(orders.toCsv(), "order.csv");
    }

    public void saveCategory(){
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("category,subcategory");
        for(String key: categories.keySet())
            for(String val: categories.get(key))
                stringJoiner.add(key + "," + val);
        save(stringJoiner.toString(), "categories.csv");
    }

    public void  saveStore(){
        save(stores.toCsv(),"store.csv");
    }

}
