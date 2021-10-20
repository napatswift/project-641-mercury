package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.TextField;
import ku.cs.models.Store;
import ku.cs.models.StoreList;
import ku.cs.models.components.dialogs.AlertDialog;
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
    public void handleBack() {
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSubmit() {
        String nameStore = nameStoreTF.getText();
        if(stores.findStoreByName(nameStore) != null) {
            AlertDialog.alertDialog("Unable to create a store.", "This name is already in use.");
            return;
        }
        dataSource.getUserList().getCurrUser().createStore(nameStore);
        dataSource.saveAccount();
        stores.addStore(dataSource.getUserList().getCurrUser().getStore());
        dataSource.saveStore();

        try {
            FXRouter.goTo("my_store", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า my_store ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }





}
