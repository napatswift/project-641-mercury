package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ku.cs.models.CsvReader;
import ku.cs.models.Product;

import java.io.IOException;
import java.util.*;

public class MarketPlaceController {
    @FXML
    AnchorPane bodyAP;
    @FXML
    ScrollPane productListSP;
    @FXML
    TabPane productTP;
    @FXML
    Label productNameLabel, productPriceLabel, productDetailLabel;
    @FXML
    ImageView selectedProductIV;
    @FXML
    FlowPane productFlowPane;
    @FXML
    Button seeMoreBtn;
    @FXML
    MenuButton sortingMB;

    private boolean bodyToggle = false;
    private Product selectedProduct;
    private int productIndex = -1;

    private ArrayList<Product> allProducts;
    private ArrayList<Product> selectedProducts;

    private VBox buildCard(String name, double price, String imagePath, String id){
        int cardWidth = 250;
        int height = cardWidth * 80 / 100;
        ImageView image = new ImageView();
        image.setFitWidth(cardWidth);
        image.setFitHeight(height);
        Image productImage = new Image(imagePath);
        PixelReader pixelReader = productImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) productImage.getWidth(),
                (int) (productImage.getHeight() * height / cardWidth));
        image.setImage(croppedImage);

        Label productNameLabel = new Label(name);
        Label priceLabel = new Label("$" + String.format("%.2f", price));
        productNameLabel.getStyleClass().add("subtitle1");
        priceLabel.getStyleClass().add("h6");

        VBox productVbox = new VBox(productNameLabel, priceLabel);
        productVbox.setAlignment(Pos.CENTER_LEFT);
        productVbox.setSpacing(3);

        HBox hBox = new HBox(productVbox);
        hBox.setSpacing(3);
        hBox.setPrefHeight(30);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        VBox card = new VBox(image, hBox);
        card.setPrefWidth(cardWidth);
        card.setAccessibleRole(AccessibleRole.BUTTON);
        card.setCursor(Cursor.HAND);
        card.getStyleClass().add("product-card");
        card.setId(id);

        card.setOnMouseReleased(this::handleProductCard);
        return card;
    }

    private void buildProductPage(){
        productNameLabel.setText(selectedProduct.getName());
        productPriceLabel.setText("$"+selectedProduct.getPrice());
        productDetailLabel.setText(selectedProduct.getDetails());
        selectedProductIV.setImage(new Image(selectedProduct.getPicturePath()));
    }

    @FXML
    private void handleMargetPlaceBtn(ActionEvent e){
        productTP.getSelectionModel().select(0);
    }

    private Product getProductById(String id){
        for(Product product: selectedProducts){
            if(product.getId().equals(id))
                return product;
        }
        return null;
    }

    @FXML
    private void handleProductCard(MouseEvent event) {
        VBox vBox = (VBox) event.getSource();
        selectedProduct = getProductById(vBox.getId());
        if(selectedProduct == null)
            return;
        buildProductPage();
        productTP.getSelectionModel().select(1);
    }

    @FXML
    private void handleCategoryBtn(ActionEvent event){
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

    public void handleSeeMoreBtn(ActionEvent e){
        populateProduct(10);
    }

    private void parseProducts() throws IOException{
        String [] lines = CsvReader.getLines("data/products.csv");
        for(String line: lines){
            String [] entry = line.split(";");
            //name,picturePath,details,price,stock,id,rating,review,rolloutDate
            selectedProducts.add(
                    new Product(entry[0],
                            entry[9],
                            entry[6],
                            Double.parseDouble(entry[2]) / 100, 999,
                            entry[1],
                            Double.parseDouble(entry[7]),
                            Integer.parseInt(entry[8]),
                            entry[10])
            );
        }
    }

    private void populateProduct(int amount){
        int i = 0;
        for(Product product: selectedProducts){
            if(i > productIndex && i <= productIndex + amount) {
                VBox card = buildCard(product.getName(), product.getPrice(),
                        product.getPicturePath(), product.getId());
                productFlowPane.getChildren().add(card);
            }
            i++;
        }
        productIndex += amount;
    }

    @FXML
    private void sortProductBy(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        selectedProducts = allProducts;
        if (menuItem.getId().equals("lowestPrice")){
            sortingMB.setText("SORT BY : LOWEST PRICE");
            selectedProducts.sort(Comparator.comparingDouble(Product::getPrice));
        } else if (menuItem.getId().equals("highestPrice")){
            selectedProducts.sort(Comparator.comparingDouble(Product::getPrice));
            sortingMB.setText("SORT BY : HIGHEST PRICE");
            Collections.reverse(selectedProducts);
        } else{
            selectedProducts.sort(Comparator.comparing(Product::getRolloutDate));
        }

        productFlowPane.getChildren().clear();
        int temp = productIndex;
        productIndex = 0;
        populateProduct(temp);
    }

    @FXML
    public void initialize() throws IOException {
        selectedProducts = new ArrayList<>();
        parseProducts();
        selectedProducts.sort(Comparator.comparing(Product::getRolloutDate));
        allProducts = selectedProducts;
        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);
    }

}
