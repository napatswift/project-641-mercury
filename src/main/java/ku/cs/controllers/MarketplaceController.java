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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.models.CategoryList;
import ku.cs.models.components.*;
import ku.cs.models.components.theme.ThemeMenu;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.util.Iterator;

public class MarketplaceController {
    @FXML
    private AnchorPane bodyAP;

    @FXML
    private HBox categoriesMenuHBox;

    @FXML
    private HBox filerHBox;

    @FXML
    private TextField lowerBoundTF;

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private TabPane productTP;

    @FXML
    private Button seeMoreBtn;

    @FXML
    private MenuButton sortingMB;

    @FXML
    private ToolBar topBarTB;

    @FXML
    private TextField upperBoundTF;

    private Tab storeProductPageTab;
    private Tab orderSummaryTab;
    private Tab reportingTab;
    private Tab myAccountTab;
    private Tab productDetailTab;

    private boolean bodyToggle = false;
    private int productIndex = -1;
    private double upperBoundParsed = Double.MAX_VALUE, lowerBoundParsed = 0;
    private String filterCategory;

    private ProductList productList;
    private DataSource dataSource;
    private ProductDetailPageController productDetailPageController;

    @FXML
    private void sortProductBy(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        if (menuItem.getId().equals("lowestPrice")){
            sortingMB.setText("SORT BY : LOWEST PRICE");
            productList.sort(ProductList.SortType.BY_LOWEST);
        } else if (menuItem.getId().equals("highestPrice")){
            sortingMB.setText("SORT BY : HIGHEST PRICE");
            productList.sort(ProductList.SortType.BY_HIGHEST);
        } else if (menuItem.getId().equals("mostRecent")){
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

        if (lower.isEmpty() || lower.isBlank()) {
            lowerBoundParsed = 0;
        } else if (lower.matches("[\\d\\.]*")) {
                lowerBoundParsed = Double.parseDouble(lower);
        }

        if (upper.isEmpty() || upper.isBlank()) {
            upperBoundParsed = Double.MAX_VALUE;
        } else if (upper.matches("[\\d\\.]*")) {
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
            double lTemp = lowerBoundParsed;
            double uTemp = upperBoundParsed;
            filterProduct();
            if (lTemp != lowerBoundParsed || uTemp != upperBoundParsed) {
                productFlowPane.getChildren().clear();
                int temp = productIndex;
                productIndex = -1;
                populateProduct(temp + 1);
            }
        }
    }

    /* handler */
    @FXML
    private void handleMargetPlaceBtn(){
        setBodyToggle();
        productTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleProductCard(MouseEvent event) {
        setBodyToggle();
        ProductCard productCard = (ProductCard) event.getSource();
        Product selectedProduct = productCard.getProduct();
        productList.setSelectedProduct(selectedProduct);
        if(productList.getSelectedProduct() == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/product_detail.fxml"));
        try {
            Node node = loader.load();
            productDetailPageController = loader.getController();
            productDetailTab.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productTP.getTabs().add(productDetailTab);
        productTP.getSelectionModel().select(productDetailTab);
        setProductDetailPageController();
        productDetailPageController.setSelectedProduct(selectedProduct);
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

    public void handleMyAccount() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/my_account.fxml"));
        try{
            Node node = loader.load();
            myAccountTab = new Tab("my_account", node);
            if (!productTP.getTabs().contains(myAccountTab))
                productTP.getTabs().add(myAccountTab);
            productTP.getSelectionModel().select(myAccountTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFilterCategory(String category){
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
            if (newValue.getText().equals("marketplace")) {
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
                if (myAccountTab != null ) {
                    myAccountTab.setContent(null);
                    productTP.getTabs().remove(myAccountTab);
                }
                if (productDetailTab != null ) {
                    productDetailTab.setContent(null);
                    productTP.getTabs().remove(productDetailTab);
                }
            }
        });
    }

    public void setProductDetailPageController() {
        productDetailPageController.setMarketPlaceController(this);
        productDetailPageController.setDataSource(dataSource);
        productDetailPageController.setProductTP(productTP);
        productDetailPageController.setStoreProductPageTab(storeProductPageTab);
        productDetailPageController.setReportingTab(reportingTab);
        productDetailPageController.setOrderSummaryTab(orderSummaryTab);
    }

    @FXML
    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        dataSource.parseProduct();

        populateCategory(dataSource.getCategories());
        dataSource.parseReview();
        dataSource.saveCategory();
        productList = dataSource.getProducts();
        productList.sort();
        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);

        addThemeMenu();

        setProductTPModel();
        orderSummaryTab = new Tab("order_summary");
        reportingTab = new Tab("reporting");
        storeProductPageTab = new Tab("store_product_page");
        productDetailTab = new Tab("product_detail_page");
    }
}
