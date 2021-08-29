package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.models.AccountList;
import ku.cs.models.Report;
import ku.cs.models.ReportList;
import ku.cs.models.User;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdminPageController {

    private DataSource dataSource;
    private AccountList accountList;
    private ReportList reportList;

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
            ,detailText;
    @FXML private ImageView imageView
            ,userImage
            ,suspectedPersonImage;
    @FXML private ListView<User> userListView;
    @FXML private ListView<Report> reportListView;
    @FXML private TabPane adminTP;
    @FXML private VBox userLeftVBox;
    @FXML private Button userButton, categoryButton, reportButton, resetPasswordButton, logOutButton;

    @FXML
    public void initialize() throws IOException {
        userLeftVBox.setVisible(false);

        dataSource = (DataSource) FXRouter.getData();
        accountList = dataSource.getAccounts();
        User user = dataSource.getAccounts().getCurrAccount();
        dataSource.parseReport(",");
        reportList = dataSource.getReports();

        showAdmin(user);
        showUserListView();
        clearSelectedUser();
        handleSelectedUserListView();

        showReportListView();
        clearSelectedReport();
        handleSelectedReportListView();
    }

    public void showAdmin(User user){
        nameAdmin.setText(user.getName());
        role.setText(""+ user.getRole());
        imageView.setImage(new Image(user.getPicturePath()));
    }

    private void resetBtn(Button btn){
        Button [] buttons = {userButton, categoryButton, reportButton};
        int idx = adminTP.getSelectionModel().getSelectedIndex();
        buttons[idx].getStyleClass().removeAll("list-item-active-btn");
        btn.getStyleClass().add("list-item-active-btn");
    }

    private void showUserListView() throws IOException {
        ObservableList<User> data = FXCollections.observableArrayList();

        for(User user : accountList.toListReverse()) {
            if(user.getRole() == User.Role.USER){
                data.add(user);
            }
        }
        userListView.getItems().addAll(data);
        userListView.setCellFactory(userListView -> new User.UserListCell());
        userListView.refresh();
    }

    private void handleSelectedUserListView() {
        userListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable,
                                        User oldValue, User newValue) {
                        showSelectedUser(newValue);
                    }
                });
    }

    private void showSelectedUser(User user) {
        userLeftVBox.setVisible(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        userImage.setImage(new Image(user.getPicturePath()));
        userName.setText(user.getUsername());
        realNameUser.setText(user.getName());
        lastLogin.setText("last login "+user.getLoginDateTime().format(formatter));
        if(user.getHasStore()){
            storeName.setText(user.getStoreName());
        }
        else
            storeName.setText("This User Don't Have Store");
    }

    private void clearSelectedUser() {
        userName.setText("");
        realNameUser.setText("");
        lastLogin.setText("");
        storeName.setText("");
    }

    private void showReportListView() throws IOException {
        reportListView.getItems().addAll(reportList.toList());
        reportListView.refresh();
    }

    private void handleSelectedReportListView() {
        reportListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSelectedReport(newValue));
    }

    private void showSelectedReport(Report report) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        suspectedPersonImage.setImage(new Image(report.getSuspectedPerson().getPicturePath()));
        suspectedPersonRealName.setText(report.getSuspectedPerson().getUsername());
        suspectedPersonUserName.setText(report.getSuspectedPerson().getName());
        reportTime.setText("report time "+report.getReportDateTime().format(formatter));
        detailText.setText(report.getDetail());
        if(report.getSuspectedPerson().getHasStore()){
            suspectedPersonStoreName.setText(report.getSuspectedPerson().getStoreName());
        }
        else
            suspectedPersonStoreName.setText("This User Don't Have Store");
    }

    private void clearSelectedReport() {
        suspectedPersonUserName.setText("");
        suspectedPersonRealName.setText("");
        reportTime.setText("");
        suspectedPersonStoreName.setText("");
        detailText.setText("");
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

    public void handleAddCategoryButton(ActionEvent actionEvent) {
    }

    public void handleAddSubCategoryButton(ActionEvent actionEvent) {
    }

    public void handleDismissBtn(ActionEvent actionEvent) {
    }

    public void handleBanBtn(ActionEvent actionEvent) {
    }
}
