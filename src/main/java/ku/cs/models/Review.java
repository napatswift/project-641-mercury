package ku.cs.models;

public class Review {
    private String title;
    private String id;
    private String detail;
    private int rating;
    private User author;
    private String productId;

    public Review(String id, String title, String detail, User user, String productId) {
        this.title = title;
        this.detail = detail;
        this.author = user;
        this.productId = productId;
        this.id = id;
    }

    public String getId() {
        return id;
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
