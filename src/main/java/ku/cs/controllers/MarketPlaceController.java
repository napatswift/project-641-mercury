package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.models.User;
import ku.cs.models.Category;
import ku.cs.models.CategoryList;
import ku.cs.models.components.*;
import ku.cs.models.components.theme.ThemeMenu;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MarketPlaceController {
    @FXML
    private Label amountInStockLabel;

    @FXML
    private TextField amountTF;

    @FXML
    private AnchorPane bodyAP;

    @FXML
    private HBox categoriesMenuHBox;

    @FXML
    private VBox categoriesVBox;

    @FXML
    private Label categoryBreadcrumbsLabel;

    @FXML
    private TextArea detailReviewTA;

    @FXML
    private HBox filerHBox;

    @FXML
    private SVGPath inStockStatusSvg;


    @FXML
    private TextField lowerBoundTF;

    @FXML
    private Label productDetailLabel;

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private ScrollPane productListSP;

    @FXML
    private Label productNameBreadcrumbsLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private TabPane productTP;

    @FXML
    private Label ratingSubmissionLabel;

    @FXML
    private HBox reviewRatingPanelStarHBox;

    @FXML
    private TextField reviewTitleTF;

    @FXML
    private VBox reviewVBox;

    @FXML
    private Button seeMoreBtn;

    @FXML
    private ImageView selectedProductIV;

    @FXML
    private HBox selectedProductStoreHBox;

    @FXML
    private MenuButton sortingMB;

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

    @FXML
    private ToolBar topBarTB;

    @FXML
    private TextField upperBoundTF;

    Tab storeProductPageTab;

    Tab orderSummaryTab, reportingTab;

    private boolean bodyToggle = false;
    private int productIndex = -1;
    private double upperBoundParsed = Double.MAX_VALUE, lowerBoundParsed = 0;
    private int newReviewRating = -1;
    private User currUser;
    private String filterCategory;

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
        productIndex = -1;
        populateProduct(temp + 1);
    }

    private void filterProduct(){
        String lower = lowerBoundTF.getText();
        String upper = upperBoundTF.getText();

        if (lower.equals("")) {
            lowerBoundParsed = 0;
        } else if (lower.matches("\\d\\.*")) {
                lowerBoundParsed = Double.parseDouble(lower);
        }

        if (upper.equals("")) {
            upperBoundParsed = Double.MAX_VALUE;
        } else if (upper.matches("\\d\\.*")) {
                upperBoundParsed = Double.parseDouble(upper);
        }
    }

    private void setBodyToggle(){
        if (!bodyToggle)
            return;
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(200));
        translateTransition.setNode(bodyAP);
        translateTransition.setByY(-120);
        bodyToggle = false;
        translateTransition.play();
    }

    @FXML
    private void handleFilter(KeyEvent ke){
        if( ke.getCode() == KeyCode.ENTER){
            filterProduct();
            productFlowPane.getChildren().clear();
            int temp = productIndex;
            productIndex = -1;
            populateProduct(temp + 1);
        }
    }

    /* product page builder */
    private void buildProductPage(){
        resetStar();
        /* reset amount to 1 */
        amountTF.setText("1");
        /* clear boxes */
        categoriesVBox.getChildren().clear();
        productListSP.setVvalue(0);

        populateReview();

        /* set product information */
        productNameLabel.setText(productList.getSelectedProduct().getName());
        productPriceLabel.setText("$"+productList.getSelectedProduct().getPrice());
        productDetailLabel.setText(productList.getSelectedProduct().getDetails());
        selectedProductIV.setImage(new Image(productList.getSelectedProduct().getPicturePath()));

        /* set store name */
        Store store = productList.getSelectedProduct().getStore();
        User owner = store.getOwner();
        selectedProductStoreHBox.setUserData(store);
        selectedProductStoreHBox.setOnMouseReleased(this::handleSelectedProductStoreBtn);
        storeNameLabel.setText(store.getName().toUpperCase());
        storeImageIV.setImage(new Image(owner.getPicturePath()));

        /* set bread crumbs info */
        categoryBreadcrumbsLabel.setText(productList.getSelectedProduct().getCategory().getName());
        categoryBreadcrumbsLabel.setOnMouseReleased(this::handleCategoryBreadcrumbsLabel);
        productNameBreadcrumbsLabel.setText(productList.getSelectedProduct().getName());

        /* handling in stock label and icon */
        amountInStockLabel.setText("" + productList.getSelectedProduct().getStock() + " in stock");
        if (productList.getSelectedProduct().getStock() < 10){
            inStockStatusSvg.setContent("M13,10h-2V8h2V10z M13,6h-2V1h2V6z M7,18c-1.1,0-1.99,0.9-1.99,2S5.9,22,7,22s2-0.9,2-2S8.1,18,7,18z M17,18 c-1.1,0-1.99,0.9-1.99,2s0.89,2,1.99,2s2-0.9,2-2S18.1,18,17,18z M8.1,13h7.45c0.75,0,1.41-0.41,1.75-1.03L21,4.96L19.25,4l-3.7,7 H8.53L4.27,2H1v2h2l3.6,7.59l-1.35,2.44C4.52,15.37,5.48,17,7,17h12v-2H7L8.1,13z");
        } else {
            inStockStatusSvg.setContent("M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2z");
        }
        /* handling category */
        for(Category category: productList.getSelectedProduct().getCategories())
            categoriesVBox.getChildren().add(new CategoryPane(category));
    }

    private void handleCategoryBreadcrumbsLabel(MouseEvent event){
        Label label = (Label) event.getSource();
        setFilterCategory(label.getText());
    }

    private void resetStar(){
        star1.setStyle("-fx-fill: primary-overlay");
        star2.setStyle("-fx-fill: primary-overlay");
        star3.setStyle("-fx-fill: primary-overlay");
        star4.setStyle("-fx-fill: primary-overlay");
        star5.setStyle("-fx-fill: primary-overlay");
        ratingSubmissionLabel.setText("0/5");
    }

    private void resetReviewForm(){
        resetStar();
        newReviewRating = -1;
        reviewTitleTF.setText("");
        detailReviewTA.setText("");
    }

    @FXML
    private void selectRatingStar(ActionEvent e){
        setBodyToggle();
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
        starsHBox.getChildren().clear();

        reviewVBox.getChildren().clear();
        Product product = dataSource.getProducts().getSelectedProduct();
        for (Review review: product.getReviews()){
            ReviewCard card = new ReviewCard(review);
            card.getFlagArea().setOnMouseReleased(this::handleReportReviewBtn);
            reviewVBox.getChildren().add(card);
        }

        if (product.getReview() == 0){
            Label noReviewLabel = new Label("No review yet! You'll be first!");
            noReviewLabel.getStyleClass().add("subtitle1");
            noReviewLabel.setPadding(new Insets(10));
            reviewVBox.getChildren().add(noReviewLabel);
        }

        reviewRatingPanelStarHBox.getChildren().add(new RatingStars(product.getRating()));
        Label reviewRatingPanelLabel =
                new Label(String.format("%.2f", product.getRating()) + "/5 (" + product.getReview() + " reviews)");
        reviewRatingPanelLabel.getStyleClass().add("subtitle1");
        reviewRatingPanelStarHBox.getChildren().add(reviewRatingPanelLabel);

        // handling product rating
        starsHBox.getChildren().add(new RatingStars(productList.getSelectedProduct().getRating()));
        Label starsHBoxLabel =
                new Label(String.format("%.2f", product.getRating()) + "/5 (" + product.getReview() + " reviews)");
        starsHBoxLabel.getStyleClass().add("subtitle1");
        starsHBox.getChildren().add(starsHBoxLabel);
    }

    /* handler */
    @FXML
    private void handleReportProductBtn(){
        if (dataSource.getReports() == null)
            dataSource.parseReport();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/reporting.fxml"));
        try {
            Node node = loader.load();
            ReportingViewController controller = loader.getController();
            controller.setReportItem(productList.getSelectedProduct());
            controller.setDataSource(dataSource);
            controller.setCancelBtnHandler(this::handleCancelBtn);
            reportingTab.setContent(node);
            productTP.getTabs().add(reportingTab);
            productTP.getSelectionModel().select(reportingTab);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleReportReviewBtn(MouseEvent event){
        if (dataSource.getReports() == null)
            dataSource.parseReport();

        HBox source = (HBox) event.getSource();
        Review sourceReview =  dataSource.getReviews().getReviewByID(source.getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/reporting.fxml"));
        try {
            Node node = loader.load();
            ReportingViewController controller = loader.getController();
            controller.setReportItem(sourceReview);
            controller.setDataSource(dataSource);
            controller.setCancelBtnHandler(this::handleCancelBtn);
            reportingTab.setContent(node);
            productTP.getTabs().add(reportingTab);
            productTP.getSelectionModel().select(reportingTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAmountBtn(ActionEvent event){
        setBodyToggle();
        Button button = (Button) event.getSource();
        int stock = productList.getSelectedProduct().getStock();
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

    @FXML
    private void handleMargetPlaceBtn(){
        setBodyToggle();
        productTP.getSelectionModel().select(0);
    }

    @FXML
    private void handleSubmitReviewBtn(){
        setBodyToggle();
        for(Review review: dataSource.getProducts().getSelectedProduct().getReviews())
            if (review.getAuthor().getUsername().equals(currUser.getUsername())) {
                resetReviewForm();
                return;
            }
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
        setBodyToggle();
        ProductCard productCard = (ProductCard) event.getSource();
        Product selectedProduct = productCard.getProduct();
        productList.setSelectedProduct(selectedProduct);
        resetReviewForm();
        if(productList.getSelectedProduct() == null)
            return;
        buildProductPage();
        productTP.getSelectionModel().select(1);
    }

    @FXML
    private void handleCategoryBtn(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(200));
        translateTransition.setNode(bodyAP);
        if (!bodyToggle) {
            translateTransition.setByY(120);
            bodyToggle = true;
        } else{
            translateTransition.setByY(-120);
            bodyToggle = false;
        }
        translateTransition.play();

    }

    @FXML
    public void handleSeeMoreBtn(ActionEvent e){
        setBodyToggle();
        populateProduct(10);
    }

    @FXML
    public void handleMyStore() {
        setBodyToggle();
        if (!dataSource.getUserList().getCurrUser().getHasStore()) {
            try {
                FXRouter.goTo("create_store", dataSource);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า create_store ไม่ได้");
                System.err.println("ตรวจสอบ Route");
            }
        } else {
            try {
                FXRouter.goTo("my_store", dataSource);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า my_store ไม่ได้");
                System.err.println("ตรวจสอบ Route");
                e.printStackTrace();
            }
        }
    }

    private void handleFilterByCategory(MouseEvent e){
        setBodyToggle();
        Button button = (Button) e.getSource();
        setFilterCategory(button.getId());
    }

    private void handleSelectedProductStoreBtn(MouseEvent e){
        HBox source = (HBox) e.getSource();
        Store store = (Store) source.getUserData();
        if (storeProductPageTab == null) storeProductPageTab = new Tab("storeProductPage");
        productTP.getTabs().add(storeProductPageTab);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/store_product_page.fxml"));

        try {
            Node node = loader.load();
            storeProductPageTab.setContent(node);
            StoreProductPageController controller = loader.getController();
            controller.setStore(store);
            productTP.getSelectionModel().select(storeProductPageTab);
            FlowPane flowPane = controller.getProductFlowPane();

            ProductList products = dataSource.getProducts();
            populateProduct(flowPane, products.getProductByNameStore(store.getName()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setFilterCategory(String category){
        productTP.getSelectionModel().select(0);
        filerHBox.getChildren().clear();

        Label filterLabel = new Label("Filter");
        filterLabel.getStyleClass().add("subtitle1");
        Label headerLabel = new Label(category);
        headerLabel.getStyleClass().add("subtitle2");
        filerHBox.getChildren().add(filterLabel);

        SVGPath closeSVG = new SVGPath();
        closeSVG.setContent("M14.59 8L12 10.59 9.41 8 8 9.41 10.59 12 8 14.59 9.41 16 12 13.41 14.59" +
                " 16 16 14.59 13.41 12 16 9.41 14.59 8zM12 2C6.47 2 2 6.47 2 12s4.47 10 10 10 10-4.47" +
                " 10-10S17.53 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z");
        closeSVG.setStyle("-fx-fill: primary-med-overlay");
        HBox close = new HBox(closeSVG);
        close.setOnMouseReleased(this::unselectFilter);
        close.setCursor(Cursor.HAND);

        HBox hBox = new HBox(headerLabel, close);
        hBox.setSpacing(15);
        hBox.setStyle("" +
                "-fx-background-radius: 40;" +
                "-fx-background-color: primary-overlay;" +
                "-fx-border-color: primary-med-overlay;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 40");
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.prefWidth(Region.USE_COMPUTED_SIZE);
        hBox.setAlignment(Pos.CENTER);
        filerHBox.getChildren().add(hBox);

        filterCategory = category;

        productFlowPane.getChildren().clear();
        int temp = productIndex;
        productIndex = -1;
        populateProduct(temp + 1);
    }

    private void unselectFilter(MouseEvent e){
        setBodyToggle();
        filterCategory = null;
        filerHBox.getChildren().clear();

        productFlowPane.getChildren().clear();
        int temp = productIndex;
        productIndex = -1;
        populateProduct(temp + 1);
    }

    /* marketplace page */
    private void populateProduct(int amount){
        int i = 0;
        Iterator<Product> iterator;
        iterator = productList.iterator(lowerBoundParsed, upperBoundParsed, filterCategory);
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (i++ > productIndex && i < productIndex + amount) {
                ProductCard card = new ProductCard(product);
                card.setOnMouseReleased(this::handleProductCard);
                productFlowPane.getChildren().add(card);
            }
        }

        productIndex += amount;
        seeMoreBtn.setDisable(productIndex >= productList.size());
    }

    private void populateProduct(FlowPane flowPane, ArrayList<Product> products){
        for (Product p: products) {
                ProductCard card = new ProductCard(p);
                card.setOnMouseReleased(this::handleProductCard);
                flowPane.getChildren().add(card);
        }
    }

    private void populateCategory(CategoryList categories){
        int i = 0;
        VBox box = new VBox();
        box.setSpacing(3);
        for(String category: categories.categorySet()){
            if (i != 0 && i % 4 == 0){
                categoriesMenuHBox.getChildren().add(box);
                box = new VBox();
                box.setSpacing(3);
            }
            Button categoryBtn = new Button(category);
            categoryBtn.setStyle("-fx-text-fill: on-secondary-color");
            categoryBtn.setId(category);
            categoryBtn.setOnMouseReleased(this::handleFilterByCategory);
            box.getChildren().add(categoryBtn);
            i++;
        }
        categoriesMenuHBox.getChildren().add(box);
    }

    public void handleLogOutBtn() {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException exception) {
            exception.printStackTrace();
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void addThemeMenu(){
        int size = topBarTB.getItems().size();
        Node lastNode = topBarTB.getItems().get(size - 1);
        MenuButton themeMenu = new ThemeMenu();
        themeMenu.getStyleClass().add("on-secondary-color-menu-button");
        topBarTB.getItems().set(size - 1, themeMenu);
        topBarTB.getItems().add(lastNode);
    }

    private void setProductTPModel(){
        productTP.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getText().equals("marketplace") || newValue.getText().equals("product_view")) {
                if (storeProductPageTab != null ) {
                    storeProductPageTab.setContent(null);
                    productTP.getTabs().remove(storeProductPageTab);
                }
                if (reportingTab != null ) {
                    reportingTab.setContent(null);
                    productTP.getTabs().remove(reportingTab);
                }
                if (orderSummaryTab != null ) {
                    orderSummaryTab.setContent(null);
                    productTP.getTabs().remove(orderSummaryTab);
                }
            }
        });
    }

    @FXML
    public void initialize() throws IOException {
        dataSource = (DataSource) FXRouter.getData();
        dataSource.parseProduct();

        populateCategory(dataSource.getCategories());
        dataSource.parseReview();
        dataSource.saveCategory();
        productList = dataSource.getProducts();
        reviewList = dataSource.getReviews();
        currUser = dataSource.getUserList().getCurrUser();
        productList.sort();
        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);

        addThemeMenu();

        setTextField();
        setProductTPModel();
        orderSummaryTab = new Tab("order_summary");
        reportingTab = new Tab("reporting");
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
        setBodyToggle();
        productTP.getTabs().remove(orderSummaryTab);
        productTP.getTabs().remove(reportingTab);
        productTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleBuyBtn() {
        int amountBuy = Integer.parseInt(amountTF.getText());
        if(productList.getSelectedProduct().isInStock(amountBuy)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/order-summary.fxml"));
                Node node = loader.load();
                orderSummaryTab.setContent(node);
                productTP.getTabs().add(orderSummaryTab);
                OrderSummaryController controller = loader.getController();
                controller.setDataSource(dataSource);
                controller.setAmountBuy(amountBuy);
                controller.setOnActionCancelBtn(this::handleCancelBtn);
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

    public void handleMyAccount() {
        try{
            FXRouter.goTo("my_account", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
