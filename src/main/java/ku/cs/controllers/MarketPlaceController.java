package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MarketPlaceController {
    @FXML
    AnchorPane bodyAP;
    @FXML
    ScrollPane productListSP;
    @FXML
    TabPane productTP;
    @FXML
    Label productNameLabel, productPriceLabel,
            productDetailLabel,
            categoryBreadcrumbsLabel, productNameBreadcrumbsLabel,
            amountInStockLabel,
            storeNameLabel,
            ratingSubmissionLabel;
    @FXML
    SVGPath inStockStatusSvg,
            star1, star2, star3, star4, star5;
    @FXML
    ImageView selectedProductIV;
    @FXML
    FlowPane productFlowPane;
    @FXML
    Button seeMoreBtn,
            starBtn1, starBtn2, starBtn3, starBtn4, starBtn5,
            submitReviewBtn;
    @FXML
    MenuButton sortingMB;
    @FXML
    TextField upperBoundTF, lowerBoundTF, amountTF,
            reviewTitleTF;
    @FXML
    TextArea detailReviewTA;
    @FXML
    HBox starsHBox,
        reviewRatingPanelStarHBox;
    @FXML
    VBox reviewVBox, categoriesVBox;

    private boolean bodyToggle = false;
    private int productIndex = -1;
    private ComponentBuilder componentBuilder = new ComponentBuilder();
    private double upperBoundParsed = Double.MAX_VALUE, lowerBoundParsed = 0;
    private int newReviewRating = -1;
    private User currUser = new User("napat", "some");

    private ProductList productList;
    private ReviewList reviewList;
    DataSource dataSource;

    @FXML
    private void sortProductBy(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        if (menuItem.getId().equals("lowestPrice")){
            sortingMB.setText("SORT BY : LOWEST PRICE");
            productList.sort(ProductList.SortType.BY_LOWEST);
        } else if (menuItem.getId().equals("highestPrice")){
            sortingMB.setText("SORT BY : HIGHEST PRICE");
            productList.sort(ProductList.SortType.BY_HIGHEST);
        } else{
            sortingMB.setText("SORT BY : MOST RECENT");
            productList.sort(ProductList.SortType.BY_ROLLOUT_DATE);
        }
        productFlowPane.getChildren().clear();
        int temp = productIndex;
        productIndex = 0;
        populateProduct(temp);
    }

    private void filterProduct(){
        if (lowerBoundTF.getText().equals("")) {
            lowerBoundParsed = 0;
        } else{
            try {
                lowerBoundParsed = Double.parseDouble(lowerBoundTF.getText());
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
        }
        if (upperBoundTF.getText().equals("")) {
            upperBoundParsed = Double.MAX_VALUE;
        } else{
            try {
                upperBoundParsed = Double.parseDouble(upperBoundTF.getText());
            } catch (NumberFormatException e){
                System.err.println(e);
            }
        }
    }

    @FXML
    private void handleFilter(KeyEvent ke){
        if( ke.getCode() == KeyCode.ENTER){
            filterProduct();
        }
        productFlowPane.getChildren().clear();
        int temp = productIndex;
        productIndex = 0;
        populateProduct(temp);
    }

    // product page builder
    private void buildProductPage(){
        resetStar();
        // reset amount to 1
        amountTF.setText("1");
        // clear boxes
        categoriesVBox.getChildren().clear();

        populateReview();

        // set product information
        productNameLabel.setText(productList.getSelectedProduct().getName());
        productPriceLabel.setText("$"+productList.getSelectedProduct().getPrice());
        productDetailLabel.setText(productList.getSelectedProduct().getDetails());
        selectedProductIV.setImage(new Image(productList.getSelectedProduct().getPicturePath()));

        // set store name;
        storeNameLabel.setText(productList.getSelectedProduct().getStore().getName().toUpperCase(Locale.ROOT));

        // set bread crumbs info
        categoryBreadcrumbsLabel.setText(productList.getSelectedProduct().getCategory().getName());
        productNameBreadcrumbsLabel.setText(productList.getSelectedProduct().getName());

        // handling in stock label and icon
        amountInStockLabel.setText("" + productList.getSelectedProduct().getStock() + " in stock");
        if (productList.getSelectedProduct().getStock() < 5){
            inStockStatusSvg.setContent(
                    "M12,4.6L15,9h-2.6l8.6,8.6l2-7.3l0-0.3c0-0.5-0.5-1-1-1h-4.8l-4.4-6.6C12.6," +
                            "2.2,12.3,2,12,2s-0.6,0.1-0.8,0.4L9,5.6L10.4,7L12,4.6z M9.9,9L9.9," +
                            "9L9.4,8.5l0,0L8,7.1l0,0L3.4,2.5L2.6,1.8L1.9,1L0.6,2.3L5,6.7l2,2L6.8," +
                            "9H2c-0.5,0-1,0.5-1,1c0,0.1,0,0.2,0,0.3l2.5,9.3c0.2,0.8,1,1.5,1.9," +
                            "1.5h13c0.2,0,0.5,0,0.7-0.1l2.9,2.9l1.3-1.3l-2.9-2.9c0,0,0,0,0,0L9.9,9z");
        } else {
            inStockStatusSvg.setContent("" +
                    "M22,9H17.21L12.83,2.44A1,1,0,0,0,12,2a1,1,0,0,0-.83.43L6.79," +
                    "9H2a1,1,0,0,0-1,1,.84.84,0,0,0,0,.27l2.54,9.27A2,2,0,0,0,5.5," +
                    "21h13a2,2,0,0,0,1.93-1.46L23,10.27,23,10A1,1,0,0,0,22,9ZM12,4.6," +
                    "15,9H9Zm-.21,13.9L8.19,15l1.4-1.4,2.2,2.1L16,11.5l1.4,1.4Z");
        }

        // handling category
        for(Category category: productList.getSelectedProduct().getCategories())
            categoriesVBox.getChildren().add(componentBuilder.categoryPane(category));
    }

    private void resetStar(){
        star1.setStyle("-fx-fill: primary-overlay");
        star2.setStyle("-fx-fill: primary-overlay");
        star3.setStyle("-fx-fill: primary-overlay");
        star4.setStyle("-fx-fill: primary-overlay");
        star5.setStyle("-fx-fill: primary-overlay");
    }

    private void resetReviewForm(){
        resetStar();
        reviewTitleTF.setText("");
        detailReviewTA.setText("");
    }

    @FXML
    private void selectRatingStar(ActionEvent e){
        Button star = (Button) e.getSource();
        resetStar();
        int rating = Integer.parseInt(star.getId().toCharArray()[7] + "");
        switch (rating){
            case (5):
                star5.setStyle("-fx-fill: primary-color");
            case (4):
                star4.setStyle("-fx-fill: primary-color");
            case (3):
                star3.setStyle("-fx-fill: primary-color");
            case (2):
                star2.setStyle("-fx-fill: primary-color");
            case (1):
                star1.setStyle("-fx-fill: primary-color");
        }
        ratingSubmissionLabel.setText(rating + "/5");
        newReviewRating = rating;
    }

    private void populateReview(){
        reviewRatingPanelStarHBox.getChildren().clear();

        reviewVBox.getChildren().clear();
        int sumOfRating = 0;
        String id = productList.getSelectedProduct().getId();
        ArrayList<Review> selectedProductReview = reviewList.getProductReviewList(id);
        for(Review review: selectedProductReview){
            reviewVBox.getChildren().add(componentBuilder.reviewCard(review));
            sumOfRating += review.getRating();
        }

        if (selectedProductReview.size() == 0){
            Label noReviewLabel = new Label("No review yet! You'll first!");
            noReviewLabel.setPadding(new Insets(10));
            reviewVBox.getChildren().add(noReviewLabel);
        }

        double rating = selectedProductReview.size() == 0 ? 0 : (double) sumOfRating / selectedProductReview.size();

        ComponentBuilder.starsRating(reviewRatingPanelStarHBox, rating);
        reviewRatingPanelStarHBox.getChildren().add(new Label(rating + "/5 (" + selectedProductReview.size() + ")"));
        productList.getSelectedProduct().setReview(selectedProductReview.size());
        productList.getSelectedProduct().setRating(rating);

        // handling product rating
        starsHBox.getChildren().clear();
        ComponentBuilder.starsRating(starsHBox, productList.getSelectedProduct().getRating());
        starsHBox.getChildren().add(new Label(productList.getSelectedProduct().getRating()+"/5 (" + productList.getSelectedProduct().getReview() + ")"));
    }

    // handler
    @FXML
    private void handleAmountBtn(ActionEvent event){
        Button button = (Button) event.getSource();
        String amountStr = amountTF.getText();
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e){
            amount = 1;
        }
        if (button.getId().equals("increase")){
            if(productList.getSelectedProduct().getStock() > amount)
                amount++;
        } else if (button.getId().equals("decrease")){
            if(amount > 1)
                amount--;
        }
        if (amount > productList.getSelectedProduct().getStock()) {
            amount = productList.getSelectedProduct().getStock();
        }
        amount = Math.max(amount, 1);
        amountTF.setText(amount + "");
    }

    @FXML
    private void handleMargetPlaceBtn(MouseEvent e){
        productTP.getSelectionModel().select(0);
    }

    @FXML
    private void handleSubmitReviewBtn(ActionEvent e){
        //TODO user can only review one time on a product
        String title = reviewTitleTF.getText();
        String detail = detailReviewTA.getText();
        reviewList.addReview(title, detail, newReviewRating,
                currUser, productList.getSelectedProduct());
        populateReview();
        resetReviewForm();
        dataSource.saveReview();
        dataSource.saveProduct();
        newReviewRating = -1;
    }

    @FXML
    private void handleProductCard(MouseEvent event) {
        VBox vBox = (VBox) event.getSource();
        productList.setSelectedProduct(vBox.getId());
        if(productList.getSelectedProduct() == null)
            return;
        buildProductPage();
        productTP.getSelectionModel().select(1);
    }

    @FXML
    private void handleCategoryBtn(ActionEvent e){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(200));
        translateTransition.setNode(bodyAP);
        if (!bodyToggle) {
            translateTransition.setByY(100);
            bodyToggle = true;
        } else{
            translateTransition.setByY(-100);
            bodyToggle = false;
        }
        translateTransition.play();
    }

    @FXML
    public void handleSeeMoreBtn(ActionEvent e){
        populateProduct(10);
    }

    // marketplace page
    private void populateProduct(int amount){
        int i = 0;
        for(Product product: productList){
            // TODO: move to models
            if (product.getPrice() > upperBoundParsed || product.getPrice() < lowerBoundParsed)
                continue;
            if (i > productIndex && i <= productIndex + amount) {
                VBox card = componentBuilder.productCard(product.getName(), product.getPrice(),
                        product.getPicturePath(), product.getId());
                card.setOnMouseReleased(this::handleProductCard);
                productFlowPane.getChildren().add(card);
            }
            i++;
        }
        productIndex += amount;
    }

    @FXML
    public void initialize() throws IOException {
        dataSource = (DataSource) FXRouter.getData();
        dataSource.parseProduct();
        dataSource.parseReview();
        dataSource.saveCategory();
        productList = dataSource.getProducts();
        reviewList = dataSource.getReviews();
        currUser = dataSource.getAccounts().getCurrAccount();
        productList.sort();
        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);
    }

    public void handleLogOutBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
