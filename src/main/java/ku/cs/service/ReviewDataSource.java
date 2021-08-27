package ku.cs.service;

import ku.cs.models.*;

import java.io.IOException;
import java.io.InvalidObjectException;

public class ReviewDataSource {
    private String filePath;
    private String header;
    private ProductList productList;
    private ReviewList reviewList;
    private AccountList accountList;

    public ReviewDataSource(String filePath){
        this.filePath = filePath;
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public void setAccounts(AccountList accountList){
        this.accountList = accountList;
    }

    public void parse(String sep, ProductList productList, AccountList accountList) throws IOException {
        if (productList == null) {
            throw new InvalidObjectException("haven't set productList yet");
        }
        ReviewList reviewList = new ReviewList();
        String [] lines = CsvReader.getLines("data/reviews.csv");
        for(String line: lines){
            // productId,title,detail,rating,reviewerUsername
            String [] entry = line.split(sep);
            String productId = entry[0];
            String title = entry[1];
            String detail = entry[2];
            int rating = Integer.parseInt(entry[3]);
            String reviewerUsername = entry[4];
            Product product = productList.getProduct(productId);
            User reviewerUser = accountList.getUserAccount(reviewerUsername);
            reviewList.addReview(new Review(title, detail, rating, reviewerUser, product));
        }
         this.reviewList = reviewList;
    }

    public void parse(ProductList productList, AccountList accountList) throws IOException{
        parse(",", productList, accountList);
    }

    public ReviewList getReviewList() {
        return reviewList;
    }
}
