package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
            starBtn1, starBtn2, starBtn3, starBtn4, starBtn5;
    @FXML
    MenuButton sortingMB;
    @FXML
    TextField upperBoundTF, lowerBoundTF, amountTF;
    @FXML
    HBox starsHBox, shipHBox;
    @FXML
    VBox reviewVBox;

    private boolean bodyToggle = false;
    private Product selectedProduct;
    private int productIndex = -1;
    private ComponentBuilder componentBuilder = new ComponentBuilder();
    private double upperBoundParsed = Double.MAX_VALUE, lowerBoundParsed = 0;

    private ArrayList<Product> products;
    private ArrayList<Review> reviews;

    private void buildProductPage(){
        resetStar();
        // reset amount to 1
        amountTF.setText("1");
        // clear boxes
        starsHBox.getChildren().clear();
        shipHBox.getChildren().clear();

        // set product information
        productNameLabel.setText(selectedProduct.getName());
        productPriceLabel.setText("$"+selectedProduct.getPrice());
        productDetailLabel.setText(selectedProduct.getDetails());
        selectedProductIV.setImage(new Image(selectedProduct.getPicturePath()));

        // set store name;
        storeNameLabel.setText(selectedProduct.getStore().getName().toUpperCase(Locale.ROOT));

        // set bread crumbs info
        categoryBreadcrumbsLabel.setText(selectedProduct.getCategory().getName());
        productNameBreadcrumbsLabel.setText(selectedProduct.getName());

        // handling in stock label and icon
        amountInStockLabel.setText("" + selectedProduct.getStock() + " in stock");
        if (selectedProduct.getStock() < 5){
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

        // handling rating
        ComponentBuilder.starsRating(starsHBox, selectedProduct.getRating());
        starsHBox.getChildren().add(new Label(selectedProduct.getRating()+"/5 (" + selectedProduct.getReview() + ")"));

        // handling category
        Category category = selectedProduct.getCategory();
        Label categoryLabel = new Label(category.getName());
        categoryLabel.getStyleClass().add("subtitle1");
        shipHBox.getChildren().add(categoryLabel);
        for (SubCategory subCategory: category.getSubCategories()){
            shipHBox.getChildren().add(componentBuilder.ship(subCategory.getName(), subCategory.getValue()));
        }

        populateReview();
    }

    private void resetStar(){
        star1.setStyle("-fx-fill: primary-overlay");
        star2.setStyle("-fx-fill: primary-overlay");
        star3.setStyle("-fx-fill: primary-overlay");
        star4.setStyle("-fx-fill: primary-overlay");
        star5.setStyle("-fx-fill: primary-overlay");
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

    }

    private void populateReview(){
        reviewVBox.getChildren().clear();
        for(Review review: reviews){
            if(review.getProductId().equals(selectedProduct.getId())) {
                reviewVBox
                        .getChildren()
                        .add(componentBuilder.reviewCard(review));
            }
        }
    }

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
            if(selectedProduct.getStock() > amount)
                amount++;
        } else if (button.getId().equals("decrease")){
            if(amount > 1)
                amount--;
        }
        if (amount > selectedProduct.getStock()) {
            amount = selectedProduct.getStock();
        }
        amount = Math.max(amount, 1);
        amountTF.setText(amount + "");
    }

    @FXML
    private void handleMargetPlaceBtn(MouseEvent e){
        productTP.getSelectionModel().select(0);
    }

    private Product getProductById(String id){
        for(Product product: products){
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

    public void handleSeeMoreBtn(ActionEvent e){
        populateProduct(10);
    }

    private void parseProducts() throws IOException{
        String [] lines = CsvReader.getLinesWithHeader("data/products.tsv");
        String [] header = lines[0].split("\t");
        lines = Arrays.copyOfRange(lines, 1, lines.length);
        for(String line: lines){
            String [] entry = line.split("\t");
            int entry_len = entry.length;
            //name,picturePath,details,price,stock,id,rating,review,rolloutDate
            String name = entry[0];
            String id = entry[1];
            double price = Double.parseDouble(entry[2]) / 100;
            Store store = new Store(entry[3]);
            int stock = Integer.parseInt(entry[4]);
            String details = entry[5];
            double rating = Double.parseDouble(entry[6]);
            int review = Integer.parseInt(entry[7]);
            String picturePath = entry[8];
            String rolloutDate = entry[9];
            Product newProduct =
                    new Product(name, picturePath, details,
                                price, stock, id, rating, review, rolloutDate, store);
            for(int idx = 10; idx < entry_len; idx++){
                String [] col = header[idx].split("-");
                newProduct.addSubCategory(col[0], col[1], entry[idx]);
            }
            products.add(newProduct);

        }
    }

    private void parseReview() throws IOException{
        String [] lines = CsvReader.getLines("data/reviews.csv");
        for(String line: lines){
            // productId,title,detail,rating,reviewerUsername
            String [] entry = line.split(",");
            String productId = entry[0];
            String title = entry[1];
            String detail = entry[2];
            int rating = Integer.parseInt(entry[3]);
            String reviewerUsername = entry[4];
            Product product = getProductById(productId);
            reviews.add(new Review(title, detail, rating, reviewerUsername, product));
        }
    }

    private void populateProduct(int amount){
        int i = 0;
        for(Product product: products){
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
    private void sortProductBy(ActionEvent e) {
        MenuItem menuItem = (MenuItem) e.getSource();
        if (menuItem.getId().equals("lowestPrice")){
            sortingMB.setText("SORT BY : LOWEST PRICE");
            products.sort(Comparator.comparingDouble(Product::getPrice));
        } else if (menuItem.getId().equals("highestPrice")){
            products.sort(Comparator.comparingDouble(Product::getPrice));
            sortingMB.setText("SORT BY : HIGHEST PRICE");
            Collections.reverse(products);
        } else{
            products.sort(Comparator.comparing(Product::getRolloutDate));
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

    @FXML
    public void initialize() throws IOException {
        products = new ArrayList<>();
        reviews = new ArrayList<>();
        parseProducts();
        parseReview();
        products.sort(Comparator.comparing(Product::getRolloutDate));
        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);
    }

}
