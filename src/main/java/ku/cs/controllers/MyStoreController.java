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
import javafx.stage.FileChooser;
import ku.cs.models.*;
import ku.cs.models.User;
import ku.cs.models.Category;
import ku.cs.models.CategoryList;
import ku.cs.models.components.*;
import ku.cs.models.components.dialogs.ConfirmEditProductDialog;
import ku.cs.models.components.dialogs.PictureConfirmDialog;
import ku.cs.models.components.listCell.OrderListCell;
import ku.cs.models.components.listCell.ProductListCell;
import ku.cs.service.DataSource;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class MyStoreController  {
    private DataSource dataSource;
    private Product product;
    private Image image;
    private User currUser;
    private File file;
    private Path target;
    private ArrayList<Order> orders;

    @FXML Label usernameLabel, nameLabel, nameStoreLabel;
    @FXML TabPane myStoreTP;
    @FXML ImageView userImage;
    @FXML ChoiceBox<String> categoryCB, subCategoryCB;
    @FXML TextField valueTF, nameProductTF, priceTF, stockTF;
    @FXML TextArea descriptionTF;
    @FXML ImageView pictureViewIV;
    @FXML Label nameProductLabel, priceLabel, stockLabel, descriptionLabel;
    @FXML HBox newProductCategoryHBox;
    @FXML ImageView productIV;
    @FXML ListView<Product> productsListLV;
    @FXML ListView<Order> orderLV;
    @FXML Label rateLB, detailsLB,numberLowerLabel;
    @FXML TextField nameProductLB, priceLB, stockLB;
    @FXML VBox rightProductVB, ImageViewVBox;
    @FXML SplitPane productSP;
    @FXML HBox ratingStarsSelectedProduct;
    @FXML SVGPath stockWarningSelectedProductSVG;
    @FXML AnchorPane productsRightPane;
    @FXML ToggleButton myAccountMenuBtn, myStoreMenuBtn, productsMenuBtn, ordersMenuBtn;

    private Tab myStoreTab, myAccountTab;

    ResizeableImageView selectedProductResizeableImageView;
    Product selectedProduct;
    CategoryListPane newProductCategoryListPane;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        currUser = dataSource.getUserList().getCurrUser();
        dataSource.parseOrder();
        orders = dataSource.getOrders().getOrdersByStore(dataSource.getUserList().getCurrUser().getStoreName());
        setupUserInfo();
        loadCategory();
        handleListProductBtn();

        productsRightPane.setVisible(false);
        newProductCategoryListPane = new CategoryListPane();
        newProductCategoryHBox.getChildren().add(newProductCategoryListPane);

        showProductsListView();
        handleProductsListView();
        setupTabPaneListener();
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
        myStoreTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleAddProductBtn(){
        product = new Product("", "", dataSource.getUserList().getCurrUser().getStore());
        myStoreTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleOrdersBtn(){
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
        String value = valueTF.getText();
        valueTF.setText("");
        String category = categoryCB.getSelectionModel().getSelectedItem();
        String subCategory = subCategoryCB.getSelectionModel().getSelectedItem();
        product.addSubCategory(category,subCategory,value);
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

            productIV.setImage(image);
        }
    }

    @FXML
    public void handleSelectProductPicture() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        file = chooser.showOpenDialog(pictureViewIV.getScene().getWindow());

        if (file != null){
            File destDir = new File("images"+ File.separator + "product_images");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            Image uploadedImage = null;

            try {
                uploadedImage = new Image(new FileInputStream(file.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String[] fileSplit = file.getName().split("\\.");
            String filename = "PRODUCT_IMG" +LocalDate.now()
                    + "_" + System.currentTimeMillis()
                    + "." + fileSplit[fileSplit.length - 1];

            target = FileSystems.getDefault().getPath(
                    destDir.getAbsolutePath()
                            + File.separator
                            + filename);

            pictureViewIV.setImage(uploadedImage);
            image = uploadedImage;
        }
    }

    public void handleConfirmBtn(){
        if (file != null) {
            try {
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                product.setPictureName(target.getFileName().toString());
                dataSource.getProducts().addProduct(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataSource.saveProduct();
        myStoreTP.getSelectionModel().select(0);
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

    public void handleAllBtn(){
        showOrderListView(orders);
    }

    public void handleToShipBtn(){
        showOrderListView(OrderList.getToShipOrder(orders));
    }

    public void handleShippedBtn(){
        showOrderListView(OrderList.getShipedOrder(orders));
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
    private void handleUploadNewPictureForProduct() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(pictureViewIV.getScene().getWindow());
        
        if (file == null) return;

        String[] fileSplit = file.getName().split("\\.");

        File destDir = new File("images" + File.separator + "product_images");

        if (!destDir.exists()) {
            if (!destDir.mkdirs()) return;
        }

        try {
            Image uploadedImage = new Image(new FileInputStream(file.getPath()));
            PictureConfirmDialog dialog = new PictureConfirmDialog(uploadedImage);
            Optional<Boolean> result = dialog.showAndWait();

            if (result.isPresent() && result.get()) {
                String filename = "PRODUCT_IMG" + LocalDate.now()
                        + "_" + System.currentTimeMillis()
                        + "." + fileSplit[fileSplit.length - 1];

                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()
                                + File.separator
                                + filename);

                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                selectedProduct.setPictureName(target.getFileName().toString());
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
}
