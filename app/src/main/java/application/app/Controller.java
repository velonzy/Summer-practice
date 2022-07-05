package application.app;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button btnA, btnStart, btnReadFromFile, btnReadFromWindow, btnSBS;

    @FXML
    private Button btnAddVertex, btnAddEdge, btnDeleteVertex, btnDeleteEdge, btnClear;

    @FXML
    private TextArea window;

    @FXML
    private Pane pane;

    private GraphController graph;

    @FXML
    public void switchToGraphWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Graph window.fxml"));
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 550));
    }

    @FXML
    public void readFromWindow() throws IOException {
        graph = new GraphController();
        graph.readGraphFromWindow(window.getText());
        graph.drawGraph(pane);
        //Vertex vertexA = graph.graph.findVertex('a');
        //Vertex vertexB = graph.graph.findVertex('b');
        //graph.runningAlgorithmAStar(vertexA, vertexB);
//        readFromFile();
    }

    public void readFromFile() throws IOException {
        String fileName = "/home/anna/IdeaProjects/app/src/main/resources/data.txt";
        graph = new GraphController();
        graph.readGraphFromFile(fileName);
        graph.drawGraph(pane);
    }

    @FXML
    public void mouseRightButton(MouseEvent event){
        
    }

    @FXML
    public void mousePressed(MouseEvent event) {
        btnStart.setStyle("-fx-background-color: linear-gradient(to bottom left, #3fbab4, #410b61)");
    }

    @FXML
    public void mouseRelease(MouseEvent event) {
        btnStart.setStyle("-fx-background-color: linear-gradient(to bottom left, #52fff6, #7012a6)");
    }

    @FXML
    public void mouseMenuPressed(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color:    linear-gradient(to bottom left, #52fff6, #9a35d4)");
    }

    @FXML
    public void mouseMenuMove(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color:   linear-gradient(to bottom left, #a2fcf8, #d5b8e6)");
    }

    @FXML
    public void mouseMenuRelease(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: white");
    }

    @FXML
    public void switchToGraph(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Start window.fxml"));
        Stage stage = (Stage) btnA.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 550));
    }

}