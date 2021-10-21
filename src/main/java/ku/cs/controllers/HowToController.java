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
    Label marketplaceLabel;
    @FXML
    Label myAccountLabel;
    @FXML
    Label myStoreILabel;
    @FXML
    Label myStoreIILabel;
    @FXML
    Label addProductLabel;
    @FXML
    Label addProductConfirmLabel;
    @FXML
    Label editProductLabel;
    @FXML
    Label editProductConfirmLabel;
    @FXML
    Label orderPageLabel;
    @FXML
    Label couponPage;
    @FXML
    Label couponPage1;
    @FXML
    Label categoryLabel;
    @FXML
    Label categoryFilterLabel;
    @FXML
    Label sortByLabel;
    @FXML
    Label myAccountResetPasswordLabel;
    @FXML
    Label buyProductLabel;
    @FXML
    Label buyProductConfirmLabel;
    @FXML
    Label nameShopLabel;
    @FXML
    Label reviewProductLabel;
    @FXML
    Label reportPageLabel;
    @FXML
    Label reportPageLabel1;


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
    @FXML
    private ImageView image_marketplace_page,image_category,image_category_filter,image_sort_by;
    @FXML
    private ImageView image_my_account_page,image_my_account_reset,image_buy_product,image_buy_product_confirm,image_name_shop,image_review_product,image_report_page,image_report_product,image_report_review;
    @FXML
    private ImageView image_my_store_page_I,image_my_store_page_II,image_add_product_page,image_add_product_confirm_page,image_edit_product,image_edit_product_confirm,image_order_page,image_coupon_page,image_create_coupon;

    public void initialize() {
        String pathHomePage = getClass().getResource("/ku/cs/image/homePage2.png").toExternalForm();
        image_home_page.setImage(new Image(pathHomePage));
        String pathSignUpPage = getClass().getResource("/ku/cs/image/signUp1.png").toExternalForm();
        image_sign_up_page.setImage(new Image(pathSignUpPage));
        String pathSignUpPage2 = getClass().getResource("/ku/cs/image/signUpSelectProfile1.png").toExternalForm();
        image_sign_up_page_2.setImage(new Image(pathSignUpPage2));
        String pathMarketplace = getClass().getResource("/ku/cs/image/marketplace1.png").toExternalForm();
        image_marketplace_page.setImage(new Image(pathMarketplace));
        String pathMyAccount = getClass().getResource("/ku/cs/image/myAccount1.png").toExternalForm();
        image_my_account_page.setImage(new Image(pathMyAccount));
        String pathMyStore1 = getClass().getResource("/ku/cs/image/myStore1.png").toExternalForm();
        image_my_store_page_I.setImage(new Image(pathMyStore1));
        String pathMyStore2 = getClass().getResource("/ku/cs/image/myStore2.png").toExternalForm();
        image_my_store_page_II.setImage(new Image(pathMyStore2));
        String pathAddProduct = getClass().getResource("/ku/cs/image/addProduct1.png").toExternalForm();
        image_add_product_page.setImage(new Image(pathAddProduct));
        String pathAddProduct1 = getClass().getResource("/ku/cs/image/addProductConfirm1.png").toExternalForm();
        image_add_product_confirm_page.setImage(new Image(pathAddProduct1));
        String pathEditProduct = getClass().getResource("/ku/cs/image/editProduct.png").toExternalForm();
        image_edit_product.setImage(new Image(pathEditProduct));
        String pathEditProductConfirm = getClass().getResource("/ku/cs/image/confirmWindow1.png").toExternalForm();
        image_edit_product_confirm.setImage(new Image(pathEditProductConfirm));
        String pathOrderPage = getClass().getResource("/ku/cs/image/orderPage1.png").toExternalForm();
        image_order_page.setImage(new Image(pathOrderPage));
        String pathCouponPage = getClass().getResource("/ku/cs/image/couponPage1.png").toExternalForm();
        image_coupon_page.setImage(new Image(pathCouponPage));
        String pathCreateCoupon = getClass().getResource("/ku/cs/image/createCoupon1.png").toExternalForm();
        image_create_coupon.setImage(new Image(pathCreateCoupon));
        String pathCategory = getClass().getResource("/ku/cs/image/category1.png").toExternalForm();
        image_category.setImage(new Image(pathCategory));
        String pathCategoryFilter = getClass().getResource("/ku/cs/image/categoryFilter1.png").toExternalForm();
        image_category_filter.setImage(new Image(pathCategoryFilter));
        String pathSortBy = getClass().getResource("/ku/cs/image/sortBy1.png").toExternalForm();
        image_sort_by.setImage(new Image(pathSortBy));
        String pathResetPassword = getClass().getResource("/ku/cs/image/myAccountRePassword1.png").toExternalForm();
        image_my_account_reset.setImage(new Image(pathResetPassword));
        String pathBuyProduct = getClass().getResource("/ku/cs/image/buyProduct1.png").toExternalForm();
        image_buy_product.setImage(new Image(pathBuyProduct));
        String pathBuyProductConfirm = getClass().getResource("/ku/cs/image/orderBuyProduct1.png").toExternalForm();
        image_buy_product_confirm.setImage(new Image(pathBuyProductConfirm));
        String pathBuyNameShop = getClass().getResource("/ku/cs/image/nameShop1.png").toExternalForm();
        image_name_shop.setImage(new Image(pathBuyNameShop));
        String pathReviewPage = getClass().getResource("/ku/cs/image/reviewPage1.png").toExternalForm();
        image_review_product.setImage(new Image(pathReviewPage));
        String pathReportPage = getClass().getResource("/ku/cs/image/reportPage1.png").toExternalForm();
        image_report_page.setImage(new Image(pathReportPage));
        String pathReportProduct = getClass().getResource("/ku/cs/image/reportProduct.png").toExternalForm();
        image_report_product.setImage(new Image(pathReportProduct));
        String pathReportReview = getClass().getResource("/ku/cs/image/reportReview.png").toExternalForm();
        image_report_review.setImage(new Image(pathReportReview));
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
                "\n\t(6) How-To เพื่อไปอ่านวิธีการใช้งานเบื้องต้น" +
                "\n\t(7) Theme Setting ใช้เปลี่ยนสีธีมของหน้าต่างโปรแกรม");
        signUpPageLabel.setText("หน้าลงทะเบียน \n" +
                "หน้าลงทะเบียนผู้ใช้ (registration) สำหรับผู้ใช้ระบบ โดยหน้านี้จะประกอบไปด้วย 2 ปุ่ม " +
                "\n\t(1) Login คือจะย้อนกลับไปยังหน้าแรก " +
                "\n\t(2) NEXT เมื่อทำการใส่ข้อมูลครบแล้ว เพื่อไปยังหน้าต่อไป คือหน้า upload รูปภาพ\n" +
                "ขั้นตอนสำหรับการลงทะเบียน \n" +
                "\t1. กรอกชื่อในช่อง (Name)\n" +
                "\t2. กรอก username ในช่อง (Username)\n" +
                "เป็นตัวอักษร A-Z พิมพ์ใหญ่หรือพิมพ์เล็ก หรือ 0-9 หรือ Underscore (_) \n" +
                "ตั้งแต่ 3 ตัวอักษรขึ้นไป และไม่ซ้ำกับ username อื่น\n" +
                "\t3. กรอกรหัสผ่านในช่อง (Password)\n" +
                "เป็นตัวอักษร A-Z พิมพ์ใหญ่หรือพิมพ์เล็ก หรือ 0-9 หรืออักษรพิเศษดังนี้ @ $ ! % * # ? & : + ~ { } < > _ - \n" +
                "ความยาวตั้งแต่ 6 ตัวอักษรและไม่เกิน 20 ตัวอักษร\n" +
                "\t4. กรอกรหัสผ่านอีกครั้งเพื่อการยืนยันรหัสผ่านในช่อง (Confirm password)\n" +
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
        marketplaceLabel.setText("หน้า Marketplace \n" +
                "\t(1) ปุ่ม Marketplace เมื่อกดจะกลับมาที่หน้า Marketplace\n" +
                "\t(2) ปุ่ม Category ใช้เลือกหมวดหมู่ของสินค้า\n" +
                "\t(3) ปุ่ม My account เพื่อเข้าสู่หน้าข้อมูลผู้ใช้\n" +
                "\t(4) ปุ่ม My store เพื่อเข้าสู่หน้าข้อมูลร้านค้าของผู้ใช้\n" +
                "\t(5) ปุ่ม Theme Setting เพื่อเปลี่ยนสีธีมของหน้าต่างโปรแกรม\n" +
                "\t(6) ปุ่ม Log out เพื่อออกจากระบบ\n" +
                "\t(7) ช่องข้อความสำหรับใส่ช่วงราคาสินค้าที่ต้องการ\n" +
                "\t(8) เมนูเลือกการเรียงลำดับของสินค้า (sort by)\n");
        myAccountLabel.setText("หน้าบัญชีผู้ใช้งานระบบ (My account) \n" +
                "\tผู้ใช้สามารถแก้ไขรูปภาพ และเปลี่ยนรหัสผ่านของตนเองได้โดยกดปุ่ม My account ในหน้า Marketplace \n" +
                "\tหรือ กดที่เมนู My Account ในหน้า My Store จะปรากฏหน้าต่าง (Your personal info)\n" +
                "\tหากผู้ใช้ต้องการเปลี่ยนรูปประจำตัวให้กดที่รูปประจำตัว (1) แล้วเลือกรูปที่จะเปลี่ยน\n" +
                "\tและถ้าต้องการเปลี่ยนรหัสผ่านให้กดที่ปุ่ม (2) จากนั้นจะปรากฏหน้าต่างให้ผู้ใช้เปลี่ยนรหัสผ่าน\n");
        myAccountResetPasswordLabel.setText("หน้าต่างเปลี่ยนรหัสผ่าน\n" +
                "\tเมื่อใส่รหัสผ่านครบทั้ง 3 ช่องแล้วให้กดปุ่ม CONFIRM การเปลี่ยนรหัสผ่านจึงจะสมบูรณ์");
        myStoreILabel.setText("หน้าร้านค้า (My store)" +
                "\n\t(1) ปุ่ม Back กลับไปยังหน้า Marketplace" +
                "\n\t(2) (Enter Name Store) ช่องสำหรับใส่ชื่อร้านค้าที่ต้องการจะตั้ง" +
                "\n\t(3) ปุ่ม Submit เพื่อยืนยันการตั้งชื่อร้าน");
        myStoreIILabel.setText("หน้าร้านค้า (My Store)(ต่อ)" +
                "\n\t(1) ปุ่ม Marketplace กลับไปยังหน้า Marketplace" +
                "\n\t(2) ปุ่ม Add เพิ่มสินค้าที่ต้องการจะขายในร้าน" +
                "\n\t(3) ปุ่ม Edit ใช้กำหนดจำนวนสินค้าที่ถือว่าคงเหลือน้อย" +
                "\n\t(4) ปุ่ม My account เพื่อเข้าไปยังข้อมูลผู้ใช้" +
                "\n\t(5) ปุ่ม My store เพื่อเข้าดูสินค้าที่มีอยู่ในร้านค้า" +
                "\n\t(6) ปุ่ม Product เพื่อเข้ามายังหน้าเพิ่มสินค้า หรือ แก้ไขสินค้า" +
                "\n\t(7) ปุ่ม Orders เพื่อตรวจสอบรายการสินค้าที่ลูกค้าสั่ง" +
                "\n\t(8) ปุ่ม Coupon เพื่อกำหนดโปรโมชัน");
        addProductLabel.setText("เพิ่มสินค้า" +
                "\n\t(1) ปุ่ม Marketplace กลับไปยังหน้า Marketplace" +
                "\n\t(2) เพิ่มรูปภาพสินค้า เมื่อกดไปที่สัญลักษณ์จะปรากฎหน้าต่างไฟล์" +
                "\n\t(3) ใส่ข้อมูลของสินค้าต่างๆให้ครบถ้วน" +
                "\n\t(4) สำหรับตั้งหมวดหมู่ของสินค้า เมื่อใส่ข้อมูลหมวดหมู่เสร็จให้กดปุ่ม Add" +
                "\n\t(5) ปุ่ม Next เมื่อกรอกข้อมูลสินค้าเสร็จสิ้นแล้วให้กดปุ่ม Next เพื่อไปสู่หน้ายืนยันสินค้า");
        addProductConfirmLabel.setText("ยืนยันสินค้า" +
                "\n\t(1) ปุ่ม Cancel เมื่อไม่ต้องการยืนยันสินค้า" +
                "\n\t(2) ปุ่ม Confirm เมื่อต้องการยืนยันสินค้า");
        editProductLabel.setText("แก้ไขข้อมูลสินค้า \n" +
                "\tเมื่อต้องการแก้ไขข้อมูลสินค้า\n" +
                "\tกดที่ (1) สินค้าที่ต้องการแก้ไขข้อมูล\n" +
                "\t(2) ใส่ข้อมูลสินค้าที่ต้องการแก้ไขใหม่\n" +
                "\tเมื่อใส่ข้อมูลสินค้าใหม่เสร็จสิ้นแล้วกดที่ปุ่ม (3)Edit จะมีหน้าต่างยืนยันการแก้ไขปรากฎขึ้นมา");
        editProductConfirmLabel.setText("หน้าต่างยืนยันการแก้ไขข้อมูลสินค้า" +
                "\n\t(1) ปุ่ม OK เมื่อต้องการยืนยันการแก้ไขข้อมูล" +
                "\n\t(2) ปุม Cancel เมื่อไม่ต้องการยืนยันการแก้ไขข้อมูล");
        orderPageLabel.setText("หน้ารายการสั่งซื้อ (Orders)\n" +
                "ผลรายการสั่งซื้อมี 3 รูปแบบ\n" +
                "1. รายการสั่งซื้อทั้งหมด โดยกดที่ปุ่ม (1)  All \n" +
                "2. รายการสั่งซื้อที่ต้องจัดส่ง โดยกดที่ปุ่ม (2)  To Ship \n" +
                "3. รายการสั่งซื้อที่จัดส่งแล้ว โดยกดที่ปุ่ม (3)  Shiped \n" +
                "\tรายการสั่งซื้อที่จัดส่งแล้วจะแสดงหมายเลขติดตามพัสดุดังช่องที่ (4) " +
                "\n\tรายการสั่งซื้อที่ต้องจัดส่งจะมีช่องข้อความ (5) ให้ใส่เลขติดตามพัสดุ  " +
                "\n\tเมื่อใส่เลขติดตามพัสดุแล้วให้กดปุ่ม (6) การจัดส่งจึงจะสมบูรณ์");
        couponPage.setText("หน้าโปรโมชัน (Coupon)\n" +
                "หากต้องการจัดโปรโมชัน \n" +
                "\tกดที่ปุ่ม (1) ADD จากนั้นจะปรากฏหน้าต่างให้เลือกโปรโมชัน\n" +
                "\tหากต้องการลบคูปองให้กดปุ่ม (3) หรือหากต้องการเปิด-ปิดการใช้งานให้กดปุ่ม (2)\n");
        couponPage1.setText("หน้าต่างเลือกโปรโมชัน (Coupon)\n" +
                "\tกล่องตัวเลือกที่ (1) คือเลือกยอดซื้อขั้นต่ำมีให้เลือก 2 ประเภทคือ ราคาสินค้าขั้นต่ำ และ จำนวนสินค้าขั้นต่ำ\n" +
                "จากนั้นให้ใส่จำนวนในช่องข้อความที่ (2) \n" +
                "\tกล่องตัวเลือกที่ (3) คือส่วนลดมีให้เลือก 2 ประเภทคือ \n" +
                "ลดเป็นจำนวนเงิน และ ลดเป็นเปอร์เซ็นต์ จากนั้นให้ใส่จำนวนในช่องข้อความที่ (4) และใส่โค้ดส่วนลดในช่องข้อความที่ (5) \n" +
                "\tจากนั้นกดปุ่ม (7) Create การจัดโปรโมชันจึงจะสมบูรณ์ และคูปองจะปรากฏ\n" +
                "\tปุ่ม (6) Back เมื่อไม่ต้องการสร้างคูปอง");
        categoryLabel.setText("ปุ่ม (2) Category สามารถเลือกให้ระบบแสดงเฉพาะสินค้าในหมวดหมู่ที่ผู้ใช้สนใจ \n" +
                "เมื่อเลือกหมวดหมู่สินค้าที่สนใจแล้ว ระบบจะแสดงสินค้าตามหมวดหมู่ที่เลือก");
        categoryFilterLabel.setText("หมวดหมู่สินค้าที่เลือก");
        sortByLabel.setText("นอกจากนี้แล้วผู้ใช้ยังสามารถเลือกแสดงสินค้าตามช่วงราคาสินค้า และสามารถเรียงลำดับสินค้าจากราคาสินค้า" +
                "\nโดยใส่ราคาสินค้าใน (7) ช่องข้อความสำหรับใส่ช่วงราคาสินค้าที่ต้องการจากนั้นให้กดปุ่ม ENTER " +
                "\nหรือเรียงลำดับจากสินค้าที่ผู้ขายเพิ่มล่าสุดได้โดยเลือกที่ (8) เมนูเลือกการเรียงลำดับของสินค้า (sort by) ");
        buyProductLabel.setText("สั่งซื้อสินค้า \n" +
                "\tหน้า Marketplace เป็นหน้าสำหรับซื้อสินค้าโดยสามารถกดเลือกสินค้าที่ต้องการซื้อที่พื้นที่แสดงสินค้า\n" +
                "\tเมื่อกดเลือกสินค้าแล้ว จะเข้าสู่หน้ารายละเอียดของสินค้า\n" +
                "หน้ารายละเอียดสินค้า\n" +
                "\t(1) ชื่อร้านค้า\n" +
                "\t(2) คำอธิบายสินค้า\n" +
                "\t(3) จำนวนสินค้า\n" +
                "\t(4) ปุ่มกดซื้อ\n" +
                "\t(5) ช่องระบุจำนวนสินค้า\n" +
                "\tผู้ใช้สามารถระบุจำนวนสินค้าที่ต้องการซื้อได้ที่(5) ช่องระบุจำนวนสินค้า และกดซื้อสินค้าที่ปุ่ม(4) BUY ");
        buyProductConfirmLabel.setText("จากนั้นจะปรากฎหน้าต่างเพื่อให้ผู้ใช้ตรวจสอบความถูกต้องของรายการสั่งซื้อ\n" +
                "และถ้ามีคูปองส่วนลดสามารถใส่คูปองได้ใน(1)(enter coupon) หน้ายืนยันรายการสั่งซื้อ จากนั้นให้กดปุ่ม(2) Order เพื่อยืนยันรายการสั่งซื้อ ");
        nameShopLabel.setText("ผู้ใช้สามารถเลือกร้านค้าเพื่อซื้อสินค้าในร้านนั้นได้ โดยกดชื่อร้านค้าในหน้ารายละเอียดสินค้า และสามารถเลือกคูปองส่วนลดได้จากร้านนั้น");
        reviewProductLabel.setText("การให้คะแนนสินค้าและการรายงานความไม่เหมาะสม\n" +
                "\tผู้ใช้สามารถให้คะแนนความพึงพอใจต่อสินค้าได้ โดยกดเลือกสินค้าชิ้นที่ต้องการให้คะแนนด้านล่างของหน้ารายละเอียดสินค้า \n" +
                "\tจะเป็นส่วนของการให้คะแนนความพึงพอใจ");
        reportPageLabel.setText("ผู้ใช้สามารถรายงานความไม่เหมาะสมของสินค้า และรายงานความไม่เหมาะสมของความคิดเห็นที่ผู้ใช้\n" +
                "คนอื่นแสดงความคิดเห็นได้ โดยกดปุ่ม report\n" +
                "\tปุ่ม(1) report สินค้า\n" +
                "\tปุ่ม(2) report ความคิดเห็น");
        reportPageLabel1.setText("เมื่อกดปุ่ม report จะปรากฏหน้าต่างรายละเอียดของการรายงานความไม่เหมาะสม \n" +
                "ผู้ใช้สามารถเลือกประเภทความไม่เหมาะสมของสินค้า หรือความไม่เหมาะสมของความความคิดเห็นและกด REPORT ");
    }

}
