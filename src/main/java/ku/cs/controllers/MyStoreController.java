package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Window;
import ku.cs.models.*;
import ku.cs.models.User;
import ku.cs.models.CategoryList;
import ku.cs.models.components.*;
import ku.cs.models.components.dialogs.ConfirmEditProductDialog;
import ku.cs.models.components.dialogs.PictureConfirmDialog;
import ku.cs.models.components.listCell.OrderListCell;
import ku.cs.models.components.listCell.ProductListCell;
import ku.cs.models.components.listCell.PromotionListCell;
import ku.cs.models.coupon.Coupon;
import ku.cs.models.coupon.CouponType;
import ku.cs.models.utils.ImageUploader;
import ku.cs.service.DataSource;
import ku.cs.strategy.OrderByStoreGetter;
import ku.cs.strategy.ShippedOrderByStoreGetter;
import ku.cs.strategy.ToShipOrderByStoreGetter;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MyStoreController  {
    private DataSource dataSource;
    private Product product;
    private User currUser;
    private ImageUploader imageUploader;
    private ArrayList<Order> orders;
    private ArrayList<Coupon> couponTypes;
    private String categoryText = "";
    private Image image;

    @FXML private Label usernameLabel, nameLabel, nameStoreLabel;
    @FXML private TabPane myStoreTP;
    @FXML private ImageView userImage;
    @FXML private ChoiceBox<String> categoryCB, subCategoryCB;
    @FXML private TextField valueTF, nameProductTF, priceTF, stockTF;
    @FXML private TextArea descriptionTF;
    @FXML private ImageView pictureViewIV;
    @FXML private Label nameProductLabel, priceLabel, stockLabel, descriptionLabel;
    @FXML private HBox newProductCategoryHBox;
    @FXML private ImageView confirmProductIV;
    @FXML private ListView<Product> productsListLV;
    @FXML private ListView<Order> orderLV;
    @FXML private ListView<Coupon> couponsLV;
    @FXML private Label rateLB, detailsLB,numberLowerLabel, categoryLB;
    @FXML private TextField nameProductLB, priceLB, stockLB;
    @FXML private VBox rightProductVB, ImageViewVBox;
    @FXML private SplitPane productSP;
    @FXML private HBox ratingStarsSelectedProduct;
    @FXML private SVGPath stockWarningSelectedProductSVG;
    @FXML private AnchorPane productsRightPane;
    @FXML private ToggleButton myAccountMenuBtn, myStoreMenuBtn, productsMenuBtn, ordersMenuBtn, couponMenuBtn;
    @FXML Button clearBtn;

    private Tab myStoreTab, myAccountTab;

    private ResizeableImageView selectedProductResizeableImageView;
    private Product selectedProduct;
    private CategoryListPane newProductCategoryListPane;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        currUser = dataSource.getUserList().getCurrUser();
        dataSource.parseOrder();
        dataSource.parseCoupon();
        orders = dataSource.getOrders().getOrderList(new OrderByStoreGetter(currUser.getStoreName()));
        couponTypes = dataSource.getCoupons().toListCouponInStore(currUser.getStore());
        setupUserInfo();
        loadCategory();
        handleListProductBtn();

        productsRightPane.setVisible(false);
        newProductCategoryListPane = new CategoryListPane();
        newProductCategoryHBox.getChildren().add(newProductCategoryListPane);

        showProductsListView();
        handleProductsListView();
        setupTabPaneListener();
        showCouponListView(couponTypes);
        showOrderListView(orders);

        setGroup();
        setNumberTextField();
    }

    private void setupUserInfo(){
        usernameLabel.setText("@" + currUser.getUsername());
        nameStoreLabel.setText(currUser.getStoreName());
        nameLabel.setText(currUser.getName());
        numberLowerLabel.setText("" + currUser.getStore().getStockLowerBound());
        userImage.setImage(new Image(currUser.getPicturePath()));
        userImage.setClip(new Circle(25, 25, 25));
    }

    private void setupTabPaneListener(){
        myStoreTP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != myAccountTab) {
                    myStoreTP.getTabs().remove(myAccountTab);
                    myAccountTab = null;
                }
                if (newValue != myStoreTab) {
                    myStoreTP.getTabs().remove(myStoreTab);
                    myStoreTab = null;
                }
            }
        });
    }

    private void setNumberTextField() {
        priceLB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d\\.*")) {
                priceLB.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
        });

        priceTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d\\.*")) {
                priceTF.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
        });

        stockLB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                stockLB.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        stockTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                stockTF.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void setGroup(){
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((observableValue, ot, nt) -> {
            if (ot != null)
                ((ToggleButton) ot).getStyleClass().remove("list-item-active-btn");
            if (nt != null)
                ((ToggleButton) nt).getStyleClass().add("list-item-active-btn");
        });

        myAccountMenuBtn.setToggleGroup(group);
        myStoreMenuBtn.setToggleGroup(group);
        productsMenuBtn.setToggleGroup(group);
        ordersMenuBtn.setToggleGroup(group);
        couponMenuBtn.setToggleGroup(group);

        productsMenuBtn.fire();
    }

    @FXML
    public void handleBackBtn(){
        try {
            FXRouter.goTo("marketplace",dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleMyAccountMenuBtn(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/my_account.fxml"));
        try {
            Node node = loader.load();
            myAccountTab = new Tab("my_account", node);
            if (!myStoreTP.getTabs().contains(myAccountTab))
                myStoreTP.getTabs().add(myAccountTab);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myStoreTP.getSelectionModel().select(myAccountTab);
        MyAccountController controller = loader.getController();
        controller.showUser(dataSource.getUserList().getCurrUser());
    }

    @FXML
    public void handleMyStoreMenuBtn(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/store_product_page.fxml"));
        try {
            Node node = loader.load();
            myStoreTab = new Tab("my_store", node);
            if (!myStoreTP.getTabs().contains(myStoreTab))
                myStoreTP.getTabs().add(myStoreTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStoreTP.getSelectionModel().select(myStoreTab);

        Store store = currUser.getStore();
        StoreProductPageController controller = loader.getController();
        controller.setStore(store);

        FlowPane flowPane = controller.getProductFlowPane();
        ArrayList<Product> products = dataSource.getProducts().getProductByNameStore(store.getName());
        for (Product p: products) {
            ProductCard card = new ProductCard(p);
            card.setOnMouseReleased(this::handleProductCardStoreProductPage);
            flowPane.getChildren().add(card);
        }
    }

    private void handleProductCardStoreProductPage(MouseEvent event){
        ProductCard productCard = (ProductCard) event.getSource();
        if (productsListLV.getItems().contains(productCard.getProduct())) {
            productsListLV.getSelectionModel().select(productCard.getProduct());
            productsMenuBtn.fire();
        }
    }

    @FXML
    public void handleListProductBtn(){
        showProductsListView();
        myStoreTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleAddProductBtn(){
        clearBtn.setVisible(false);
        product = new Product("", "", dataSource.getUserList().getCurrUser().getStore());
        myStoreTP.getSelectionModel().select(1);
        categoryLB.setText(categoryText);
    }

    @FXML
    public void handleOrdersBtn(){
        myStoreTP.getSelectionModel().select(3);
    }

    @FXML
    public void handleCouponBtn(){
        myStoreTP.getSelectionModel().select(4);
    }

    public void loadCategory(){
        ObservableList<String> list = FXCollections.observableArrayList(dataSource.getCategories().categorySet());
        categoryCB.setItems(list);

        categoryCB.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> loadSubCategory(list.get(t1.intValue())));
    }

    public void loadSubCategory(String category){
        CategoryList categories = dataSource.getCategories();
        ObservableList<String> list = FXCollections.observableArrayList(categories.getSubcategoryOf(category));
        subCategoryCB.setItems(list);
    }

    @FXML public void handleAddBtn(){
        clearBtn.setVisible(true);
        String value = valueTF.getText();
        valueTF.setText("");
        String category = categoryCB.getSelectionModel().getSelectedItem();
        String subCategory = subCategoryCB.getSelectionModel().getSelectedItem();
        product.addSubCategory(category,subCategory,value);
        categoryLB.setText(product.getCategories().toString());
    }

    @FXML public void handleClearBtn(){
        clearBtn.setVisible(false);
        product.getCategories().clear();
        categoryLB.setText("");
    }

    @FXML public void handleNextBtn(){
        String name = nameProductTF.getText();
        double price = Double.parseDouble(priceTF.getText());
        int stock = Integer.parseInt(stockTF.getText());
        String detail = descriptionTF.getText();

        if(!name.equals("") && price > 0 && stock > 0 && !detail.equals("")) {
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setDetails(detail);
            product.setStore(dataSource.getUserList().getCurrUser().getStore());

            myStoreTP.getSelectionModel().select(2);
            nameProductLabel.setText(product.getName());
            priceLabel.setText(String.format("%.2f", product.getPrice()));
            stockLabel.setText(String.format("%d", product.getStock()));
            descriptionLabel.setText(product.getDetails());

            newProductCategoryListPane.setCategoryList(product.getCategories());
            confirmProductIV.setImage(image);
        }
    }

    @FXML
    public void handleSelectNewProductPicture() {
        Window window = pictureViewIV.getScene().getWindow();
        imageUploader = new ImageUploader(window, "images/product_images");
        imageUploader.show("Upload product picture");

        try {
            Image uploadedImage = new Image(
                    new FileInputStream(imageUploader.getUploadedFile()));
            pictureViewIV.setImage(uploadedImage);
            image = uploadedImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleConfirmBtn(){
        try {
            imageUploader.saveImageFile();
            product.setPictureName(imageUploader.getDestinationFile().getFileName().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource.getProducts().addProduct(product);
        dataSource.saveProduct();
        myStoreTP.getSelectionModel().select(0);
        showProductsListView();
    }

    public void handleEditBtn(){
        String productName = nameProductLB.getText();
        String price = priceLB.getText();
        String stock = stockLB.getText();
        ConfirmEditProductDialog dialog = new ConfirmEditProductDialog(productName, price, stock);
        Optional<Boolean> result = dialog.showAndWait();
        if (result.isPresent() && result.get()) {
            product.setName(productName);
            product.setPrice(Double.parseDouble(price));
            product.setStock(Integer.parseInt(stock));
            dataSource.saveProduct();
        }
    }

    public void showProductsListView(){
        productsListLV.getItems().clear();
        productsListLV.getItems().addAll(dataSource.getProducts().getProductByNameStore(dataSource.getUserList().getCurrUser().getStoreName()));
        productsListLV.setCellFactory(productListView -> new ProductListCell(productListView));
        productsListLV.refresh();
    }

    public void showSelectedProduct(Product product){
        selectedProduct = product;
        productsListLV.refresh();
        stockWarningSelectedProductSVG.setVisible(currUser.getStore().stockIsLow(product));
        nameProductLB.setText(product.getName());
        priceLB.setText(String.format("%.2f",product.getPrice()));
        stockLB.setText(String.format("%d",product.getStock()));
        rateLB.setText(String.format("%.2f/5",product.getRating()));
        detailsLB.setText(product.getDetails());

        if (selectedProductResizeableImageView == null) {
            selectedProductResizeableImageView = new ResizeableImageView(new Image(product.getPicturePath()));
            selectedProductResizeableImageView.fitWidthProperty().bind(rightProductVB.widthProperty());
            ImageViewVBox.getChildren().add(selectedProductResizeableImageView);
        }

        selectedProductResizeableImageView.setImage(new Image(product.getPicturePath()));
        ratingStarsSelectedProduct.getChildren().add(new RatingStars(product.getRating()));
    }

    public void handleProductsListView(){
        productsListLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    ratingStarsSelectedProduct.getChildren().clear();
                    showSelectedProduct(newValue);
                    productSP.setDividerPositions(0.5, 0.5);
                    productsRightPane.setVisible(newValue != null);
                    product = newValue;
                }
        );
    }

    public void showOrderListView(ArrayList<Order> orderArrayList){
        orderLV.getItems().clear();
        orderLV.getItems().addAll(orderArrayList);
        orderLV.setCellFactory(orderListView -> new OrderListCell(dataSource));
        orderLV.refresh();
    }

    public void showCouponListView(ArrayList<Coupon> couponTypeArrayList){
        couponsLV.setCellFactory(couponsLV -> new PromotionListCell());
        couponsLV.getItems().clear();
        couponsLV.getItems().addAll(couponTypeArrayList);
        couponsLV.refresh();
    }

    public void handleAllBtn(){
        showOrderListView(dataSource.getOrders().getOrderList(new OrderByStoreGetter(currUser.getStoreName())));
    }

    public void handleToShipBtn(){
        showOrderListView(dataSource.getOrders().getOrderList(new ToShipOrderByStoreGetter(currUser.getStoreName())));
    }

    public void handleShippedBtn(){
        showOrderListView(dataSource.getOrders().getOrderList(new ShippedOrderByStoreGetter(currUser.getStoreName())));
    }

    public void handleChangeStockLowerBoundWarning() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setGraphic(null);
        dialog.setHeaderText(null);
        dialog.setTitle("Set Lower Bound");
        dialog.setContentText("Enter your lower bound stock warning");

        Optional<String> newLower = dialog.showAndWait();
        newLower.ifPresent(s -> currUser.getStore().setStockLowerBound(Integer.parseInt(s)));
        newLower.ifPresent(s -> numberLowerLabel.setText(s));
        dataSource.saveStore();
        productsListLV.refresh();
    }

    @FXML
    private void handleUploadNewPictureForSelectedProduct() {
        ImageUploader imageUploader = new ImageUploader(usernameLabel.getScene().getWindow(), "images/product_images");
        if (!imageUploader.show("Upload new picture for " + selectedProduct.getName()))
            return;

        try {
            Image uploadedImage = new Image(new FileInputStream(imageUploader.getUploadedFile()));
            PictureConfirmDialog dialog = new PictureConfirmDialog(uploadedImage);
            Optional<Boolean> result = dialog.showAndWait();

            if (result.isPresent() && result.get()) {
                imageUploader.saveImageFile();
                selectedProduct.setPictureName(imageUploader.getDestinationFile().getFileName().toString());
                dataSource.saveProduct();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        showSelectedProduct(selectedProduct);
    }

    public void handleCreateCouponBtn(ActionEvent actionEvent) {
        try{
            FXRouter.goTo("create_coupon",dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
