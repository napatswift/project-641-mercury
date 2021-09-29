package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.models.components.*;
import ku.cs.models.components.theme.ThemeMenu;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Flow;

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
    ImageView storeImageIV, selectedProductIV;
    @FXML
    FlowPane productFlowPane;
    @FXML
    Button seeMoreBtn,
            starBtn1, starBtn2, starBtn3, starBtn4, starBtn5;
    @FXML
    MenuButton sortingMB;
    @FXML
    TextField upperBoundTF, lowerBoundTF, amountTF,
            reviewTitleTF;
    @FXML
    TextArea detailReviewTA;
    @FXML
    HBox starsHBox, filerHBox,
        reviewRatingPanelStarHBox, categoriesMenuHBox,
        selectedProductStoreHBox;
    @FXML
    VBox reviewVBox, categoriesVBox;
    @FXML
    ToolBar topBarTB;

    Tab storeProductPageTab;

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
    private void handleReportProductBtn(MouseEvent event){
        if (dataSource.getReports() == null)
            dataSource.parseReport();

        dataSource.getReviews().setCurrReview(null);

        try {
            FXRouter.goTo("reporting", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleReportReviewBtn(MouseEvent event){
        if (dataSource.getReports() == null)
            dataSource.parseReport();

        HBox source = (HBox) event.getSource();
        Review sourceReview =  dataSource.getReviews().getReviewByID(source.getId());
        dataSource.getProducts().setSelectedProduct(null);
        dataSource.getReviews().setCurrReview(sourceReview);

        try {
            FXRouter.goTo("reporting", dataSource);
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

        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e){
            amount = 1;
        }

        if (button.getId().equals("increase")){
            if(stock > amount)
                amount++;
        } else if (button.getId().equals("decrease")){
            if(amount > 1) amount--;
        }

        if (amount > stock) amount = stock;

        amount = Math.max(amount, 1);
        amountTF.setText(amount + "");
    }

    @FXML
    private void handleMargetPlaceBtn(MouseEvent e){
        setBodyToggle();
        productTP.getSelectionModel().select(0);
    }

    @FXML
    private void handleSubmitReviewBtn(ActionEvent e){
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
    private void handleCategoryBtn(ActionEvent e){
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
    public void handleMyStore(ActionEvent event) {
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
    }

    private void populateProduct(FlowPane flowPane, ArrayList<Product> products){
        for (Product p: products) {
                ProductCard card = new ProductCard(p);
                card.setOnMouseReleased(this::handleProductCard);
                flowPane.getChildren().add(card);
        }
    }

    private void populateCategory(Set<String> categories){
        int i = 0;
        VBox box = new VBox();
        box.setSpacing(3);
        for(String category: categories){
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

    public void handleLogOutBtn(ActionEvent e) {
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

        setProductTPModel();
    }
}
