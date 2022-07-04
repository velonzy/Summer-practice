package application.app;

//import project.application.canvas.EdgeDrawable;
//import project.application.canvas.VertexDrawable;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphController { //для считывания графа
    ArrayList<VertexDrawable> vertexesDrawable;
    Graph graph;
    ArrayList<EdgeDrawable> edgesDrawable;

//    public GraphController(){
//        vertexesDrawable = new ArrayList<VertexDrawable>();
//        edgesDrawable = new ArrayList<EdgeDrawable>();
//        graph = new Graph();
//    }

    public void readGraph(String string){
        vertexesDrawable = new ArrayList<VertexDrawable>();
        edgesDrawable = new ArrayList<EdgeDrawable>();
        graph = new Graph();

        String[] tokens = string.split("[\n ]");
        int numberOfVertexes = Integer.parseInt(tokens[0]);
        for (int i = 1; i < numberOfVertexes * 3; i += 3){
            Vertex vertex = new Vertex(tokens[i].charAt(0), Double.parseDouble(tokens[i + 1]), Double.parseDouble(tokens[i + 2]));
            graph.addVertex(vertex);
            vertexesDrawable.add(new VertexDrawable(vertex));
        }
        for (int i = numberOfVertexes * 3 + 1; i < tokens.length; i+= 3){
            char start, finish;
            double weight;
            start = tokens[i].charAt(0);
            finish = tokens[i + 1].charAt(0);
            weight = Double.parseDouble(tokens[i + 2]);
            Vertex sVertex, fVertex;
            sVertex = graph.findVertex(start);
            fVertex = graph.findVertex(finish); // добавить исключение, когда одно из них не найдено
            graph.addEdge(sVertex, fVertex, weight);
            edgesDrawable.add(new EdgeDrawable(sVertex, fVertex, weight));
        }
    }

    public void drawGraph(Pane pane){
        pane.getChildren().clear();
        for (VertexDrawable vertex: vertexesDrawable){
            pane.getChildren().add(vertex.getView());
            pane.getChildren().add(vertex.getName());
        }
        for (EdgeDrawable edge: edgesDrawable){
            pane.getChildren().add(edge.getView());
            pane.getChildren().add(edge.getName());
        }
    }
}
