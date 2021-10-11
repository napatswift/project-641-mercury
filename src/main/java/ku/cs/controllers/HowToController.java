package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import java.io.IOException;
public class HowToController {

    @FXML
    TabPane howToTP;

    @FXML
    Label firstPageLabel;
    @FXML
    Label signUpPageLabel;
    @FXML
    Label signUpUploadLabel;

    @FXML
    public void handleRegisterBtn(){
        howToTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleHowToUseBtn(){
        howToTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleMarketPlaceBtn(){
        howToTP.getSelectionModel().select(2);
    }

    @FXML
    public void handleSellerBtn(){
        howToTP.getSelectionModel().select(3);
    }

    @FXML
    public void handleBack() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    private ImageView image_home_page,image_sign_up_page,image_sign_up_page_2;

    public void initialize() {
        String pathHomePage = getClass().getResource("/ku/cs/image/home-page.png").toExternalForm();
        image_home_page.setImage(new Image(pathHomePage));
        String pathSignUpPage = getClass().getResource("/ku/cs/image/sign-up-page.png").toExternalForm();
        image_sign_up_page.setImage(new Image(pathSignUpPage));
        String pathSignUpPage2 = getClass().getResource("/ku/cs/image/sign-up-page2.png").toExternalForm();
        image_sign_up_page_2.setImage(new Image(pathSignUpPage2));
        readData();
    }

    public void readData() {
        firstPageLabel.setText("หน้าแรก \n" +
                "เมื่อกดเข้าใช้งานโปรแกรม จะปรากฏหน้าแรกเป็นหน้าที่มีส่วนของการลงชื่อเข้าใช้ระบบ โดยให้ใส่ " +
                "\n\t(1) username  " +
                "\n\t(2) รหัสผ่าน (password) \n" +
                "จากนั้นให้กดปุ่ม" +
                "\n\t(3) LOGIN เพื่อเข้าสู่ระบบ หากยังไม่มีบัญชีสำหรับการใช้เข้าใช้งานระบบให้กดปุ่ม " +
                "\n\t(4) SIGN-UP เพื่อทำการลงทะเบียนบัญชีผู้ใช้ \n" +
                "นอกจากนี้ยังมีปุ่ม " +
                "\n\t(5) About Us เพื่อไปยังหน้าที่แสดงรายชื่อผู้จัดทำ " +
                "\n\t(6) How-To เพื่อไปอ่านวิธีการใช้งานเบื้องต้น");
        signUpPageLabel.setText("หน้าลงทะเบียน \n" +
                "หน้าลงทะเบียนผู้ใช้ (registration) สำหรับผู้ใช้ระบบ โดยหน้านี้จะประกอบไปด้วย 2 ปุ่ม " +
                "\n\t(1) Login คือจะย้อนกลับไปยังหน้าแรก " +
                "\n\t(2) NEXT เมื่อทำการใส่ข้อมูลครบแล้ว เพื่อไปยังหน้าต่อไป คือหน้า upload รูปภาพ\n" +
                "ขั้นตอนสำหรับการลงทะเบียน \n" +
                "\t1.กรอกชื่อในช่อง (Name)\n" +
                "\t2.กรอก username ในช่อง (Username)\n" +
                "เป็นตัวอักษร A-Z พิมพ์ใหญ่หรือพิมพ์เล็ก หรือ 0-9 หรือ Underscore (_) \n" +
                "ตั้งแต่ 3 ตัวอักษรขึ้นไป และไม่ซ้ำกับ username อื่น\n" +
                "\t3.กรอกรหัสผ่านในช่อง (Password)\n" +
                "เป็นตัวอักษร A-Z พิมพ์ใหญ่หรือพิมพ์เล็ก หรือ 0-9 หรืออักษรพิเศษดังนี้ @ $ ! % * # ? & : + ~ { } < > _ - \n" +
                "ความยาวตั้งแต่ 6 ตัวอักษรและไม่เกิน 20 ตัวอักษร\n" +
                "\t4.กรอกรหัสผ่านอีกครั้งเพื่อการยืนยันรหัสผ่านในช่อง (Confirm password)\n" +
                "รหัสผ่านต้องเหมือนกับรหัสผ่านที่กรอกในช่องก่อนหน้า\n" +
                "เมื่อใส่ข้อมูลครบถ้วนให้กด NEXT เพื่อไปยังหน้าถัดไป\n");
        signUpUploadLabel.setText("หน้าลงทะเบียน upload รูปภาพ \n" +
                "ผู้ใช้ระบบ upload รูปภาพ เพื่อใช้เป็นภาพของผู้ใช้ระบบ โดยประกอบด้วยปุ่ม " +
                "\n\t(1) SELECT PROFILE PICTURE เมื่อกดแล้วจะปรากฏหน้าต่างเลือกไฟล์ \n" +
                "ต่อมาคือปุ่ม " +
                "\n\t(2) CANCEL เพื่อยกเลิกการลงทะเบียน โดยจะไม่มีการบันทึกข้อมูลใด ๆ \n" +
                "และสุดท้ายคือปุ่ม " +
                "\n\t(3) CONFIRM เพื่อการยืนยันการลงทะเบียนใช้เข้าใช้งานในระบบ \n" +
                "เมื่อลงทะเบียนบัญชีผู้ใช้สำเร็จ ก็จะสามารถเข้าสู่ระบบได้");
    }

}
