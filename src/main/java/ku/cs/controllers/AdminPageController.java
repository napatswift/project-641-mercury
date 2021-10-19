package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.models.Admin;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.models.CategoryList;
import ku.cs.models.components.dialogs.TextDialog;
import ku.cs.models.components.listCell.CategoryListCell;
import ku.cs.models.components.listCell.ReportListCell;
import ku.cs.models.components.listCell.SubCategoryListCell;
import ku.cs.models.components.listCell.UserListCell;
import ku.cs.models.Report;
import ku.cs.models.ReportList;
import ku.cs.service.DataSource;
import ku.cs.strategy.FromMostRecentReportComparator;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AdminPageController {

    private DataSource dataSource;
    private UserList userList;
    private ReportList reportList;
    private Report selectReport;
    private User selectUser;
    private Admin adminUser;
    private String selectCategory;
    private CategoryList categories;

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
    @FXML private ToggleButton userButton, categoryButton, reportButton, resetPasswordButton, logOutButton;
    @FXML private Button banAndUnbanBtn;
    @FXML private TextField addCategoryTF
            , addSubCategoryTF;
    private Tab resetPasswordTab;


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
        categories = dataSource.getCategories();

        showAdmin(adminUser);
        showUserListView();
        clearSelectedUser();
        handleSelectedUserListView();

        showReportListView();
        clearSelectedReport();
        handleSelectedReportListView();

        showCategoryListView();
        handleSelectedCategoryListView();
        resetPasswordTab = new Tab("reset_password");
        resetToggleGroupToButton();
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

            if (user.getHasStore())
                storeName.setText(user.getStoreName());
            else
                storeName.setText("This User Don't Have Store");

            if(user.isBanned()){
                banAndUnbanBtn.setText("Unban");
                statusUserBan.setText("InActivate");
                statusUserBan.setTextFill(Color.RED);
                banAndUnbanBtn.setTextFill(Color.BLUE);
            }
            else{
                banAndUnbanBtn.setText("Ban");
                statusUserBan.setText("Activate");
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
        ArrayList<Report> reports = reportList.toList();
        reports.sort(new FromMostRecentReportComparator());
        reportListView.getItems().addAll(reports);
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
            selectUser = report.getSuspectedPerson();
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
        categoryListView.getItems().addAll(categories.categorySet());
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
            subCategoryListView.getItems().addAll(categories.getSubcategoryOf(selectCategory));
        subCategoryListView.setCellFactory(subCategoryListView -> new SubCategoryListCell());
        subCategoryListView.refresh();
    }

    // Button
    private void resetToggleGroupToButton(){
        ToggleGroup group = new ToggleGroup();

        userButton.setToggleGroup(group);
        categoryButton.setToggleGroup(group);
        reportButton.setToggleGroup(group);
        resetPasswordButton.setToggleGroup(group);

        group.selectedToggleProperty().addListener((observableValue, ot, nt) -> {
            if (ot != null) {
                ((ToggleButton) ot).getStyleClass().remove("list-item-active-btn");
            }
            if (nt != null) {
                ((ToggleButton) nt).getStyleClass().add("list-item-active-btn");
            }
        });

        userButton.fire();
    }

    @FXML
    private void handleLogOutButton(){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCategoryButton() {
        adminTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleUserButton() {
        adminTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleReportButton() {
        adminTP.getSelectionModel().select(2);
    }

    @FXML
    public void handleResetPasswordButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/reset_password.fxml"));
        try {
            Node node = loader.load();
            resetPasswordTab.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!adminTP.getTabs().contains(resetPasswordTab))
            adminTP.getTabs().add(resetPasswordTab);
        adminTP.getSelectionModel().select(resetPasswordTab);
        ResetPasswordController controller = loader.getController();
        controller.setHandleBackButton(this::handleResetPasswordBack);
    }

    public void handleResetPasswordBack(ActionEvent event){
        userButton.fire();
        adminTP.getTabs().remove(resetPasswordTab);
    }

    public void handleAddCategoryButton() {
        String categoryString = addCategoryTF.getText();
        if(addCategoryTF.getText() != null
                && !categoryString.isEmpty() && !categoryString.isBlank()) {
            categoryString = categoryString.toLowerCase(Locale.ROOT);
            if (categories.addCategory(categoryString)) {
                dataSource.saveCategory();
                categoryListView.getItems().clear();
                showCategoryListView();
            } else {
                TextDialog dialog = new TextDialog("Sorry...", selectCategory + " is already contained.");
                dialog.showAndWait();
            }
        }
        addCategoryTF.clear();

    }

    public void handleAddSubCategoryButton() {
        String subcategoryString = addSubCategoryTF.getText();
        if(addSubCategoryTF.getText() != null
                && !subcategoryString.isBlank() && !subcategoryString.isEmpty()){
            String subCat = addSubCategoryTF.getText().toLowerCase(Locale.ROOT);
            if (categories.addSubCategory(selectCategory, subCat)) {
                dataSource.saveCategory();
                showSelectedCategory(selectCategory);
            } else {
                TextDialog dialog = new TextDialog("Sorry...", selectCategory + " already contains " + subcategoryString + ".");
                dialog.showAndWait();
            }
        }
        addSubCategoryTF.clear();
    }

    public void handleDismissBtn() {
        removeReportFormReportListView(selectReport);
        reportLeftVBox.setVisible(false);
    }

    public void handleBanBtn() {
        if(selectReport != null && adminUser.bans(selectUser)) {
            removeReportFormReportListView(selectReport);
            dataSource.saveAccount();
            reportLeftVBox.setVisible(false);
        }
    }

    public void handleBanAndUnbanBtn() {
        if(!selectUser.isBanned()){
            adminUser.bans(selectUser);
        }
        else{
            adminUser.unbans(selectUser);
        }
        dataSource.saveAccount();
        showSelectedUser(selectUser);
    }
}
