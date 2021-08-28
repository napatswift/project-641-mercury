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
    private static int S_WIDTH = 450;
    private static int L_WIDTH = 1024;
    private static int HEIGHT = 768;

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, appName, L_WIDTH, HEIGHT);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute(){
        String packageStr = "ku/cs/";
        FXRouter.when("login", packageStr+"login.fxml", S_WIDTH, HEIGHT);
        FXRouter.when("sign_up", packageStr+"sign_up.fxml", S_WIDTH, HEIGHT);
        FXRouter.when("sign_up_profile_picture", packageStr+"sign_up_profile_picture.fxml",S_WIDTH, HEIGHT);
        FXRouter.when("how_to_register", packageStr+"how_to_register.fxml");
        FXRouter.when("how_to_use", packageStr+"how_to_use.fxml");
        FXRouter.when("how_to_market", packageStr+"how_to_market.fxml");
        FXRouter.when("how_to_seller", packageStr+"how_to_seller.fxml");
        FXRouter.when("admin_page", packageStr+"admin_page.fxml");
        FXRouter.when("about_us", packageStr+"about_us.fxml");
        FXRouter.when("marketplace", packageStr + "marketplace.fxml");
        FXRouter.when("reset_password", packageStr + "reset_password.fxml",S_WIDTH, 600);
        FXRouter.when("create_store", packageStr + "create_store.fxml");
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