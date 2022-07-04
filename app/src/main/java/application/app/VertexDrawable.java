package application.app;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//import project.application.model.*;
import javafx.scene.text.Text;

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
        vertexCircle.setCenterX((Double) coordinates.getX());
        vertexCircle.setCenterY((Double) coordinates.getY());
        vertexCircle.setRadius(15);
        vertexCircle.setFill(Paint.valueOf("#3fbab4"));
        nameCircle = new Text(String.valueOf(name));
        nameCircle.setX((Double) coordinates.getX());
        nameCircle.setY((Double) coordinates.getY());
    }

    public Circle getView(){
        return vertexCircle;
    }

    public Text getName(){
        return nameCircle;
    }
}
