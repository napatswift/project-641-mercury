package ku.cs.models;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Product implements Comparable<Product>{
    private String name;
    private String picturePath;
    private String details;
    private double price;
    private int stock;
    private final String id;
    private Store store;

    private double rating;
    private int review;
    private final LocalDateTime rolloutDate;
    private ArrayList<Category> categories;

    @Override
    public int compareTo(Product other) {
        return this.id.compareTo(other.id);
    }

    public Product(String name, String picturePath, String details,
                   double price, int stock, String id, double rating,
                   int review, String rolloutDate, Store store) {
        this.name = name;
        this.picturePath = picturePath;
        this.details = details;
        setPrice(price);
        setStock(stock);
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.store = store;
        categories = new ArrayList<>();
        this.rolloutDate = rolloutDate.equals("null") ?
                null : LocalDateTime.parse(rolloutDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "/images/product_images"
                + File.separator
                + picturePath).toURI().toString();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public Category getCategory(int index) {
        return categories.get(index);
    }

    public Category getCategory() {
        return getCategory(0);
    }

    public LocalDateTime getRolloutDate() {
        return rolloutDate;
    }

    public String getDetails() {
        return details;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public double getRating(){
        return rating;
    }

    public int getReview() {
        return review;
    }

    public String getId(){
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setPrice(double price) {
        if(price >= 0){
            this.price = price;
        }
    }

    public void setStock(int stock){
        if(stock >= 0){
            this.stock = stock;
        }
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInStock(){
        if (this.stock > 0){
            return true;
        } else{
            return false;
        }
    }

    public boolean sell(int amount){
        if (stock >= amount){
            stock -= amount;
            return true;
        }else{
            return false;
        }
    }

    public String toCsv(int numCat){
        /*
         * "name,picture_path_1,details,price,stock,sub_category1," +
         * "sub_category2,sub_category3,sub_category4,sub_category5,sub_category6,store"
         */
        StringJoiner stringJoiner = new StringJoiner(",");
        int len = 0;
        for(Category cat: categories){
            for(String str: cat.toCsv()) {
                stringJoiner.add(str);
                len++;
            }
        }
        for (int i = len; i < numCat; i++) {
            stringJoiner.add("");
        }

        return "\"" + name.replace("\"", "\"\"") + "\"" + ","
                + id + ","
                + price + ","
                + "\"" + store.getName() + "\"" + "," // TODO: add id to store
                + stock + ","
                + "\"" + details.replace("\"", "\"\"") + "\"" + ","
                + rating + ","
                + review + ","
                + picturePath + ","
                + rolloutDate.toString() + ","
                + stringJoiner;
    }

    public void addSubCategory(String categoryName, String subCategoryName, String value){
        for(Category category: categories){
            if (category.getName().equals(categoryName)){
                category.addSubCategory(new SubCategory(subCategoryName, value));
                return;
            }
        }
        Category newCategory = new Category(categoryName);
        newCategory.addSubCategory(new SubCategory(subCategoryName, value));
        categories.add(newCategory);
    }
}
