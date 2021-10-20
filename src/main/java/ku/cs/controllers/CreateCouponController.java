package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ku.cs.models.Store;
import com.github.saacsos.FXRouter;
import ku.cs.models.components.dialogs.AlertDialog;
import ku.cs.models.coupon.CouponList;
import ku.cs.service.DataSource;

import java.io.IOException;

public class CreateCouponController {
    private DataSource dataSource;
    private CouponList coupons;

    @FXML ChoiceBox<String> minimumStatusBox,
            discountStatusBox;

    @FXML TextField codeTF,
    minimumTF,
    discountTF;

    @FXML
    public void initialize(){
        setStatusBox();
        dataSource = (DataSource) FXRouter.getData();
        coupons = dataSource.getCoupons();
    }

    public void setStatusBox(){
        ObservableList<String> minimumStatusList = FXCollections.observableArrayList("Minimum Cost","Minimum Pieces");
        minimumStatusBox.setItems(minimumStatusList);
        minimumStatusBox.setValue("Minimum Cost");

        ObservableList<String> discountStatusList = FXCollections.observableArrayList("Discount Cost","Discount Percent");
        discountStatusBox.setItems(discountStatusList);
        discountStatusBox.setValue("Discount Cost");
    }

    public void handleCreateCoupon(ActionEvent actionEvent) {
        if(codeTF.getText().isEmpty()){
            AlertDialog.alertDialog("This coupon cannot be created.", "Please enter code");
            return;
        }

        if(coupons.checkCouponCode(codeTF.getText())) {
            AlertDialog.alertDialog("This coupon cannot be created.", "This coupon code has already been used.");
            return;
        }

        try {
            String code = codeTF.getText();
            Store owner = dataSource.getUserList().getCurrUser().getStore();
            Double discount = discountStatusBox.getValue().equals("Discount Cost") ? Double.parseDouble(discountTF.getText()) : null;
            Double percentDiscount = discountStatusBox.getValue().equals("Discount Percent") ? (Double.parseDouble(discountTF.getText()) / 100) : null;
            Double minimumValue = minimumStatusBox.getValue().equals("Minimum Cost") ? Double.parseDouble(minimumTF.getText()) : null;
            Integer minimumQuantity = minimumStatusBox.getValue().equals("Minimum Pieces") ? Integer.parseInt(minimumTF.getText()) : null;

            if(!checkCreateCoupon(discount,percentDiscount,minimumValue,minimumQuantity))
                return;

            coupons.addCoupon(code,owner,true,minimumValue,minimumQuantity,discount,percentDiscount);
            dataSource.saveCoupon();

            AlertDialog.alertDialog("The system is finished.","Coupon creation is complete.");
            FXRouter.goTo("my_store",dataSource);
        }catch (Exception a) {
            a.printStackTrace();
            AlertDialog.alertDialog("This coupon cannot be created.","Please enter numeric information.");
        }
    }

    public boolean checkCreateCoupon(Double discount,Double percentDiscount,Double minimumValue,Integer minimumQuantity){
        if(percentDiscount != null)
            if(percentDiscount > 100 || percentDiscount <= 0) {
                AlertDialog.alertDialog("This coupon cannot be created.", "Please enter a number between 1 - 100.");
                return false;
            }
        if(discount != null)
            if(discount <= 0) {
                AlertDialog.alertDialog("This coupon cannot be created.", "Please enter a number greater than 0.");
                return false;
            }
        if(minimumQuantity != null)
            if(minimumQuantity <= 0){
                AlertDialog.alertDialog("This coupon cannot be created.", "Please enter a number greater than 0.");
                return false;
            }
        if(minimumValue != null)
            if(minimumValue <= 0){
                AlertDialog.alertDialog("This coupon cannot be created.", "Please enter a number greater than 0.");
                return false;
            }
        return true;
    }

    public void handleBackBtn(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("my_store",dataSource);
    }
}
