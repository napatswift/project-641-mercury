package ku.cs.service;

import java.io.File;

public class Theme {
    public enum ColorTheme {DEFAULT, T_262626, T_EE8002 , T_00965A};
    private ColorTheme currTheme;
    private final String dir = "/ku/cs/style";

    public Theme() {
        this.currTheme = ColorTheme.DEFAULT;
    }

    public boolean setCurrTheme(ColorTheme theme){
        if (currTheme != theme) {currTheme = theme; return true;}
        return false;
    }

    public String getThemePath(){
        String path = dir + File.separator + (currTheme == ColorTheme.DEFAULT ? "style-color" : currTheme) + ".css";
        return Theme.class.getResource(path).toExternalForm();
    }
}
