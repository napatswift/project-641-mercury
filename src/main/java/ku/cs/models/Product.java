package ku.cs.models;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.TreeSet;

public class Product implements Comparable<Product>{
    private String name;
    private String picturePath;
    private String details;
    private double price;
    private int stock;
    private final String id;

    private double rating;
    private int review;
    private final LocalDateTime rolloutDate;

    @Override
    public int compareTo(Product other) {
        return this.id.compareTo(other.id);
    }

    public Product(String name, String picturePath, String details, double price, int stock, String id, double rating, int review, String rolloutDate) {
        this.name = name;
        this.picturePath = picturePath;
        this.details = details;
        setPrice(price);
        setStock(stock);
        this.id = id;
        this.rating = rating;
        this.review = review;

        this.rolloutDate = rolloutDate.equals("null") ? null : LocalDateTime.parse(rolloutDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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

    public String toCsv(){
        /*
         * "name,picture_path_1,details,price,stock,sub_category1," +
         * "sub_category2,sub_category3,sub_category4,sub_category5,sub_category6,store"
         */

        return name + ","
                + picturePath + ","
                + price + ","
                + stock;

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("picturePath='" + picturePath + "'")
                .add("details='" + details + "'")
                .add("price=" + price)
                .add("stock=" + stock)
                .toString();
    }
}
