package ku.cs.models.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import ku.cs.models.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductListCell extends ListCell<Product> {
    private final GridPane gridPane;
    private final Label topLabel;
    private final Label label;
    private final SVGPath warning;
    private final ImageView imageView;
    private final ColumnConstraints col2;
    private final ColumnConstraints col3;

    public ProductListCell(ListView<Product> listView) {
        gridPane = new GridPane();
        topLabel = new Label();
        label = new Label();
        imageView = new ImageView();
        warning = new SVGPath();

        prefWidthProperty().bind(listView.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);

        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);

        topLabel.getStyleClass().add("subtitle2");
        topLabel.setStyle("-fx-text-fill: on-surface-med-color;");
        label.getStyleClass().add("subtitle1");
        label.setStyle("-fx-text-fill: on-surface-color");

        ColumnConstraints col1 = new ColumnConstraints();
        col2 = new ColumnConstraints();
        col1.setPercentWidth(15);
        col2.setPercentWidth(85);
        col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setHgap(8);

        GridPane.setHalignment(warning, HPos.RIGHT);

        VBox box = new VBox(topLabel, label);
        box.setSpacing(3);
        gridPane.add(imageView, 0, 0);
        gridPane.add(box, 1, 0);
    }

    private void setTime(LocalDateTime time){
        String pattern = "HH:mm - d MMM yy";
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
        topLabel.setText(simpleDateFormat.format(time));
    }

    private void setImage(String imagePath){
        imageView.setImage(new Image(imagePath));
    }

    private void updateStock(boolean stockIsLow){
        warning.setContent("M12 5.99L19.53 19H4.47L12 5.99M12 2L1 21h22L12 2zm1 14h-2v2h2v-2zm0-6h-2v4h2v-4z");
        warning.setStyle("-fx-fill: error-color");

        if (stockIsLow){
            if (!gridPane.getColumnConstraints().contains(col3))
                gridPane.getColumnConstraints().add(col3);
            if (!gridPane.getChildren().contains(warning))
                gridPane.add(warning, 2, 0);
        } else {
            gridPane.getColumnConstraints().remove(col3);
            gridPane.getChildren().remove(warning);
        }
    }

    @Override
    protected void updateItem(Product product, boolean b) {
        super.updateItem(product, b);
        if (product != null && !b) {
            setTime(product.getRolloutDate());
            label.setText(product.getName());
            updateStock(product.stockIsLow());
            setImage(product.getPicturePath());
            setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: surface-overlay");
            setPadding(new Insets(5, 15, 8, 8));
            setGraphic(gridPane);
        } else {
            setGraphic(null);
            setStyle(null);
        }
    }
}
