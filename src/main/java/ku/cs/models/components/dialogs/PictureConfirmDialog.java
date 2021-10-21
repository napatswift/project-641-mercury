package ku.cs.models.components.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PictureConfirmDialog extends Dialog<Boolean> {
    public PictureConfirmDialog(Image image) {
        setStyleSheet();
        final DialogPane dialogPane = getDialogPane();
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200);
        HBox text = new HBox(new Label("Do you want to upload this picture to product?"));
        vBox.getChildren().addAll(imageView, text);

        dialogPane.setContent(vBox);

        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(
                (dialogButton) -> {
                    ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
                    return data == ButtonBar.ButtonData.OK_DONE;
                }
        );

    }

    public void setStyleSheet(){
        getDialogPane().getStylesheets().add(getClass().getResource("/ku/cs/style/style.css").toString());
        getDialogPane().getStylesheets().add(com.github.saacsos.FXRouter.getTheme().getThemePath());
    }
}
