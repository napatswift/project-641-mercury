package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.service.DataSource;

import java.io.IOException;

public class ReportingViewController {

    @FXML
    private HBox reportItemHBox;

    @FXML
    private HBox reportPromptLabel;

    @FXML
    private VBox radioBtnVBox;

    private DataSource dataSource;

    @FXML
    void handleBackBtn(ActionEvent event) {
        dataSource.getReports().setCurrReport(null);
        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmitReportBtn(ActionEvent event) {

    }

    @FXML
    public void initialize() throws IOException {
      dataSource = (DataSource) FXRouter.getData();
    }

}
