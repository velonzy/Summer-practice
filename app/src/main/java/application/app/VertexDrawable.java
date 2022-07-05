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
    private final static double radiusOfVertexCircle = 18.0;
    public VertexDrawable(Vertex vertex){
        this.vertex = vertex;
        name = vertex.getName();
        coordinates = vertex.getCoordinates();
        vertexCircle = new Circle();
        vertexCircle.setLayoutX((Double) coordinates.getX());
        vertexCircle.setLayoutY((Double) coordinates.getY());
        vertexCircle.setRadius(radiusOfVertexCircle);
        vertexCircle.setFill(Paint.valueOf("#d5b8e6"));
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
    public void setName(String name){
        nameCircle.setText(name);
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
