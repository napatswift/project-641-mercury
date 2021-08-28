package ku.cs.models;

import java.util.ArrayList;
import java.util.Iterator;

public class ReviewList implements Iterable<Review> {
    private final ArrayList<Review> reviews;
    private Review reportingReview;

    public ReviewList(){
        reviews = new ArrayList<>();
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public void addReview(String title, String detail,
                          int rating, User user, Product product){
        title = title.trim();
        detail = detail.trim();
        if (title.equals("") || detail.equals("") ||
                rating < 0 || rating > 5 ||
                user == null || product == null){
            return;
        }
        addReview(new Review(title, detail, rating, user, product));
    }

    public ArrayList<Review> getReview(String id) {
        ArrayList<Review> gettingReviews = new ArrayList<>();
        for (Review review: reviews){
            if(review.getProductId().equals(id))
                gettingReviews.add(review);
        }
        return gettingReviews;
    }

    public void setReportingReview(Review reportingReview) {
        if (reportingReview != null)
            this.reportingReview = reportingReview;
    }

    public void resetReportingReview(){
        this.reportingReview = null;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("productId,title,detail,rating,reviewerUsername");
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
