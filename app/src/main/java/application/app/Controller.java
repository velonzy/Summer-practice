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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Controller {
    private final Desktop desktop = Desktop.getDesktop(); // информация об устройстве для считывания файла
    public Button endStepByStepButton;
    @FXML
    private Button btnA, btnStart, btnReadFromFile, btnReadFromWindow, btnSBS;

    @FXML
    private Button btnAddVertex, btnAddEdge, btnDeleteVertex, btnDeleteEdge, btnClear;

    @FXML
    private Button btnStepBack, btnStepForward, btnToEnd;

    @FXML
    private TextArea window, resultWindow;

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
    private Alert alertPath, alertError;

    private boolean eventFlag;

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
        alertPath = new Alert(AlertType.INFORMATION);
        eventFlag = true;
        alertError = new Alert(AlertType.ERROR);
        alertError.setTitle("Error");
    }

    @FXML
    public void switchToGraphWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Graph window.fxml")));
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 550));
    }

    @FXML
    public void readFromWindow() throws NumberFormatException, DataFormatException{
        try {
            graph = new GraphController();
            graph.readGraph(window.getText());
            graph.drawGraph(pane);
        } catch (NumberFormatException e){
            alertError.setHeaderText("Invalid input");
            alertError.setContentText("Error in numerical values");
            alertError.showAndWait();
            graph = new GraphController();
            graph.drawGraph(pane);
        } catch (DataFormatException e){
            alertError.setHeaderText("Invalid input");
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
            graph.getGraph().clearGraph();
            graph = new GraphController();
            graph.drawGraph(pane);
        }
    }

    @FXML
    public void readFromFile() throws NumberFormatException, IOException, DataFormatException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        String path;
        if(file != null) {
            path = file.getPath();
        } else {
            return;
        }
        try {
            graph = new GraphController();
            graph.readGraphFromFile(path);
            graph.drawGraph(pane);
        } catch (NumberFormatException e){
            alertError.setHeaderText("Invalid input");
            alertError.setContentText("Error in numerical values");
            alertError.showAndWait();
            graph = new GraphController();
            graph.drawGraph(pane);
        } catch (DataFormatException e){
            alertError.setHeaderText("Invalid input");
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
            graph.getGraph().clearGraph();
            graph = new GraphController();
            graph.drawGraph(pane);
        }
    }

    @FXML
    public void mouseMoved(MouseEvent event){
        graph.drawGraphAndLabels(pane);
    }

    @FXML
    public void mouseClick(MouseEvent event){
        if (eventFlag) {
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
                    setHandlers();
                } if (event.getClickCount() == 2) {
                    if (contextMenu.isShowing()) {
                        contextMenu.hide();
                    }
                    char name = graph.getGraph().getAvailableName();
                    Vertex vertex = new Vertex(name, event.getX(), event.getY());
                    graph.drawVertex(pane, vertex);
                    graph.drawGraph(pane);
                }
            }
        }

    }

    private void setHandlers() {
        for (VertexDrawable vertexDrawable : graph.getVertexesDrawable()) {
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
                            graph.getGraph().addAvailableName(oldName);
                            vertexDrawable.setName(String.valueOf(name));
                            graph.getGraph().deleteAvailableName(name.charAt(0));
                            graph.renewEdgesNames();
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
    }

    @FXML
    public void stepByStepSolution(){
        resultWindow.clear();
        eventFlag = false;
        btnClear.setDisable(true);
        btnAddEdge.setDisable(true);
        btnDeleteEdge.setDisable(true);
        btnA.setDisable(true);
        btnReadFromFile.setDisable(true);
        btnReadFromWindow.setDisable(true);
        btnSBS.setDisable(true);
        graph.startStepByStep();
        btnStepBack.setVisible(true);
        btnStepForward.setVisible(true);
        btnToEnd.setVisible(true);
        endStepByStepButton.setVisible(true);
        graph.drawGraphAndLabels(pane);
    }

    @FXML
    public void stepBack() {
        graph.doStep(-1);
        graph.drawGraphAndLabels(pane);
    }

    @FXML
    public void stepForward() {
        graph.doStep(1);
        graph.drawGraphAndLabels(pane);
        if (graph.isFinalInStepByStep()) {
            String path = graph.getPath();
            if (path == null || path.equals("")){
                resultWindow.setText("Path doesn't exist");
            } else {
                resultWindow.setText("Results:" + "\n" + "Path: " + path + "\n" + "Weight: " + Double.toString(graph.getWeight()));
            }
        }
    }

    @FXML
    public void toEnd() {
        graph.toEndStepByStep();
        graph.drawGraphAndLabels(pane);
        String path = graph.getPath();
        if (path == null || path.equals("")){
            resultWindow.setText("Path doesn't exist");
        } else {
            resultWindow.setText("Results:" + "\n" + "Path: " + path + "\n" + "Weight: " + Double.toString(graph.getWeight()));
        }
    }

    @FXML
    public void endStepByStep() {
        eventFlag = true;
        btnClear.setDisable(false);
        btnAddEdge.setDisable(false);
        btnDeleteEdge.setDisable(false);
        btnA.setDisable(false);
        btnReadFromFile.setDisable(false);
        btnReadFromWindow.setDisable(false);
        btnSBS.setDisable(false);
        btnStepBack.setVisible(false);
        btnStepForward.setVisible(false);
        btnToEnd.setVisible(false);
        endStepByStepButton.setVisible(false);
        graph.normalGraphColor();
        graph.allowEvents();
        graph.endSBS();
        graph.drawGraph(pane);
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
        Vertex vertexA = graph.getGraph().findVertex('a');
        Vertex vertexB = graph.getGraph().findVertex('z');
        graph.runningAlgorithmAStar(vertexA, vertexB);
        alertPath.setTitle("Astar solution");
        alertPath.setHeaderText("Results:");
        String path = graph.getPath();
        if (path == null || path.equals("")){
            resultWindow.setText("Path doesn't exist");
            alertPath.setContentText("Path doesn't exist");
            alertPath.showAndWait();
        } else {
            resultWindow.setText("Results:" + "\n" + "Path: " + path + "\n" + "Weight: " + Double.toString(graph.getWeight()));
            alertPath.setContentText("Path: " + path + "\n" + "Weight: " + Double.toString(graph.getWeight()));
            alertPath.showAndWait();
        }
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

    @FXML
    public void clearGraph(ActionEvent event){
        graph = new GraphController();
        graph.drawGraph(pane);
    }

}