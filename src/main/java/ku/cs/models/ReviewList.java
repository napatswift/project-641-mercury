package ku.cs.models;

import java.util.*;

public class ReviewList implements Iterable<Review> {
    private final ArrayList<Review> reviews;
    private Review currReview;

    public ReviewList() {
        reviews = new ArrayList<>();
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public boolean addReview(String title, String detail, int rating, User user, Product product){
        title = title.trim();
        detail = detail.trim();
        if (title.equals("") || detail.equals("") ||
                rating <= 0 || rating > 5 ||
                user == null || product == null){
            return false;
        }
        String id = UUID.randomUUID().toString();
        Review newReview = new Review(id, title, detail, user, product.getId());

        if (!newReview.setRating(rating))
            return false;

        product.addReview(newReview);
        addReview(newReview);
        return true;
    }

    public void setCurrReview(Review currReview) {
        this.currReview = currReview;
    }

    public ArrayList<Review> getProductReviewList(String idProduct) {
        ArrayList<Review> gettingReviews = new ArrayList<>();
        for (Review review: reviews){
            if(review.getProductId().equals(idProduct))
                gettingReviews.add(review);
        }
        return gettingReviews;
    }

    public Review getCurrReview() {
        return currReview;
    }

    public Review getReviewByID(String id){
        for (Review review: reviews){
            if(review.getId().equals(id)){
                return review;
            }
        }
        return null;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("id,product_id,title,detail,rating,reviewer_username");
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

    public Iterator<Review> iterator(String productId){
        return reviews.stream().filter(r -> r.getProductId().equals(productId)).iterator();
    }
}
