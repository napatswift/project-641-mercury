package ku.cs.models;

public class Review {
    private String title;
    private String id;
    private String detail;
    private int rating;
    private String reviewerUsername;
    private String productId;

    public Review(String title, String detail, int rating, String reviewerUsername, Product product) {
        this.title = title;
        this.detail = detail;
        this.rating = rating;
        this.reviewerUsername = reviewerUsername;
        this.productId = product.getId();
        this.id = productId + product.getReview();
    }

    public String getId() {
        return id;
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
        return reviewerUsername;
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
                + reviewerUsername;
    }
}
