package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.StageStyle;
import ku.cs.models.*;
import ku.cs.models.components.CategoryListPane;
import ku.cs.models.components.ProductCard;
import ku.cs.models.components.RatingStars;
import ku.cs.models.components.ReviewCard;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ProductDetailPageController {

    @FXML
    private Label amountInStockLabel;

    @FXML
    private TextField amountTF;

    @FXML
    private VBox categoriesVBox;

    @FXML
    private Label categoryBreadcrumbsLabel;

    @FXML
    private Button decrease;

    @FXML
    private TextArea detailReviewTA;

    @FXML
    private SVGPath inStockStatusSvg;

    @FXML
    private Button increase;

    @FXML
    private Label productDetailLabel;

    @FXML
    private Label productNameBreadcrumbsLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label ratingSubmissionLabel;

    @FXML
    private HBox reviewRatingPanelStarHBox;

    @FXML
    private TextField reviewTitleTF;

    @FXML
    private VBox reviewVBox;

    @FXML
    private ImageView selectedProductIV;

    @FXML
    private HBox selectedProductStoreHBox;

    @FXML
    private SVGPath star1;

    @FXML
    private SVGPath star2;

    @FXML
    private SVGPath star3;

    @FXML
    private SVGPath star4;

    @FXML
    private SVGPath star5;

    @FXML
    private HBox starsHBox;

    @FXML
    private ImageView storeImageIV;

    @FXML
    private Label storeNameLabel;

    private MarketplaceController marketPlaceController;
    private DataSource dataSource;
    private TabPane productTP;
    private int newReviewRating = -1;
    private Product selectedProduct;
    private Tab storeProductPageTab;
    private Tab orderSummaryTab;
    private Tab reportingTab;
    private CategoryListPane categoryListPane;

    public void setMarketPlaceController(MarketplaceController marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setProductTP(TabPane productTP) {
        this.productTP = productTP;
    }

    public void setStoreProductPageTab(Tab storeProductPageTab) {
        this.storeProductPageTab = storeProductPageTab;
    }

    public void setOrderSummaryTab(Tab orderSummaryTab) {
        this.orderSummaryTab = orderSummaryTab;
    }

    public void setReportingTab(Tab reportingTab) {
        this.reportingTab = reportingTab;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        buildProductPage();
        populateReview();

    }

    public void initialize(){
        categoryListPane = new CategoryListPane();
        categoriesVBox.getChildren().add(categoryListPane);
    }

    @FXML
    void handleBuyBtn(ActionEvent event) {
        int amountBuy = Integer.parseInt(amountTF.getText());
        if(selectedProduct.isInStock(amountBuy)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/order_summary.fxml"));
                Node node = loader.load();
                orderSummaryTab.setContent(node);
                productTP.getTabs().add(orderSummaryTab);
                OrderSummaryController controller = loader.getController();
                controller.setDataSource(dataSource);
                controller.setAmountBuy(amountBuy);
                controller.setOnActionCancelBtn(this::handleOrderSummaryCancelBtn);
                productTP.getSelectionModel().select(orderSummaryTab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(
                    Alert.AlertType.NONE,
                    "Our product in stock is lower than your demand" + "\n"
                            + "please enter amount again",
                    ButtonType.OK);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("Sorry");
            alert.showAndWait();
        }
    }

    @FXML
    void handleMargetPlaceBtn(MouseEvent event) {
        productTP.getSelectionModel().select(0);
    }

    @FXML
    void handleReportProductBtn(MouseEvent event) {
        if (dataSource.getReports() == null)
            dataSource.parseReport();
        createReportPage().setReportItem(selectedProduct);
        productTP.getTabs().add(reportingTab);
        System.out.println(productTP.getTabs().size());
        productTP.getSelectionModel().select(reportingTab);
    }

    @FXML
    void handleSubmitReviewBtn(ActionEvent event) {
        User currUser = dataSource.getUserList().getCurrUser();
        ReviewList reviewList = dataSource.getReviews();
        for(Review review: selectedProduct.getReviews())
            if (review.getAuthor().getUsername().equals(currUser.getUsername())) {
                resetReviewForm();
                return;
            }
        String title = reviewTitleTF.getText();
        String detail = detailReviewTA.getText();
        reviewList.addReview(title, detail, newReviewRating,
                currUser, dataSource.getProducts().getSelectedProduct());
        populateReview();
        resetReviewForm();
        dataSource.saveReview();
        dataSource.saveProduct();
        newReviewRating = -1;
    }


    private void buildProductPage(){
        resetStar();
        /* reset amount to 1 */
        amountTF.setText("1");
        /* clear boxes */

        /* set product information */
        productNameLabel.setText(selectedProduct.getName());
        productPriceLabel.setText("$"+selectedProduct.getPrice());
        productDetailLabel.setText(selectedProduct.getDetails());
        selectedProductIV.setImage(new Image(selectedProduct.getPicturePath()));

        /* set store name */
        Store store = selectedProduct.getStore();
        User owner = store.getOwner();
        selectedProductStoreHBox.setUserData(store);
        selectedProductStoreHBox.setOnMouseReleased(this::handleSelectedProductStoreBtn);
        storeNameLabel.setText(store.getName().toUpperCase());
        storeImageIV.setImage(new Image(owner.getPicturePath()));

        /* set bread crumbs info */
        if (selectedProduct.getCategories().size() > 0) {
            categoryBreadcrumbsLabel.setText(selectedProduct.getCategory().getName());
            categoryBreadcrumbsLabel.setOnMouseReleased(this::handleCategoryBreadcrumbsLabel);
        } else {
            categoryBreadcrumbsLabel.setText("Category");
        }
        productNameBreadcrumbsLabel.setText(selectedProduct.getName());

        /* handling in stock label and icon */
        amountInStockLabel.setText("" + selectedProduct.getStock() + " in stock");
        if (selectedProduct.getStock() < 10){
            inStockStatusSvg.setContent("M13,10h-2V8h2V10z M13,6h-2V1h2V6z M7,18c-1.1,0-1.99,0.9-1.99,2S5.9,22,7,22s2-0.9,2-2S8.1,18,7,18z M17,18 c-1.1,0-1.99,0.9-1.99,2s0.89,2,1.99,2s2-0.9,2-2S18.1,18,17,18z M8.1,13h7.45c0.75,0,1.41-0.41,1.75-1.03L21,4.96L19.25,4l-3.7,7 H8.53L4.27,2H1v2h2l3.6,7.59l-1.35,2.44C4.52,15.37,5.48,17,7,17h12v-2H7L8.1,13z");
        } else {
            inStockStatusSvg.setContent("M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2z");
        }

        /* handling category */
        categoryListPane.setCategoryList(selectedProduct.getCategories());
    }

    private void populateProduct(FlowPane flowPane, ArrayList<Product> products){
        for (Product p: products) {
            ProductCard card = new ProductCard(p);
            card.setOnMouseReleased(marketPlaceController::handleProductCard);
            flowPane.getChildren().add(card);
        }
    }

    private void populateReview(){
        reviewRatingPanelStarHBox.getChildren().clear();
        starsHBox.getChildren().clear();

        reviewVBox.getChildren().clear();
        for (Review review: selectedProduct.getReviews()){
            ReviewCard card = new ReviewCard(review);
            card.getFlagArea().setOnMouseReleased(this::handleReportReviewBtn);
            reviewVBox.getChildren().add(card);
        }

        if (selectedProduct.getReview() == 0){
            Label noReviewLabel = new Label("No review yet! You'll be first!");
            noReviewLabel.getStyleClass().add("subtitle1");
            noReviewLabel.setPadding(new Insets(10));
            reviewVBox.getChildren().add(noReviewLabel);
        }

        reviewRatingPanelStarHBox.getChildren().add(new RatingStars(selectedProduct.getRating()));
        Label reviewRatingPanelLabel =
                new Label(String.format("%.2f", selectedProduct.getRating()) + "/5 (" + selectedProduct.getReview() + " reviews)");
        reviewRatingPanelLabel.getStyleClass().add("subtitle1");
        reviewRatingPanelStarHBox.getChildren().add(reviewRatingPanelLabel);

        // handling product rating
        starsHBox.getChildren().add(new RatingStars(selectedProduct.getRating()));
        Label starsHBoxLabel =
                new Label(String.format("%.2f", selectedProduct.getRating()) + "/5 (" + selectedProduct.getReview() + " reviews)");
        starsHBoxLabel.getStyleClass().add("subtitle1");
        starsHBox.getChildren().add(starsHBoxLabel);
    }

    private void handleCategoryBreadcrumbsLabel(MouseEvent event){
        Label label = (Label) event.getSource();
        marketPlaceController.setFilterCategory(label.getText());
    }

    public void handleReportReviewBtn(MouseEvent event){
        HBox source = (HBox) event.getSource();
        Review sourceReview =  dataSource.getReviews().getReviewByID(source.getId());
        createReportPage().setReportItem(sourceReview);
        productTP.getTabs().add(reportingTab);
        productTP.getSelectionModel().select(reportingTab);
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

    @FXML
    private void handleAmountBtn(ActionEvent event){
        Button button = (Button) event.getSource();
        int stock = selectedProduct.getStock();
        String amountStr = amountTF.getText();
        int amount;
        String btnId = button.getId();

        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e){
            amount = 1;
        }

        if (btnId.equals("increase")){
            if (stock > amount) amount++;
        } else if (btnId.equals("decrease")){
            if (amount > 1) amount--;
        }

        if (amount > stock) amount = stock;

        amount = Math.max(amount, 1);
        amountTF.setText(amount + "");
    }

    private void handleSelectedProductStoreBtn(MouseEvent e){
        HBox source = (HBox) e.getSource();
        Store store = (Store) source.getUserData();
        if (storeProductPageTab == null)
            storeProductPageTab = new Tab("storeProductPage");
        productTP.getTabs().add(storeProductPageTab);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/store_product_page.fxml"));

        try {
            Node node = loader.load();
            storeProductPageTab.setContent(node);
            StoreProductPageController controller = loader.getController();
            controller.setStore(store);
            dataSource.parseCoupon();
            controller.setCouponList(dataSource.getCoupons());
            productTP.getSelectionModel().select(storeProductPageTab);
            FlowPane flowPane = controller.getProductFlowPane();

            ProductList products = dataSource.getProducts();
            populateProduct(flowPane, products.getProductByNameStore(store.getName()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setTextField(){
        amountTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d\\.*")) {
                amountTF.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
            if (!amountTF.getText().isBlank() || !amountTF.getText().isEmpty()) {
                int quantity = Integer.parseInt(amountTF.getText());
                int productQuantity = dataSource.getProducts().getSelectedProduct().getStock();
                if (quantity > productQuantity)
                    amountTF.setText(productQuantity + "");
            }
        });
    }

    private void handleCancelBtn(ActionEvent event){
        productTP.getTabs().remove(orderSummaryTab);
        productTP.getTabs().remove(reportingTab);
        productTP.getSelectionModel().select(0);
    }

    private void handleOrderSummaryCancelBtn(ActionEvent event){
        productTP.getTabs().remove(orderSummaryTab);
        if (productTP.getTabs().size() > 1)
            productTP.getSelectionModel().select(1);
        else
            productTP.getSelectionModel().select(0);
    }

    private void resetReviewForm(){
        resetStar();
        newReviewRating = -1;
        reviewTitleTF.setText("");
        detailReviewTA.setText("");
    }

    private void resetStar(){
        star1.setStyle("-fx-fill: primary-overlay");
        star2.setStyle("-fx-fill: primary-overlay");
        star3.setStyle("-fx-fill: primary-overlay");
        star4.setStyle("-fx-fill: primary-overlay");
        star5.setStyle("-fx-fill: primary-overlay");
        ratingSubmissionLabel.setText("0/5");
    }

    private ReportingViewController createReportPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/reporting.fxml"));
        try {
            Node node = loader.load();
            reportingTab.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReportingViewController controller = loader.getController();
        controller.setDataSource(dataSource);
        controller.setCancelBtnHandler(this::handleCancelBtn);
        return controller;
    }
}
