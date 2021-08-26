package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import ku.cs.service.ProductDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
            starBtn1, starBtn2, starBtn3, starBtn4, starBtn5,
            submitReviewBtn;
    @FXML
    MenuButton sortingMB;
    @FXML
    TextField upperBoundTF, lowerBoundTF, amountTF,
            reviewTitleTF;
    @FXML
    TextArea detailReviewTA;
    @FXML
    HBox starsHBox, shipHBox,
        reviewRatingPanelStarHBox;
    @FXML
    VBox reviewVBox;

    private boolean bodyToggle = false;
    private Product selectedProduct;
    private int productIndex = -1;
    private ComponentBuilder componentBuilder = new ComponentBuilder();
    private double upperBoundParsed = Double.MAX_VALUE, lowerBoundParsed = 0;
    private int newReviewRating = -1;

    private ProductList productList;
    private ArrayList<Review> reviews;

    // Utility
    private Product getProductById(String id){
        for(Product product: products){
            if(product.getId().equals(id))
                return product;
        }
        return null;
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

    private void reviewToCsv(String filePath){
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append("productId,title,detail,rating,reviewerUsername");
            writer.newLine();
            for(Review review: reviews){
                writer.append(review.toCsv());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }

    // product page builder
    private void buildProductPage(){
        resetStar();
        // reset amount to 1
        amountTF.setText("1");
        // clear boxes
        shipHBox.getChildren().clear();

        populateReview();

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

        // handling category
        Category category = selectedProduct.getCategory();
        Label categoryLabel = new Label(category.getName());
        categoryLabel.getStyleClass().add("subtitle1");
        shipHBox.getChildren().add(categoryLabel);
        for (SubCategory subCategory: category.getSubCategories()){
            shipHBox.getChildren().add(componentBuilder.ship(subCategory.getName(), subCategory.getValue()));
        }
    }

    private void resetStar(){
        star1.setStyle("-fx-fill: primary-overlay");
        star2.setStyle("-fx-fill: primary-overlay");
        star3.setStyle("-fx-fill: primary-overlay");
        star4.setStyle("-fx-fill: primary-overlay");
        star5.setStyle("-fx-fill: primary-overlay");
    }

    private void resetReviewForm(){
        resetStar();
        reviewTitleTF.setText("");
        detailReviewTA.setText("");
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

    private void populateReview(){
        //TODO: move to model
        reviewRatingPanelStarHBox.getChildren().clear();

        reviewVBox.getChildren().clear();
        int sumOfRating = 0;
        int n = 0;
        for(Review review: reviews){
            if(review.getProductId().equals(selectedProduct.getId())) {
                reviewVBox
                        .getChildren()
                        .add(componentBuilder.reviewCard(review));
                sumOfRating += review.getRating();
                n++;
            }
        }

        if (n == 0){
            Label noReviewLabel = new Label("No review yet! You'll first!");
            noReviewLabel.setPadding(new Insets(10));
            reviewVBox.getChildren().add(noReviewLabel);
        }

        double rating = n == 0 ? 0 : (double) sumOfRating / n;

        ComponentBuilder.starsRating(reviewRatingPanelStarHBox, rating);
        reviewRatingPanelStarHBox.getChildren().add(new Label(rating + "/5 (" + n + ")"));
        selectedProduct.setReview(n);
        selectedProduct.setRating(rating);

        // handling product rating
        starsHBox.getChildren().clear();
        ComponentBuilder.starsRating(starsHBox, selectedProduct.getRating());
        starsHBox.getChildren().add(new Label(selectedProduct.getRating()+"/5 (" + selectedProduct.getReview() + ")"));

    }

    // handler
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

    @FXML
    private void handleSubmitReviewBtn(ActionEvent e){
        // TODO remove dummy username
        // TODO: move to model
        String title = reviewTitleTF.getText().trim();
        String detail = detailReviewTA.getText().trim();
        if (title.equals("") || detail.equals("") || newReviewRating == -1){
            return;
        }
        Review newReview = new Review(title, detail, newReviewRating, "napat123", selectedProduct);
        reviews.add(newReview);
        populateReview();
        resetReviewForm();
        reviewToCsv("data/dev/review.csv");
        productToCsv("data/dev/products.tsv");
        newReviewRating = -1;
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

    @FXML
    public void handleSeeMoreBtn(ActionEvent e){
        populateProduct(10);
    }

    // parsing function

    private void parseReview() throws IOException{
        // TODO: move to data source
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

    // marketplace page
    private void populateProduct(int amount){
        int i = 0;
        for(Product product: productList){
            // TODO: move to models
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
    public void initialize() throws IOException {
        parseReview();
        ProductDataSource productDataSource = new ProductDataSource("data/product.tsv");
        productList = productDataSource.parseProductList("\t");

        // TODO move to model
        // productList.sort(Comparator.comparing(Product::getRolloutDate));

        populateProduct(15);
        seeMoreBtn.setOnAction(this::handleSeeMoreBtn);
    }

}
