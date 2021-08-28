package ku.cs.service;

import ku.cs.models.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataSource {
    private AccountList accounts;
    private ProductList products;
    private ReviewList reviews;
    private ReportList reports;
    private String directoryPath;
    private Collection<String> categories = new TreeSet<>();

    public DataSource(String directoryPath){
        this.directoryPath = directoryPath;
    }

    public static String[] getLines(String filePath) throws IOException {
        String [] lines = new String[1000];
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        int i = 0;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            lines[i++] = line;
        }
        reader.close();
        return Arrays.copyOf(lines, i);
    }

    public static String[] getLinesWithHeader(String filePath) throws IOException {
        String [] lines = new String[1000];
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            lines[i++] = line;
        }
        reader.close();
        return Arrays.copyOf(lines, i);
    }

    public void parseAll() {
        parseAll(",", ",", ",");
    }

    public void parseAll(String sep1, String sep2, String sep3) {
        try {
            parseProduct(sep1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot parse product");
        }
        try {
            parseAccount(sep2);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot parse account");
        }
        try {
            parseReview(sep3);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot parse review");
        }
    }

    public void parseProduct(String sep) throws IOException {
        products = new ProductList();
        String[] lines = getLinesWithHeader(directoryPath + File.separator + "products.tsv");
        String[] header = lines[0].split(sep);
        lines = Arrays.copyOfRange(lines, 1, lines.length);
        for (String line : lines) {
            String[] entry = line.split(sep);
            int entry_len = entry.length;

            //name,picturePath,details,price,stock,id,rating,review,rolloutDate
            String name = entry[0];
            String id = entry[1];
            double price = Double.parseDouble(entry[2]);
            Store store = new Store(entry[3]);
            int stock = Integer.parseInt(entry[4]);
            String details = entry[5];
            double rating = Double.parseDouble(entry[6]);
            int review = Integer.parseInt(entry[7]);
            String picturePath = entry[8];
            String rolloutDate = entry[9];

            Product newProduct =
                    new Product(name, picturePath, details,
                            price, stock, id, rating, review, rolloutDate, store);
            for (int idx = 10; idx < entry_len; idx++) {
                String[] col = entry[idx].split(":");
                newProduct.addSubCategory(col[0], col[1], col[2]);
                products.addCategory(col[0]+":"+col[1]);
            }
            products.addProduct(newProduct);
        }
    }

    public void parseReview(String sep) throws IOException {
        reviews = new ReviewList();
        String [] lines = getLines(directoryPath + File.separator + "reviews.csv");
        for(String line: lines){
            // productId,title,detail,rating,reviewerUsername
            String [] entry = line.split(sep);
            String productId = entry[0];
            String title = entry[1];
            String detail = entry[2];
            int rating = Integer.parseInt(entry[3]);
            String reviewerUsername = entry[4];
            Product product = products.getProduct(productId);
            User reviewerUser = accounts.getUserAccount(reviewerUsername);
            reviews.addReview(new Review(title, detail, rating, reviewerUser, product));
        }
    }

    public void parseAccount(String sep) throws IOException{
        accounts = new AccountList();
        String [] lines = getLines(directoryPath + File.separator + "accounts.csv");
        for(String line: lines){
            String [] entries = line.split(sep);
            //String username, String role, String name, String password, String picturePath, String
            // loginDateTime, String isBanned, String loginAttempt, String hasStore, String store

            String username = entries[0];
            User.Role role = entries[1].equals("USER") ? User.Role.USER : User.Role.ADMIN;
            String name = entries[2];
            String password = entries[3];
            String pictureName = entries[4];

            LocalDateTime localDateTime =
                    entries[5].equals("null") ? null : LocalDateTime.parse(entries[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            boolean isBanned = entries[6].toLowerCase(Locale.ROOT).equals("true");
            int loginAttempt = Integer.parseInt(entries[7]);
            boolean hasStore = entries[8].toLowerCase(Locale.ROOT).equals("true");
            Store store = entries[9].equals("null") ? null : new Store(entries[9],username);

            User newUser = new User(username, role, name, password, pictureName, localDateTime, isBanned, loginAttempt, hasStore, store);
            accounts.addAccount(newUser);
        }
    }

    public void parseReport(String sep) throws IOException{
        if(accounts.toList() == null)
        {
            parseAccount(sep);
        }
        reports = new ReportList();
        String [] lines = getLines(directoryPath + File.separator + "reports.csv");
        for(String line: lines){
            String[] entries = line.split(sep);
            // String reportType,User suspectedPerson,User reporter,LocalDateTime reportDateTime,Review review,Product product,String detail

            Report.ReportType reportType = Report.ReportType.HARASSMENT;
            User suspectedPerson = accounts.getUserAccount(entries[1]);
            User reporter = accounts.getUserAccount(entries[2]);

            LocalDateTime localDateTime =
                    entries[3].equals("null") ? null : LocalDateTime.parse(entries[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            Review review = entries[4].equals("null") ? null : reviews.getReviewByID(entries[4]);
            Product product = entries[5].equals("null") ? null : products.getProduct(entries[5]);
            String detail = entries[6];
            Report newReport = new Report(reportType,suspectedPerson,reporter,localDateTime,review,product,detail);
            reports.addReport(newReport);
        }
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public AccountList getAccounts() {
        return accounts;
    }

    public ProductList getProducts() {
        return products;
    }

    public ReviewList getReviews() {
        return reviews;
    }

    public ReportList getReports() {
        return reports;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void saveReview(){
        save(reviews.toCsv(), "reviews.csv");
    }

    public void saveAccount(){
        save(accounts.toCsv(), "accounts.csv");
    }

    public void saveProduct(){
        save(products.toTsv(), "products.tsv");
    }

    public void save(String string, String fileName){
        File file = new File(
                directoryPath + File.separator + fileName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
