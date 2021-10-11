package ku.cs.service;

public class Theme {
    public enum ColorTheme {DEFAULT, T_262626, T_EE8002 , T_00965A, T_FF3A32}
    private ColorTheme currTheme;
    private final String dir = "/ku/cs/style/themes/";

    public Theme() {
        this.currTheme = ColorTheme.DEFAULT;
    }

    public boolean setCurrTheme(ColorTheme theme){
        if (currTheme != theme) {currTheme = theme; return true;}
        return false;
    }

    public String getThemePath(){
        String path = dir + (currTheme == ColorTheme.DEFAULT ? "style-color" : currTheme) + ".css";
        return Theme.class.getResource(path).toExternalForm();
    }
}
