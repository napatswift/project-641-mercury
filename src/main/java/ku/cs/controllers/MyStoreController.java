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
import ku.cs.models.Category;
import ku.cs.models.Product;
import ku.cs.models.Store;
import ku.cs.models.StoreList;
import ku.cs.service.DataSource;
import java.io.IOException;
import java.util.ArrayList;

public class MyStoreController  {
    DataSource dataSource;
    ArrayList<String> subcategoryList = new ArrayList<>();
    @FXML Label usernameLabel;
    @FXML Label nameLabel;
    @FXML Label nameStoreLabel;
    @FXML TabPane myStoreTP;
    @FXML ImageView userImage;
    @FXML ChoiceBox<String> categoryCB, subCategoryCB;
    @FXML TextField valueTF;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        usernameLabel.setText(dataSource.getAccounts().getCurrAccount().getUsername());
        nameStoreLabel.setText(dataSource.getAccounts().getCurrAccount().getStoreName());
        nameLabel.setText(dataSource.getAccounts().getCurrAccount().getName());
        userImage.setImage(new Image(dataSource.getAccounts().getCurrAccount().getPicturePath()));
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
        System.err.println(value);
    }

}
