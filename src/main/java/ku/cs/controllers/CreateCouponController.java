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
        if(coupons.checkCouponCode(codeTF.getText())) {
            AlertDialog.alertDialog("เตือนอิอิอิ", "ชื่อ code นี้ได้มีการใช้งานแล้ว");
            return;
        }

        try {
            String code = codeTF.getText();
            Store owner = dataSource.getUserList().getCurrUser().getStore();
            Double discount = discountStatusBox.getValue().equals("Discount Cost") ? Double.parseDouble(discountTF.getText()) : null;
            Double percentDiscount = discountStatusBox.getValue().equals("Discount Percent") ? (Double.parseDouble(discountTF.getText()) / 100) : null;
            Double minimumValue = minimumStatusBox.getValue().equals("Minimum Cost") ? Double.parseDouble(minimumTF.getText()) : null;
            Integer minimumQuantity = minimumStatusBox.getValue().equals("Minimum Pieces") ? Integer.parseInt(minimumTF.getText()) : null;

            coupons.addCoupon(code,owner,true,minimumValue,minimumQuantity,discount,percentDiscount);
            dataSource.saveCoupon();

            AlertDialog.alertDialog("จบแล้ว","THX");
            FXRouter.goTo("my_store",dataSource);
        }catch (Exception a) {
            AlertDialog.alertDialog("เกิดข้อผิดพลาด","มีการใส่ข้อมููลไม่ถูกต้อง");
        }
    }

    public void handleBackBtn(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("my_store",dataSource);
    }
}
