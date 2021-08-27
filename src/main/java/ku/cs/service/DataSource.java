package ku.cs.service;

import ku.cs.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DataSource {
    private AccountList accounts;
    private ProductList products;
    private ReviewList reviews;
    private String directoryPath;

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
            double price = Double.parseDouble(entry[2]) / 100;
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
                String[] col = header[idx].split("-");
                newProduct.addSubCategory(col[0], col[1], entry[idx]);
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
            User newUser = new User(entries[0], entries[1] , entries[2],
                    entries[3], entries[4], entries[5], entries[6], entries[7], entries[8], entries[9]);
            accounts.addAccount(newUser);
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

    public String getDirectoryPath() {
        return directoryPath;
    }
}
