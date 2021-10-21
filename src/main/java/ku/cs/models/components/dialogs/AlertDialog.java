package ku.cs.models.components.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class AlertDialog{
    public static void alertDialog(String headerText,String detail){
        Alert alert = new Alert(
                Alert.AlertType.NONE,
                detail,
                ButtonType.OK);
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
