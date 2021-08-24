package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/market_place.fxml"));
        scene = new Scene(loader.load(), 1000, 750);
        scene.getStylesheets().add(getClass().getResource("/ku/cs/style/light.css").toExternalForm());

        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.show();
    }
}
