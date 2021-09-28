package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.*;
import ku.cs.service.DataSource;

import java.io.IOException;

public class ReportingViewController {

    @FXML
    private HBox reportItemHBox;

    @FXML
    private Label reportPromptLabel;

    @FXML
    private VBox radioBtnVBox;

    @FXML
    private TextArea reportDetailsTA;

    @FXML
    private VBox assistiveTextVBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button submitReportBtn;

    private ToggleGroup group;
    private String [] reportTypes;
    private Product product;
    private Review review;
    private DataSource dataSource;
    private EventHandler<ActionEvent> goBack;

    void setCancelBtnHandler(EventHandler<ActionEvent> event) {
        goBack = event;
        cancelBtn.setOnAction(goBack);
    }

    @FXML
    void handleSubmitReportBtn(ActionEvent event) {
        assistiveTextVBox.getChildren().clear();
        if (group.getSelectedToggle() == null || group.getSelectedToggle().getUserData() == null) {
            assistiveTextVBox
                    .getChildren().add(new Label("Please select what is wrong with this."));
            return;
        }

        if (reportDetailsTA.getText() == null || reportDetailsTA.getText().equals("")) {
            assistiveTextVBox
                    .getChildren().add(new Label("Please tell us more details about this report."));
            return;
        }

        String type = (String) group.getSelectedToggle().getUserData();
        String details = reportDetailsTA.getText();

        if (product != null) {
            ProductReport report = new ProductReport(type, product, details);
            dataSource.getReports().addReport(report);
        } else if (review != null) {
            ReviewReport report = new ReviewReport(type, review, details);
            dataSource.getReports().addReport(report);
        } else {
            System.err.println("product and review are null");
            return;
        }

        dataSource.getProducts().setSelectedProduct(null);
        dataSource.getReviews().setCurrReview(null);

        dataSource.saveReport();
        goBack.handle(event);
    }

    private void populateReportType(){
        group = new ToggleGroup();
        for (String type: reportTypes){
            RadioButton newBtn = new RadioButton(type);
            newBtn.getStyleClass().add("subtitle1");
            newBtn.setUserData(type);
            newBtn.setToggleGroup(group);
            radioBtnVBox.getChildren().add(newBtn);
        }
        group.selectedToggleProperty()
                .addListener((observableValue, toggle, t1) -> assistiveTextVBox.getChildren().clear());
    }

    private void buildCard(){
        reportItemHBox.getStyleClass().add("review-card");
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setReportItem(Product product){
        ComponentBuilder builder = new ComponentBuilder();
        reportItemHBox.getChildren().add(builder.smallProductCard(product));
        reportTypes = new String [] {
                "Copyright",
                "Offensive or sexually explicit",
                "Privacy concern",
                "Legal issue",
                "Others" };

        populateReportType();
        this.product = product;
    }

    public void setReportItem(Review review){
        reportPromptLabel.setText("What's wrong with this review?");
        ComponentBuilder builder = new ComponentBuilder();
        reportItemHBox.getChildren().add(builder.smallReviewCard(review));
        reportTypes = new String [] {
                "Spam",
                "Unuseful",
                "Offensive or sexually explicit",
                "Privacy concern",
                "Legal issue",
                "Others" };

        populateReportType();
        this.review = review;
    }

    private void clearText(KeyEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    private void clearText(MouseEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    @FXML
    public void initialize() throws IOException {
      buildCard();

      reportDetailsTA.setOnMouseClicked(this::clearText);
      reportDetailsTA.setOnKeyPressed(this::clearText);
    }

}
