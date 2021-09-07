package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.TextField;
import ku.cs.models.Store;
import ku.cs.models.StoreList;
import ku.cs.service.DataSource;

import java.io.IOException;

public class CreateStoreController {
    private StoreList stores;

    @FXML
    TextField nameStoreTF;
    private DataSource dataSource;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        dataSource.parseStore();
        this.stores = dataSource.getStores();
    }

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleTF(ActionEvent event){
        String nameStore = nameStoreTF.getText();
        TextField textField = (TextField) event.getSource();
        textField.getStyleClass().removeAll("error-outline-text-field");
        textField.getStyleClass().add("outline-text-field");

        if(stores.isExit(nameStore)){

        }


    }

    @FXML
    public void handleSubmit(ActionEvent event) throws IOException {
        String nameStore = nameStoreTF.getText();
        Store newStore = new Store(nameStore, dataSource.getAccounts().getCurrUser().getUsername());
        newStore.toCsv();
        dataSource.getAccounts().getCurrUser().createStore(nameStore);
        dataSource.saveAccount();

        try {
            FXRouter.goTo("my_store",dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }





}
