package ku.cs.models;

import ku.cs.models.io.CSVFile;

public class Review implements Comparable<Review>, CSVFile {
    private final String title;
    private final String id;
    private final String detail;
    private int rating;
    private final User author;
    private Product product;

    public Review(String id, String title, String detail, User author) {
        this.title = title;
        this.detail = detail;
        this.author = author;
        this.id = id;
    }

    public boolean setProduct(Product product) {
        if (product != null) {
            this.product = product;
            return true;
        }
        return false;
    }

    public boolean setRating(int rating){
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
            return true;
        }
        return false;
    }

    public String getId()        { return id; }
    public String getTitle()     { return title; }
    public String getDetail()    { return detail; }
    public int getRating()       { return rating; }
    public Product getProduct()  { return product; }
    public User getAuthor()      { return author; }
    public String getProductId() { return product.getId(); }

    @Override
    public String toCSV(){
        return id + ","
                + product.getId() + ","
                + "\"" + title.replace("\"", "\"\"") + "\"" + ","
                + "\"" + detail.replace("\"", "\"\"") + "\"" + ","
                + rating + ","
                + author.getUsername();
    }

    @Override
    public int compareTo(Review o) {
        return this.id.compareTo(o.id);
    }
}
