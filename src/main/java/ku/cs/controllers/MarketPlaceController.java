package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
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
import java.util.AbstractMap;
import java.util.HashMap;

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

    private boolean bodyToggle = false;
    private Product selectedProduct;

    private AbstractMap<String, Product> products;
    private VBox getCard(String name, double price, String imagePath, String id){
        int cardWidth = 250;
        int width = cardWidth;
        int height = width * 80 / 100;
        ImageView image = new ImageView();
        image.setFitWidth(width);
        image.setFitHeight(height);
        Image productImage = new Image(imagePath);
        PixelReader pixelReader = productImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) productImage.getWidth(),
                (int) (productImage.getHeight() * height / width ));
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

        card.setOnMouseReleased(this::productOnClick);
        return card;
    }

    private void buildProductPage(){
        productNameLabel.setText(selectedProduct.getName());
        productPriceLabel.setText("$"+selectedProduct.getPrice());
        productDetailLabel.setText(selectedProduct.getDetails());
        selectedProductIV.setImage(new Image(selectedProduct.getPicturePath()));
    }

    @FXML private void handleMargetPlaceBtn(ActionEvent e){
        productTP.getSelectionModel().select(0);
    }

    @FXML private void productOnClick(MouseEvent event) {
        VBox vBox = (VBox) event.getSource();
        selectedProduct = products.get(vBox.getId());
        buildProductPage();
        productTP.getSelectionModel().select(1);
    }

    @FXML private void handleCategoryBtn(ActionEvent event){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(200));
        translateTransition.setNode(bodyAP);
        if (bodyToggle == false) {
            translateTransition.setByY(100);
            bodyToggle = true;
        } else{
            translateTransition.setByY(-100);
            bodyToggle = false;
        }
        translateTransition.play();
    }

    private void parseProducts() throws IOException{
        String [] lines = CsvReader.getLines("data/products.csv");
        for(String line: lines){
            String [] entry = line.split(";");
            //name,picturePath,details,price,stock,id,rating,review
            products.put(entry[1],
                    new Product(entry[0], entry[9], entry[6], Double.parseDouble(entry[2]) / 100, 999, entry[1], Double.parseDouble(entry[7]), Integer.parseInt(entry[8])));
        }
    }

    private void populateProduct(int amount){
        for(String key: products.keySet()){
            VBox card = getCard(products.get(key).getName(), products.get(key).getPrice(),
                    products.get(key).getPicturePath(), products.get(key).getId());
            productFlowPane.getChildren().add(card);

        }
    }

    @FXML
    public void seeMore(ActionEvent event){
        populateProduct(25);
    }

    @FXML
    public void initialize() throws IOException {
        products = new HashMap();
        parseProducts();
        populateProduct(15);
    }

}
