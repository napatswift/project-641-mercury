package ku.cs.models.components.theme;

import com.github.saacsos.FXRouter;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import ku.cs.service.Theme;

import java.io.IOException;

public class ThemeMenuItem extends MenuItem {

    public ThemeMenuItem(final Theme.ColorTheme colorTheme, final String color, final ThemeMenu themeMenu) {
        super(colorTheme.toString());

        Circle colorCircle = new Circle(8);
        colorCircle.setStyle("-fx-fill: #" + color);
        setGraphic(colorCircle);

        setOnAction(event -> {
                FXRouter.setTheme(colorTheme);
                themeMenu.getScene().getRoot().getStylesheets().set(1, FXRouter.getTheme().getThemePath());
        });
    }
}
