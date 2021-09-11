package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.*;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class ReportingViewController {

    @FXML
    private HBox reportItemHBox;

    @FXML
    private Label reportPromptLabel;

    @FXML
    private VBox radioBtnVBox, assistiveTextVBox;

    @FXML
    private TextArea reportDetailsTA;

    private DataSource dataSource;
    private ToggleGroup group;

    @FXML
    void handleBackBtn(ActionEvent event) {
        dataSource.getProducts().setSelectedProduct(null);
        dataSource.getReviews().setCurrReview(null);
        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Product product = dataSource.getProducts().getSelectedProduct();
        Review review = dataSource.getReviews().getCurrReview();
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

        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateReportType(){
        String [] reportTypes;
        group = new ToggleGroup();
        if (dataSource.getProducts().getSelectedProduct() != null)
            reportTypes = new String [] {
                    "Copyright",
                    "Offensive or sexually explicit",
                    "Privacy concern",
                    "Legal issue",
                    "Others" };
        else
            reportTypes = new String [] {
                "Spam",
                "Unuseful",
                "Offensive or sexually explicit",
                "Privacy concern",
                "Legal issue",
                "Others" };

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
        Product product = dataSource.getProducts().getSelectedProduct();
        Review review = dataSource.getReviews().getCurrReview();
        ComponentBuilder builder = new ComponentBuilder();
        reportItemHBox.getStyleClass().add("review-card");
        if (product != null)
            reportItemHBox.getChildren().add(builder.smallProductCard(product));
        else
            reportItemHBox.getChildren().add(builder.smallReviewCard(review));
    }

    private void clearText(KeyEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    private void clearText(MouseEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    @FXML
    public void initialize() throws IOException {
      dataSource = (DataSource) FXRouter.getData();
      if (dataSource.getReviews().getCurrReview() != null)
          reportPromptLabel.setText("What's wrong with this review?");
      populateReportType();
      buildCard();

      reportDetailsTA.setOnMouseClicked(this::clearText);
      reportDetailsTA.setOnKeyPressed(this::clearText);
    }

}
