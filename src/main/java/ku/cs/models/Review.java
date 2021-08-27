package ku.cs.models;

public class Review {
    private String title;
    private String id;
    private String detail;
    private int rating;
    private User author;
    private String productId;

    public Review(String title, String detail, int rating, User user, Product product) {
        this.title = title;
        this.detail = detail;
        setRating(rating);
        this.author = user;
        this.productId = product.getId();
        this.id = productId + product.getReview();
    }

    public String getId() {
        return id;
    }

    public void setRating(int rating){
        if (rating >= 0 && rating <= 5)
            this.rating = rating;
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

    public String getReviewerUsername() {
        return author.getUsername();
    }
    
    public User getAuthor(){
        return author;
    }

    public String getProductId() {
        return productId;
    }

    // productId,title,detail,rating,reviewerUsername
    public String toCsv(){
        return productId + ","
                + title + ","
                + detail + ","
                + rating + ","
                + author.getUsername();
    }
}
