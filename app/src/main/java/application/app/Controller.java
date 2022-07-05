package application.app;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class Controller {

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
//        Vertex vertexA = graph.graph.findVertex('a');
//        Vertex vertexB = graph.graph.findVertex('z');
//        graph.runningAlgorithmAStar(vertexA, vertexB);
//        readFromFile();
    }

    @FXML
    public void readFromFile() throws IOException {
        String fileName = "/home/anna/IdeaProjects/app/src/main/resources/data.txt";
        graph = new GraphController();
        graph.readGraphFromFile(fileName);
        graph.drawGraph(pane);
    }

    @FXML
    public void mouseClick(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY){
            if (event.getClickCount() == 1) {
                if (contextMenu.isShowing()){
                    contextMenu.hide();
                }
            } else if (event.getClickCount() == 2){
                double x = event.getSceneX() - 174;
                double y = event.getScreenY() - 112;
                char name = graph.graph.getAvailableName();
                Vertex vertex = new Vertex(name, x, y);
                graph.drawVertex(pane, vertex);
                graph.drawGraph(pane);
            }
        } else if (event.getButton() == MouseButton.SECONDARY){
            for (VertexDrawable vertexDrawable: graph.vertexesDrawable) {
                vertexDrawable.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        contextMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        menuItemRenameVertex.setOnAction((ActionEvent actionEvent) -> {
                            if (contextMenu.isShowing()){
                                contextMenu.hide();
                            }
                            dialogRenameVertex.setTitle("Rename vertex");
                            dialogRenameVertex.setHeaderText("Enter vertex name:");
                            dialogRenameVertex.setContentText("Name:");
                            Optional <String> newName = dialogRenameVertex.showAndWait();
                            newName.ifPresent(name -> {
                                vertexDrawable.setName(String.valueOf(name));
                                //vertexDrawable.getVertex().
                            });
                        });

                        menuItemDeleteVertex.setOnAction((ActionEvent actionEvent) -> {
                            if (contextMenu.isShowing()){
                                contextMenu.hide();
                            }
                            //тут пишешь удаление
                        });
                    }
                });
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
    public void switchToGraph(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Start window.fxml"));
        Stage stage = (Stage) btnA.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 550));
    }

}