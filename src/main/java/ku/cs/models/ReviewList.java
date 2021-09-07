package ku.cs.models;

import java.util.*;

public class ReviewList implements Iterable<Review> {
    private final ArrayList<Review> reviews;
    private final Collection<String> ids;
    private Review reportingReview;

    public ReviewList(){
        reviews = new ArrayList<>();
        ids = new TreeSet<>();
    }

    public void addReview(Review review){
        reviews.add(review);
        ids.add(review.getId());
    }

    public boolean addReview(String title, String detail,
                          int rating, User user, Product product){
        title = title.trim();
        detail = detail.trim();
        if (title.equals("") || detail.equals("") ||
                rating < 0 || rating > 5 ||
                user == null || product == null){
            return false;
        }
        String id = UUID.randomUUID().toString();
        addReview(new Review(id, title, detail, rating, user, product));
        return true;
    }

    public ArrayList<Review> getProductReviewList(String idProduct) {
        ArrayList<Review> gettingReviews = new ArrayList<>();
        for (Review review: reviews){
            if(review.getProductId().equals(idProduct))
                gettingReviews.add(review);
        }
        return gettingReviews;
    }

    public Review getReviewByID(String id){
        for (Review review: reviews){
            if(review.getId().equals(id)){
                return review;
            }
        }
        return null;
    }

    public void setReportingReview(Review reportingReview) {
        if (reportingReview != null)
            this.reportingReview = reportingReview;
    }

    public void resetReportingReview(){
        this.reportingReview = null;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("id,productId,title,detail,rating,reviewerUsername");
        stringBuilder.append("\n");
        for(Review review: reviews){
            stringBuilder.append(review.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int size(){
        return reviews.size();
    }

    @Override
    public Iterator<Review> iterator() {
        return reviews.iterator();
    }
}
