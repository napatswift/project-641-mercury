package ku.cs.models;

import java.util.Collection;

public class Product {
    private String name;
    private String picturePath;
    private String details;
    private double price;
    private int stock;
    private Collection<SubCategory> subCategories;

    public Product(String name, String picturePath, String details, double price, int stock) {
        this.name = name;
        this.picturePath = picturePath;
        this.details = details;
        setPrice(price);
        setStock(stock);
    }

    public Product(String name, String picturePath, String details, double price, int stock, SubCategory[] subCategories){
        this(name, picturePath, details, price, stock);
        for(SubCategory subCat: subCategories){
            this.subCategories.add(subCat);
        }
    }

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
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

    public Collection<SubCategory> getSubCategories() {
        return subCategories;
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
}
