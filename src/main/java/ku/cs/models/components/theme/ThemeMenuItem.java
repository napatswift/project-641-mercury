package ku.cs.models.components.theme;

import com.github.saacsos.FXRouter;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import ku.cs.service.Theme;

import java.io.IOException;

public class ThemeMenuItem extends MenuItem {
    Theme.ColorTheme colorTheme;

    public ThemeMenuItem(Theme.ColorTheme colorTheme, String color) {
        super(colorTheme.toString());

        Circle colorCircle = new Circle(8);
        colorCircle.setStyle("-fx-fill: #" + color);
        setGraphic(colorCircle);

        setOnAction(event -> {
            try {
                FXRouter.setTheme(colorTheme);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
