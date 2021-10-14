package ku.cs.models.components.listCell;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class PromotionListCell extends ListCell<Coupon> {
    private boolean status;
    private final Label codeLabel;
    private final Label descriptionLabel;
    private final SVGPath statusToggleBtnSvgPath;

    public PromotionListCell() {
        codeLabel = new Label();
        Label codeTextLabel = new Label("code");
        descriptionLabel = new Label();
        statusToggleBtnSvgPath = new SVGPath();
        Button activeStatusToggleButton = new Button();
        activeStatusToggleButton.setGraphic(statusToggleBtnSvgPath);
        activeStatusToggleButton.setOnAction(this::handleToggleBtn);
        Button deleteButton = new Button();
        SVGPath deleteSvgPath = new SVGPath();
        deleteSvgPath.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
        deleteButton.setGraphic(deleteSvgPath);

        getStylesheets().add("promotion-list-cell");
        codeLabel.getStyleClass().add("h6");
        codeTextLabel.getStyleClass().add("caption");
        descriptionLabel.getStyleClass().add("body1");

        GridPane gridPane = new GridPane();
        ColumnConstraints tCol1 = new ColumnConstraints(); tCol1.setPercentWidth(25);
        ColumnConstraints tCol2 = new ColumnConstraints(); tCol2.setPercentWidth(50);
        ColumnConstraints tCol3 = new ColumnConstraints(); tCol3.setPercentWidth(25);
        gridPane.getColumnConstraints().addAll(tCol1, tCol2, tCol3);
        VBox codeVBox = new VBox(codeTextLabel, codeLabel);
        VBox descVBox = new VBox(descriptionLabel);
        HBox statusHBox = new HBox(activeStatusToggleButton, deleteButton);

        gridPane.add(codeVBox, 0, 0);
        gridPane.add(descVBox, 1, 0);
        gridPane.add(statusHBox, 2, 0);

    }

    private void handleToggleBtn(ActionEvent event) {
        if (status)
            statusToggleBtnSvgPath.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zm0 8c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
        else
            statusToggleBtnSvgPath.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zM7 15c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
        status = !status;
    }

    private void updateInfo(){
        codeLabel.setText("SOME4CODE");
        descriptionLabel.setText("Minimum Spend $329\n" +
                "$12 discount");
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(false);
    }
}
