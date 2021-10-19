package ku.cs.models.components.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TextDialog extends Dialog<Boolean> {

    public TextDialog(String headerText, String detail) {
        setStyleSheet();
        final DialogPane dialogPane = getDialogPane();
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_LEFT);
        Label headerLabel = new Label(headerText);
        headerLabel.getStyleClass().add("h6");
        Label detailLabel = new Label(detail);
        detailLabel.getStyleClass().add("body1");
        detailLabel.setWrapText(true);

        vBox.getChildren().addAll(headerLabel, detailLabel);

        dialogPane.setContent(vBox);

        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(
                (dialogButton) -> {
                    if (dialogButton == null) return false;
                    ButtonBar.ButtonData data = dialogButton.getButtonData();
                    return data == ButtonBar.ButtonData.OK_DONE;
                }
        );

    }

    public void setStyleSheet(){
        getDialogPane().getStylesheets().add(getClass().getResource("/ku/cs/style/style.css").toString());
        getDialogPane().getStylesheets().add(com.github.saacsos.FXRouter.getTheme().getThemePath());
    }
}
