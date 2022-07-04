package application.app;

//import project.application.canvas.EdgeDrawable;
//import project.application.canvas.VertexDrawable;
import javafx.scene.layout.Pane;

// files
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

public class GraphController { //для считывания графа
    ArrayList<VertexDrawable> vertexesDrawable;
    Graph graph;
    ArrayList<EdgeDrawable> edgesDrawable;

//    public GraphController(){
//        vertexesDrawable = new ArrayList<VertexDrawable>();
//        edgesDrawable = new ArrayList<EdgeDrawable>();
//        graph = new Graph();
//    }

    //в readGraphFromWindow, readGraphFromFile добавить исключения, если вводимые данные пустые, плюс для
    // файлов исключение на то, что файл не существует
    public void readGraphFromWindow(String string){
        vertexesDrawable = new ArrayList<VertexDrawable>();
        edgesDrawable = new ArrayList<EdgeDrawable>();
        if (graph != null) {
            graph.clearGraph();
        } else {
            graph = new Graph();
        }

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

    public void readGraphFromFile(String fileName) throws IOException {
        vertexesDrawable = new ArrayList<VertexDrawable>();
        edgesDrawable = new ArrayList<EdgeDrawable>();
        if (graph != null) {
            graph.clearGraph();
        } else {
            graph = new Graph();
        }
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line;
        int index = 0, numberOfVertexes = 0;
        while((line = br.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (index == 0) {
                numberOfVertexes = Integer.parseInt(tokens[0]);
            } else if (index <= numberOfVertexes) {
                Vertex vertex = new Vertex(tokens[0].charAt(0), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                graph.addVertex(vertex);
                vertexesDrawable.add(new VertexDrawable(vertex));
            } else {
                char start, finish;
                double weight;
                start = tokens[0].charAt(0);
                finish = tokens[1].charAt(0);
                weight = Double.parseDouble(tokens[2]);
                Vertex sVertex, fVertex;
                sVertex = graph.findVertex(start);
                fVertex = graph.findVertex(finish); // добавить исключение, когда одно из них не найдено
                graph.addEdge(sVertex, fVertex, weight);
                edgesDrawable.add(new EdgeDrawable(sVertex, fVertex, weight));
            }
            index ++;
        }
        br.close();
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
