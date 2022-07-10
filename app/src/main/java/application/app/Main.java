package application.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Tests.RunTests();
        URL cl;
        Parent root;
        if ((cl = getClass().getResource("Start window.fxml")) != null){
            root = FXMLLoader.load(cl);
        } else {
            return;
        }
        primaryStage.setTitle("A* algorithm");
        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
