package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.models.Category;
import ku.cs.models.Product;
import ku.cs.models.Store;
import ku.cs.models.StoreList;
import ku.cs.service.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public class MyStoreController  {
    DataSource dataSource;
    Product product;
    private File file;
    private Path target;

    @FXML Label usernameLabel;
    @FXML Label nameLabel;
    @FXML Label nameStoreLabel;
    @FXML TabPane myStoreTP;
    @FXML ImageView userImage;
    @FXML ChoiceBox<String> categoryCB, subCategoryCB;
    @FXML TextField valueTF, nameProductTF, priceTF, stockTF;
    @FXML TextArea descriptionTF;
    @FXML ImageView pictureViewIV;
    @FXML Button selectProductPictureBtn;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        usernameLabel.setText(dataSource.getAccounts().getCurrUser().getUsername());
        nameStoreLabel.setText(dataSource.getAccounts().getCurrUser().getStoreName());
        nameLabel.setText(dataSource.getAccounts().getCurrUser().getName());
        userImage.setImage(new Image(dataSource.getAccounts().getCurrUser().getPicturePath()));
        loadCategory();
    }

    @FXML
    public void handleBackBtn(ActionEvent event){
        try {
            FXRouter.goTo("marketplace",dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddProductBtn(ActionEvent event){
        myStoreTP.getSelectionModel().select(0);
    }
    @FXML
    public void handleListProductBtn(ActionEvent event){
        myStoreTP.getSelectionModel().select(1);
    }

    public void loadCategory(){
        ObservableList<String> list = FXCollections.observableArrayList(dataSource.getCategories());
        categoryCB.setItems(list);

        categoryCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loadSubCategory(list.get(t1.intValue()));
            }
        });
    }

    public void loadSubCategory(String category){
        ObservableList<String> list = FXCollections.observableArrayList(dataSource.getSubCategory(category));
        subCategoryCB.setItems(list);
    }

    @FXML public void handleAddBtn(){
        String value = valueTF.getText();
        if(!value.equals("")) {
            valueTF.setText("");
            String category = categoryCB.getSelectionModel().getSelectedItem().toString();
            String subCategory = subCategoryCB.getSelectionModel().getSelectedItem().toString();
            product.addSubCategory(category,subCategory,value);
        }
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
        }
    }

    public void handleSelectProductPicture(ActionEvent event) throws FileNotFoundException {

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        file = chooser.showOpenDialog(selectProductPictureBtn.getScene().getWindow());

        if (file != null){
            File destDir = new File("images");
            destDir.mkdirs();

            Image uploadedImage = new Image(new FileInputStream(file.getPath()));
            String[] fileSplit = file.getName().split("\\.");
            String filename = LocalDate.now()
                    + "_" + System.currentTimeMillis()
                    + "." + fileSplit[fileSplit.length - 1];

            target = FileSystems.getDefault().getPath(
                    destDir.getAbsolutePath()
                            + System.getProperty("file.separator")
                            + filename);

            pictureViewIV.setImage(uploadedImage);
        }
    }

}
