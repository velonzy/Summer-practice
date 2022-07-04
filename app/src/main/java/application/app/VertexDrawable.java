package application.app;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//import project.application.model.*;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;

public class VertexDrawable {
    private char name;
    private Circle vertexCircle;
    private Text nameCircle;
    private Vertex vertex;
    private Coordinates coordinates;

    public VertexDrawable(Vertex vertex){
        this.vertex = vertex;
        name = vertex.getName();
        coordinates = vertex.getCoordinates();
        vertexCircle = new Circle();
        vertexCircle.setLayoutX((Double) coordinates.getX());
        vertexCircle.setLayoutY((Double) coordinates.getY());
        vertexCircle.setRadius(15);
        vertexCircle.setFill(Paint.valueOf("#3fbab4"));
        nameCircle = new Text(String.valueOf(name));
        nameCircle.setX((Double) coordinates.getX());
        nameCircle.setY((Double) coordinates.getY());
        vertexCircle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vertexCircle.setCursor(Cursor.HAND);
            }
        });
    }

    public Circle getView(){
        return vertexCircle;
    }

    public Text getName(){
        return nameCircle;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void moveCircle() {
        coordinates = vertex.getCoordinates();
        vertexCircle.setLayoutX((Double) coordinates.getX());
        vertexCircle.setLayoutY((Double) coordinates.getY());
        nameCircle.setX((Double) coordinates.getX());
        nameCircle.setY((Double) coordinates.getY());
    }
}
