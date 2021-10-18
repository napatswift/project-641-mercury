package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class App extends Application {
    private String appName = "MERCURY";
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
        FXRouter.when("how_to", packageStr+"how_to.fxml");
        FXRouter.when("admin_page", packageStr+"admin_page.fxml");
        FXRouter.when("about_us", packageStr+"about_us.fxml");
        FXRouter.when("marketplace", packageStr + "marketplace.fxml");
        FXRouter.when("reset_password", packageStr + "reset_password.fxml",S_WIDTH, HEIGHT);
        FXRouter.when("create_store", packageStr + "create_store.fxml");
        FXRouter.when("my_store", packageStr + "my_store.fxml");
        FXRouter.when("reporting", packageStr + "reporting.fxml");
        FXRouter.when("my_account", packageStr + "my_account.fxml");
        FXRouter.when("create_coupon",packageStr + "create_coupon.fxml");

    }

    public static void main(String[] args) {
        launch();
    }
}