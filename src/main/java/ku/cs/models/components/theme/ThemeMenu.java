package ku.cs.models.components.theme;

import javafx.scene.control.MenuButton;
import ku.cs.service.Theme;

public class ThemeMenu extends MenuButton {
    public ThemeMenu() {
        setText("Theme Setting");
        getStylesheets().add(getClass().getResource("/ku/cs/style/components/theme-menu-button.css").toExternalForm());

        for (Theme.ColorTheme e: Theme.ColorTheme.values()) {
            String hexColor = (e == Theme.ColorTheme.DEFAULT ? "6200ee" : e.toString().split("_")[1]);
            getItems().add(new ThemeMenuItem(e, hexColor, this));
        }
    }
}
