package ku.cs.models;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class Product implements Comparable<Product> {
    private String name;
    private String pictureName;
    private String details;
    private double price;
    private int stock;
    private final String id;
    private Store store;

    private double rating;
    private final LocalDateTime rolloutDate;
    private ArrayList<Category> categories;
    private ArrayList<Review> reviews;

    @Override
    public int compareTo(Product other) {
        return this.id.compareTo(other.id);
    }

    public Product(String name, String details, String id, LocalDateTime rolloutDate, Store store) {
        this.name = name;
        this.details = details;
        this.id = id;
        this.store = store;
        categories = new ArrayList<>();
        reviews = new ArrayList<>();
        this.rolloutDate = rolloutDate;
    }

    public Product(String name, String details, Store store){
        this(name, details, UUID.randomUUID().toString(), LocalDateTime.now(), store);
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return new File(System.getProperty("user.dir")
                + File.separator
                + "/images/product_images"
                + File.separator
                + pictureName).toURI().toString();
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
        return reviews.size();
    }

    public String getId(){
        return id;
    }

    public Store getStore() {
        return store;
    }

    public boolean setPrice(double price) {
        if (price >= 0) {
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean setStock(int stock){
        if (stock >= 0) {
            this.stock = stock;
            return true;
        }
        return false;
    }

    public void addReview(Review review){
        int temp = reviews.size();
        if (reviews.add(review)){
            rating = ((rating * temp) + review.getRating()) / reviews.size();
        }
    }

    public boolean setPictureName(String pictureName) {
        File file = new File("images"
                + File.separator + "product_images"
                + File.separator + pictureName);
        this.pictureName = pictureName;
        return file.exists();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isInStock(){
        return this.stock > 0;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public boolean sell(int amount){
        if (stock >= amount){
            stock -= amount;
            return true;
        }else{
            return false;
        }
    }

    public boolean containsCategory(String category){
        for (Category cat: categories) {
            if (cat.getName().equals(category))
                return true;
        }
        return false;
    }

    public String toCsv(int numCat){
        /*
         * "name,picture_path_1,details,price,stock,sub_category1," +
         * "sub_category2,sub_category3,sub_category4,sub_category5,sub_category6,store"
         */
        StringJoiner stringJoiner = new StringJoiner(",");
        int len = 0;
        for(Category cat: categories){
            for(String str: cat.toCSV()) {
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
                + "\"" + store.getNameStore() + "\"" + "," // TODO: add id to store
                + stock + ","
                + "\"" + details.replace("\"", "\"\"") + "\"" + ","
                + rating + ","
                + getReview() + ","
                + pictureName + ","
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

    public void setDetails(String details) {
        this.details = details;
    }
}
