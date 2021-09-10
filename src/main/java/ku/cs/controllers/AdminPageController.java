package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.models.*;
import ku.cs.models.components.CategoryListCell;
import ku.cs.models.components.ReportListCell;
import ku.cs.models.components.SubCategoryListCell;
import ku.cs.models.components.UserListCell;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class AdminPageController {

    private DataSource dataSource;
    private UserList userList;
    private ReportList reportList;
    private Report selectReport;
    private User selectUser;
    private Admin adminUser;
    private String selectCategory;
    private Map<String, ArrayList<String>> categories;

    @FXML private Label nameAdmin
            ,role
            ,userName
            ,realNameUser
            ,lastLogin
            ,storeName
            ,suspectedPersonUserName
            ,reportTime
            ,suspectedPersonRealName
            ,suspectedPersonStoreName
            ,detailText
            ,statusUserBan
            ,reportType;
    @FXML private ImageView imageView
            ,userImage
            ,suspectedPersonImage;
    @FXML private ListView<User> userListView;
    @FXML private ListView<Report> reportListView;
    @FXML private ListView<String> categoryListView;
    @FXML private ListView<String> subCategoryListView;
    @FXML private TabPane adminTP;
    @FXML private VBox userLeftVBox, reportLeftVBox;
    @FXML private Button userButton, categoryButton, reportButton, resetPasswordButton, logOutButton, banAndUnbanBtn;
    @FXML private TextField addCategoryTF
            , addSubCategoryTF;


    @FXML
    public void initialize() throws IOException {
        userLeftVBox.setVisible(false);
        reportLeftVBox.setVisible(false);

        dataSource = (DataSource) FXRouter.getData();
        userList = dataSource.getUserList();

        adminUser = (Admin) userList.getCurrUser();

        dataSource.parseReport();
        reportList = dataSource.getReports();

        dataSource.parseCategory();
        categories = dataSource.getMapCategories();

        showAdmin(adminUser);
        showUserListView();
        clearSelectedUser();
        handleSelectedUserListView();

        showReportListView();
        clearSelectedReport();
        handleSelectedReportListView();

        showCategoryListView();
        handleSelectedCategoryListView();

    }

    public void showAdmin(User user) {
        nameAdmin.setText(user.getName());
        role.setText(""+ user.getRole());
        imageView.setImage(new Image(user.getPicturePath()));
        imageView.setClip(new Circle(37, 37, 37));
    }

    // User Page
    private void showUserListView() {
        userListView.getItems().addAll(userList.toListOnlyRoleUser());
        userListView.setCellFactory(userListView -> new UserListCell());
        userListView.refresh();
    }

    private void handleSelectedUserListView() {
        userListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSelectedUser(newValue));
    }

    private void showSelectedUser(User user) {
        if(user != null) {
            selectUser = user;
            userLeftVBox.setVisible(true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            userImage.setImage(new Image(user.getPicturePath()));
            userImage.setClip(new Circle(37, 37, 37));
            userName.setText(user.getUsername());
            realNameUser.setText(user.getName());
            lastLogin.setText("last login " + user.getLoginDateTime().format(formatter));
            if (user.getHasStore()) {
                storeName.setText(user.getStoreName());
            } else
                storeName.setText("This User Don't Have Store");
            if(user.isBanned()){
                banAndUnbanBtn.setText("Unban");
                statusUserBan.setText("Banned");
                statusUserBan.setTextFill(Color.RED);
                banAndUnbanBtn.setTextFill(Color.BLUE);
            }
            else{
                banAndUnbanBtn.setText("Ban");
                statusUserBan.setText("Not Banned");
                statusUserBan.setTextFill(Color.BLUE);
                banAndUnbanBtn.setTextFill(Color.RED);
            }
        }
    }

    private void clearSelectedUser() {
        userName.setText("");
        realNameUser.setText("");
        lastLogin.setText("");
        storeName.setText("");
    }

    // Report Page
    private void showReportListView() {
        reportListView.getItems().addAll(reportList.toList());
        reportListView.setCellFactory(reportListView -> new ReportListCell());
        reportListView.refresh();
    }

    private void removeReportFormReportListView(Report report) {
        if(selectReport != null) {
            reportList.removeReport(report);
            dataSource.saveReport();
            dataSource.parseReport();
            reportList = dataSource.getReports();

            reportListView.getItems().clear();
            showReportListView();
            clearSelectedReport();
            handleSelectedReportListView();
        }
    }

    private void handleSelectedReportListView() {
        reportListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSelectedReport(newValue));
    }

    private void showSelectedReport(Report report) {
        reportLeftVBox.setVisible(true);
        if(report != null) {
            selectReport = report;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            suspectedPersonImage.setImage(new Image(report.getSuspectedPerson().getPicturePath()));
            suspectedPersonImage.setClip(new Circle(37, 37, 37));
            suspectedPersonRealName.setText(report.getSuspectedPerson().getUsername());
            suspectedPersonUserName.setText(report.getSuspectedPerson().getName());
            reportTime.setText("report time " + report.getReportDateTime().format(formatter));
            detailText.setText(report.getDetail());
            reportType.setText(report.getType());

            if (report.getSuspectedPerson().getHasStore()) {
                suspectedPersonStoreName.setText(report.getSuspectedPerson().getStoreName());
            } else
                suspectedPersonStoreName.setText("This User Don't Have Store");
        }
    }

    private void clearSelectedReport() {
        suspectedPersonUserName.setText("");
        suspectedPersonRealName.setText("");
        suspectedPersonImage.setImage(null);
        reportTime.setText("");
        suspectedPersonStoreName.setText("");
        detailText.setText("");
    }

    // Category Page
    private void showCategoryListView() {
        categoryListView.getItems().addAll(categories.keySet());
        categoryListView.setCellFactory(categoryListView -> new CategoryListCell());
        categoryListView.refresh();
    }

    private void handleSelectedCategoryListView() {
        categoryListView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showSelectedCategory(newValue));
    }

    private void showSelectedCategory(String category) {
        subCategoryListView.getItems().clear();
        selectCategory = category;
        if(categories.containsKey(category))
            subCategoryListView.getItems().addAll(categories.get(selectCategory));
        subCategoryListView.setCellFactory(subCategoryListView -> new SubCategoryListCell());
        subCategoryListView.refresh();
    }

    // Button
    private void resetBtn(Button btn){
        Button [] buttons = {userButton, categoryButton, reportButton};
        int idx = adminTP.getSelectionModel().getSelectedIndex();
        buttons[idx].getStyleClass().removeAll("list-item-active-btn");
        btn.getStyleClass().add("list-item-active-btn");
    }

    @FXML
    private void handleLogOutButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCategoryButton(ActionEvent actionEvent) {
        resetBtn((Button) actionEvent.getSource());
        adminTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        resetBtn((Button) actionEvent.getSource());
        adminTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        resetBtn((Button) actionEvent.getSource());
        adminTP.getSelectionModel().select(2);
    }

    @FXML
    public void handleResetPasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("reset_password", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า reset_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleAddCategoryButton(ActionEvent actionEvent) throws IOException {
        if(addCategoryTF.getText() != null
                && !categories.containsKey(addCategoryTF.getText())
                && !addCategoryTF.getText().equals("")) {
            categories.put(addCategoryTF.getText().toLowerCase(Locale.ROOT), new ArrayList<>());
            dataSource.saveCategory();
            categoryListView.getItems().clear();
            showCategoryListView();
        }
        addCategoryTF.clear();

    }

    public void handleAddSubCategoryButton(ActionEvent actionEvent) {
        ArrayList<String> newList = categories.get(selectCategory);
        if(addSubCategoryTF.getText() != null
                && !newList.contains(addSubCategoryTF.getText())
                && !addSubCategoryTF.getText().equals("")){
            newList.add(addSubCategoryTF.getText().toLowerCase(Locale.ROOT));
            categories.put(selectCategory,newList);
            dataSource.saveCategory();
            showSelectedCategory(selectCategory);
        }
        addSubCategoryTF.clear();
    }

    public void handleDismissBtn(ActionEvent actionEvent) throws IOException {
        removeReportFormReportListView(selectReport);
    }

    public void handleBanBtn(ActionEvent actionEvent) throws IOException {
        if(selectReport != null && adminUser.setIsBanned(selectUser)) {
            removeReportFormReportListView(selectReport);
            dataSource.saveAccount();
        }
    }

    public void handleBanAndUnbanBtn(ActionEvent actionEvent) {
        if(!selectUser.isBanned()){
            adminUser.setIsBanned(selectUser);
        }
        else{
            adminUser.setIsUnbanned(selectUser);
        }
        dataSource.saveAccount();
        showSelectedUser(selectUser);
    }
}
