package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import ku.cs.models.*;
import ku.cs.models.components.ProductListCell;
import ku.cs.models.components.RatingStars;
import ku.cs.models.components.ResizeableImageView;
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

public class MyStoreController  {
    DataSource dataSource;
    Product product;
    Image image;
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
    @FXML ListView<Category> categoryLV;
    @FXML ImageView productIV;
    @FXML ListView<Product> productsListLV;
    @FXML ListView<Order> orderLV;
    @FXML Label rateLB, detailsLB;
    @FXML
    TextField nameProductLB, priceLB, stockLB;
    @FXML
    VBox rightProductVB, ImageViewVBox;
    @FXML
    SplitPane productSP;
    @FXML
    HBox ratingStarsSelectedProduct;
    @FXML
    SVGPath stockWarningSelectedProductSVG;
    @FXML
    AnchorPane productsRightPane;
    @FXML
    ToggleButton myAccountMenuBtn, myStoreMenuBtn, productsMenuBtn, ordersMenuBtn;

    ResizeableImageView selectedProductResizeableImageView;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        dataSource.parseOrder();
        orders = dataSource.getOrders().getOrdersByStore(dataSource.getUserList().getCurrUser().getStoreName());
        User currUser = dataSource.getUserList().getCurrUser();
        usernameLabel.setText("@" + currUser.getUsername());
        nameStoreLabel.setText(currUser.getStoreName());
        nameLabel.setText(currUser.getName());
        userImage.setImage(new Image(currUser.getPicturePath()));
        userImage.setClip(new Circle(25, 25, 25));
        loadCategory();
        handleListProductBtn();

        productsRightPane.setVisible(false);

        showProductsListView();
        clearSelectedProduct();
        handleProductsListView();
        setGroup();
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
    public void handleBackBtn(ActionEvent event){
        try {
            FXRouter.goTo("marketplace",dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleListProductBtn(){
        myStoreTP.getSelectionModel().select(0);
    }
    public void handleAddProductBtn(){
        product = new Product("", "", dataSource.getUserList().getCurrUser().getStore());
        myStoreTP.getSelectionModel().select(1);
    }
    public void handleOrdersBtn(){
        myStoreTP.getSelectionModel().select(4);
        showOrderListView();
        handleOrderListView();
    }


    public void loadCategory(){
        ObservableList<String> list = FXCollections.observableArrayList(dataSource.getCategories());
        categoryCB.setItems(list);

        categoryCB.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> loadSubCategory(list.get(t1.intValue())));
    }

    public void loadSubCategory(String category){
        ObservableList<String> list = FXCollections.observableArrayList(dataSource.getSubCategory(category));
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

            categoryLV.getItems().addAll(product.getCategories());
            categoryLV.refresh();

            productIV.setImage(image);
        }
    }

    public void handleSelectProductPicture(MouseEvent event) throws FileNotFoundException {
        ImageView imageBtn = (ImageView) event.getSource();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        file = chooser.showOpenDialog(imageBtn.getScene().getWindow());

        if (file != null){
            File destDir = new File("images"+ File.separator + "product_images");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            Image uploadedImage = new Image(new FileInputStream(file.getPath()));
            String[] fileSplit = file.getName().split("\\.");
            String filename = LocalDate.now()
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
        handleAddProductBtn();
    }

    public void handleEditBtn(){
        product.setName(nameProductLB.getText());
        product.setPrice(Double.parseDouble(priceLB.getText()));
        product.setStock(Integer.parseInt(stockLB.getText()));
        dataSource.saveProduct();
    }

    public void showProductsListView(){
        productsListLV.getItems().addAll(dataSource.getProductByNameStore
                (dataSource.getUserList().getCurrUser().getStoreName()));
        productsListLV.setCellFactory(productListView -> new ProductListCell(productListView));
        productsListLV.refresh();
    }

    public void showSelectedProduct(Product product){
        stockWarningSelectedProductSVG.setVisible(product.stockIsLow());
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

    public void showOrderListView(){
        orderLV.getItems().addAll(orders);
        orderLV.refresh();
    }

    public void showSelectOrder(Order order){

    }

    public void handleOrderListView(){
        orderLV.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) ->{
                    showSelectOrder(newValue);
                }
        );
    }






    public void clearSelectedProduct(){
        nameProductLB.setText("");
        priceLB.setText("");
        stockLB.setText("");
        rateLB.setText("");
        detailsLB.setText("");
    }
}
