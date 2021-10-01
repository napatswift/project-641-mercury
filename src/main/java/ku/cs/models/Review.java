package ku.cs.models;

public class Review {
    private final String title;
    private final String id;
    private final String detail;
    private int rating;
    private final User author;
    private final String productId;
    private Product product;

    public Review(String id, String title, String detail, User author, String productId) {
        this.title = title;
        this.detail = detail;
        this.author = author;
        this.productId = productId;
        this.id = id;
    }

    public String getId() { return id; }

    public boolean setProduct(Product product) {
        if (this.productId.equals(product.getId())) {
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

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getRating() {
        return rating;
    }

    public Product getProduct() {
        return product;
    }

    public String getReviewerUsername() {
        return author.getUsername();
    }
    
    public User getAuthor(){
        return author;
    }

    public String getProductId() {
        return productId;
    }

    // id,productId,title,detail,rating,reviewerUsername
    public String toCsv(){
        return id + ","
                + productId + ","
                + "\"" + title.replace("\"", "\"\"") + "\"" + ","
                + "\"" + detail.replace("\"", "\"\"") + "\"" + ","
                + rating + ","
                + author.getUsername();
    }
}
