package ku.cs.models.components.dialogs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConfirmEditProductDialog extends Dialog<Boolean> {
    public ConfirmEditProductDialog(String productName, String price, String stock) {
        setStyleSheet();
        DialogPane dialogPane = getDialogPane();
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(15));

        Label productNameLabel = new Label(productName);
        productNameLabel.setWrapText(true);
        productNameLabel.setMaxWidth(400);
        Label priceLabel = new Label("price: $" + price);
        Label stockLabel = new Label("stock: " + stock);

        Label dialogTitle = new Label("Your change looks good?");
        HBox dialogHBox = new HBox(dialogTitle);
        dialogHBox.setPadding(new Insets(20, 0, 0, 0));

        productNameLabel.getStyleClass().add("h6");
        priceLabel.getStyleClass().add("body1");
        stockLabel.getStyleClass().add("body1");

        vBox.getChildren().addAll(productNameLabel, priceLabel, stockLabel, dialogHBox);

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
