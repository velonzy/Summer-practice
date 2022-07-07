package application.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    private final Desktop desktop = Desktop.getDesktop(); // информация об устройстве для считывания файла
    @FXML
    private Button btnA, btnStart, btnReadFromFile, btnReadFromWindow, btnSBS;

    @FXML
    private Button btnAddVertex, btnAddEdge, btnDeleteVertex, btnDeleteEdge, btnClear;

    @FXML
    private Button btnStepBack, btnStepForward, btnToEnd;

    @FXML
    private TextArea window;

    @FXML
    private Pane pane;

    private GraphController graph;

    @FXML
    private ContextMenu contextMenu, contextMenuPane;

    @FXML
    private MenuItem menuItemRenameVertex, menuItemDeleteVertex;

    @FXML
    private MenuItem menuItemAddVertex;

    @FXML
    private TextInputDialog dialogRenameVertex, dialogSetWeight;


    @FXML
    public void initialize(){
        graph = new GraphController();
        contextMenu = new ContextMenu();
        contextMenuPane = new ContextMenu();
        menuItemRenameVertex = new MenuItem("Rename vertex");
        menuItemDeleteVertex = new MenuItem("Delete vertex");
        contextMenu.getItems().addAll(menuItemRenameVertex, menuItemDeleteVertex);
        dialogRenameVertex = new TextInputDialog();
        dialogSetWeight = new TextInputDialog();
        menuItemAddVertex = new MenuItem("Add vertex");
        contextMenuPane.getItems().add(menuItemAddVertex);
    }

    @FXML
    public void switchToGraphWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Graph window.fxml"));
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 550));
    }

    @FXML
    public void readFromWindow() {
        graph = new GraphController();
        graph.readGraphFromWindow(window.getText());
        graph.drawGraph(pane);
    }

    @FXML
    public void readFromFile() throws IOException {
        // добавить сисключения на тип файла
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        String path;
        if(file != null) {
            path = file.getPath();
        } else {
            return;
        }
        graph = new GraphController();
        graph.readGraphFromFile(path);
        graph.drawGraph(pane);
    }

    @FXML
    public void mouseMoved(MouseEvent event){
        graph.drawGraph(pane);
    }

    @FXML
    public void mouseClick(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY){
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
            if (event.getClickCount() == 2){
                graph.setEventHandlers();
            }
        }
        if (event.getButton() == MouseButton.SECONDARY){
            if (event.getClickCount() == 1) {
                for (VertexDrawable vertexDrawable : graph.vertexesDrawable) {
                    vertexDrawable.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            contextMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                            menuItemRenameVertex.setOnAction((ActionEvent actionEvent) -> {
                                if (contextMenu.isShowing()) {
                                    contextMenu.hide();
                                }
                                Vertex vertex = vertexDrawable.getVertex();
                                char oldName = vertex.getName();
                                //добавить проверку, что такое имя не занято
                                dialogRenameVertex.setTitle("Rename vertex");
                                dialogRenameVertex.setHeaderText("Enter vertex name:");
                                dialogRenameVertex.setContentText("Name:");
                                Optional<String> newName = dialogRenameVertex.showAndWait();
                                newName.ifPresent(name -> {
                                    graph.graph.addAvailableName(oldName);
                                    vertexDrawable.setName(String.valueOf(name));
                                    graph.graph.deleteAvailableName(name.charAt(0));
                                    //vertexDrawable.getVertex().
                                });
                                graph.drawGraph(pane);
                            });
                            menuItemDeleteVertex.setOnAction((ActionEvent actionEvent) -> {
                                if (contextMenu.isShowing()) {
                                    contextMenu.hide();
                                }
                                graph.deleteVertex(vertexDrawable);
                                graph.drawGraph(pane);
                            });
                        }
                    });
                }
            } if (event.getClickCount() == 2) {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
                char name = graph.graph.getAvailableName();
                Vertex vertex = new Vertex(name, event.getX(), event.getY());
                graph.drawVertex(pane, vertex);
                graph.drawGraph(pane);
            }
        }
    }

    @FXML
    public void stepByStepSolution(){
        btnStepBack.setVisible(true);
        btnStepForward.setVisible(true);
        btnToEnd.setVisible(true);
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
    public void runAStar(ActionEvent event) throws IOException {
        Vertex vertexA = graph.graph.findVertex('a');
        Vertex vertexB = graph.graph.findVertex('z');
        graph.runningAlgorithmAStar(vertexA, vertexB);
    }

    @FXML
    public void addEdgeClicked(ActionEvent event) throws IOException{
        dialogSetWeight.setTitle("Set weight");
        dialogSetWeight.setHeaderText("Enter weight:");
        dialogSetWeight.setContentText("Weight:");
        Optional <String> newWeight = dialogSetWeight.showAndWait();
        newWeight.ifPresent(weight -> {
            graph.addEdge(Double.valueOf(weight));
            graph.drawGraph(pane);
        });

    }

    @FXML
    public void deleteEdgeClicked(ActionEvent event) {
        graph.deleteEdge();
        graph.drawGraph(pane);
    }

}