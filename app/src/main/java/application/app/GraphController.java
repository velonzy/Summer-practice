package application.app;

import javafx.event.EventHandler;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

// files
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GraphController { //для считывания графа
    ArrayList<VertexDrawable> vertexesDrawable;
    Graph graph;
    ArrayList<EdgeDrawable> edgesDrawable;
    AStar aStar;

    Vertex chosen1, chosen2;

    public GraphController(){
        vertexesDrawable = new ArrayList<VertexDrawable>();
        graph = new Graph();
        edgesDrawable = new ArrayList<EdgeDrawable>();
    }

    private VertexDrawable findVertex(Vertex vertex) {
        for(VertexDrawable v: vertexesDrawable) {
            if (v.getVertex() == vertex) {
                return v;
            }
        }
        return null;
    }

    private EdgeDrawable findEdge(Vertex vertex) {
        for(EdgeDrawable e: edgesDrawable) {
            if(e.cmpFirst(vertex) || e.cmpSecond(vertex)){
                return e;
            }
        }
        return null;
    }

    private EdgeDrawable findEdge(Vertex start, Vertex finish) {
        for(EdgeDrawable e: edgesDrawable){
            if(e.cmpBoth(start, finish)){
                return e;
            }
        }
        return null;
    }

    public void runningAlgorithmAStar(Vertex first, Vertex second) {
        for (VertexDrawable v : vertexesDrawable){
            v.getView().setFill(Paint.valueOf("#d5b8e6"));
        }
        for (EdgeDrawable e : edgesDrawable) {
            e.getView().setStroke(Paint.valueOf("#806b8d"));
        }
        ArrayList<Vertex> solution = new ArrayList<>();
        aStar = new AStar();
        if (chosen1 != null && chosen2 != null) {
            solution = aStar.a_star_public(chosen1, chosen2, graph.getVertexes());
            VertexDrawable vertex1 = findVertex(chosen1);
            VertexDrawable vertex2 = findVertex(chosen2);
            if(vertex1 != null){
                vertex1.getView().setEffect(null);
            }
            if (vertex2 != null) {
                vertex2.getView().setEffect(null);
            }
            chosen1 = null;
            chosen2 = null;
        } else {
            return;
        }

        for (Vertex i : solution) {
            VertexDrawable v = findVertex(i);
            if (v != null) {
                v.getView().setFill(Paint.valueOf("#0cda73"));
            }
            for(Vertex child : i.getNeighbours().keySet()){
                EdgeDrawable e = findEdge(i, child);
                if(e != null && solution.contains(child) && child != solution.get(0)) {
                    e.getView().setStroke(Paint.valueOf("#0a9a50"));
                }
            }
        }
    }

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
            boolean flag = false;
            for (EdgeDrawable e: edgesDrawable) {
                if (e.isReverse(sVertex, fVertex)) {
                    e.setReversedDirection(weight);
                    flag = true;
                }
            }
            if (!flag) {
                edgesDrawable.add(new EdgeDrawable(sVertex, fVertex, weight));
            }
        }
        setEventHandlers();
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
                boolean flag = false;
                for (EdgeDrawable e: edgesDrawable) {
                    if (e.isReverse(sVertex, fVertex)) {
                        e.setReversedDirection(weight);
                        flag = true;
                    }
                }
                if (!flag) {
                    edgesDrawable.add(new EdgeDrawable(sVertex, fVertex, weight));
                }
            }
            index ++;
        }
        br.close();
        setEventHandlers();
    }

    protected void setEventHandlers() {
        // добавлены функции реакции круга на перетаскивание мышью, меняются координаты вершины в окне,
        // а также меняются координаты круга
        for(VertexDrawable v: vertexesDrawable){
            final Double[] x = new Double[1];
            final Double[] y = new Double[1];
            v.getView().setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    x[0] = v.getView().getLayoutX() - mouseEvent.getSceneX();
                    y[0] = v.getView().getLayoutY() - mouseEvent.getSceneY();
                }
            });
            v.getView().setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    v.getVertex().setCoordinates(mouseEvent.getSceneX() + x[0], mouseEvent.getSceneY() + y[0]);
                    v.moveCircle();
                    for(EdgeDrawable e: edgesDrawable) {
                        e.moveLine();
                    }
                }
            });
            v.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                        if (chosen1 == v.getVertex()) {
                            VertexDrawable vertex1 = findVertex(chosen1);
                            if(vertex1 != null){
                                vertex1.getView().setEffect(null);
                            }
                            chosen1 = null;
                            return;
                        } else if (chosen2 == v.getVertex()) {
                            VertexDrawable vertex2 = findVertex(chosen2);
                            if(vertex2 != null){
                                vertex2.getView().setEffect(null);
                            }
                            chosen2 = null;
                            return;
                        }
                        if(chosen1 == null) {
                            chosen1 = v.getVertex();
                            Bloom bloom = new Bloom();
                            bloom.setThreshold(0);
                            v.getView().setEffect(bloom);
                        } else if (chosen2 == null) {
                            Bloom bloom = new Bloom();
                            bloom.setThreshold(0);
                            v.getView().setEffect(bloom);
                            chosen2 = v.getVertex();
                        } else {
                            VertexDrawable vertex1 = findVertex(chosen1);
                            VertexDrawable vertex2 = findVertex(chosen2);
                            if(vertex1 != null){
                                vertex1.getView().setEffect(null);
                            }
                            if (vertex2 != null) {
                                vertex2.getView().setEffect(null);
                            }
                            chosen1 = v.getVertex();
                            Bloom bloom = new Bloom();
                            bloom.setThreshold(0);
                            v.getView().setEffect(bloom);
                            chosen2 = null;
                        }
                    }
                }
            });
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

    public void drawVertex(Pane pane, Vertex vertex){
        graph.addVertex(vertex);
        VertexDrawable drawableVertex = new VertexDrawable(vertex);
        vertexesDrawable.add(drawableVertex);
        drawGraph(pane);
        setEventHandlers();
    }

    public void addEdge(double weight) {
        // нужно считать вес
        if (chosen1 != null && chosen2 != null) {
            graph.addEdge(chosen1, chosen2, weight);
            EdgeDrawable edgeDrawable = findEdge(chosen1, chosen2);
            if (edgeDrawable != null && edgeDrawable.isOnlyOneWay()) {
                edgeDrawable.setReversedDirection(chosen1, chosen2, weight);
            } else if (edgeDrawable == null) {
                edgesDrawable.add(new EdgeDrawable(chosen1, chosen2, weight));
            }
        }
    }

    public void deleteEdge() {
        if (chosen1 != null && chosen2 != null) {
            EdgeDrawable edgeDrawable = findEdge(chosen1, chosen2);
            if (edgeDrawable != null && edgeDrawable.isOnlyOneWay()) {
                chosen1.deleteNeighbour(chosen2);
                if (edgeDrawable.isFromFirstToSecond(chosen1, chosen2)) {
                    edgesDrawable.remove(edgeDrawable);
                } else if (edgeDrawable.isFromSecondToFirst(chosen1, chosen2)) {
                    edgesDrawable.remove(edgeDrawable);
                }
            } else if (edgeDrawable != null && edgeDrawable.isTwoDirectional()) {
                edgeDrawable.deleteOneWay(chosen1, chosen2);
                chosen1.deleteNeighbour(chosen2);
            }
        }
    }

    public void deleteVertex(VertexDrawable vertexDrawable) {
        Vertex vertex = vertexDrawable.getVertex();

        for (Vertex v : graph.getVertexes()) {
            EdgeDrawable edgeDrawable = findEdge(vertex, v);
            if (edgeDrawable != null && edgeDrawable.isOnlyOneWay()) {
                vertex.deleteNeighbour(v);
                if (!edgeDrawable.isReverse(vertex, v)) {
                    edgesDrawable.remove(edgeDrawable);
                }
            } else if (edgeDrawable != null && edgeDrawable.isTwoDirectional()) {
                edgeDrawable.deleteOneWay(vertex, v);
                vertex.deleteNeighbour(v);
            }
            edgeDrawable = findEdge(v, vertex);
            if (edgeDrawable != null && edgeDrawable.isOnlyOneWay()) {
                vertex.deleteNeighbour(v);
                if (!edgeDrawable.isReverse(v, vertex)) {
                    edgesDrawable.remove(edgeDrawable);
                }
            } else if (edgeDrawable != null && edgeDrawable.isTwoDirectional()) {
                edgeDrawable.deleteOneWay(v, vertex);
                vertex.deleteNeighbour(v);
            }
        }
        vertexesDrawable.remove(vertexDrawable);
        graph.deleteVertex(vertex);
    }
}