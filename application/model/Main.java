package application.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../canvas/Start window.fxml"));
        primaryStage.setTitle("A* algorithm");
        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.show();
        //  linear-gradient(to bottom left, #52fff6, #9a35d4)
    }
}
