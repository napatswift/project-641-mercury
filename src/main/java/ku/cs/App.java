package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;


import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private String appName = "App Name";

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, appName, 1024, 768);
        configRoute();
        FXRouter.goTo("login");
//        scene = new Scene(loadFXML("login"), 450, 700);
//        scene.getStylesheets().add(getClass().getResource("/ku/cs/style/style.css").toExternalForm());
//        stage.setTitle(appName);

//        stage.setScene(scene);
//        stage.show();
    }

    private static void configRoute(){
        String packageStr = "ku/cs/";
        FXRouter.when("login", packageStr+"login.fxml");
        FXRouter.when("sign_up", packageStr+"sign_up.fxml");
        FXRouter.when("sign_up_profile_picture", packageStr+"sign_up_profile_picture");
    }

    public static void setRoot(FXMLLoader loader) throws IOException{
        scene.setRoot(loader.load());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}