package application.app;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

// files
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphController { //для считывания графа
    private ArrayList<VertexDrawable> vertexesDrawable;
    private Graph graph;
    private ArrayList<EdgeDrawable> edgesDrawable;
    private AStar aStar;

    private Vertex chosen1, chosen2;

    public GraphController(){
        vertexesDrawable = new ArrayList<VertexDrawable>();
        graph = new Graph();
        edgesDrawable = new ArrayList<EdgeDrawable>();
        eventFlag = true;
    }

    public ArrayList<VertexDrawable> getVertexesDrawable(){
        return vertexesDrawable;
    }

    public Graph getGraph(){
        return graph;
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

    public void normalGraphColor() {
        for (VertexDrawable v : vertexesDrawable){
            v.getView().setFill(Paint.valueOf("#d5b8e6"));
        }
        for (EdgeDrawable e : edgesDrawable) {
            e.getView().setStroke(Paint.valueOf("#806b8d"));
        }
    }

    public void runningAlgorithmAStar(Vertex first, Vertex second) {
        normalGraphColor();
        ArrayList<Vertex> solution;
        aStar = new AStar();
        if (chosen1 != null && chosen2 != null) {
            solution = aStar.a_star_public(chosen1, chosen2);
<<<<<<< HEAD
=======
            VertexDrawable vertex1 = findVertex(chosen1);
            VertexDrawable vertex2 = findVertex(chosen2);
            if(vertex1 != null){
                vertex1.getView().setEffect(null);
            }
            if (vertex2 != null) {
                vertex2.getView().setEffect(null);
            }
>>>>>>> 276e60b332ea0c9789b6f472f07fef039ae020dc
            chosen1 = null;
            chosen2 = null;
        } else {
            return;
        }
        for (int i = 0; i < solution.size() - 1; i++) {
            VertexDrawable cur = findVertex(solution.get(i));
            VertexDrawable next = findVertex(solution.get(i + 1));
            if (cur != null && next != null) {
                cur.getView().setFill(Paint.valueOf("#0cda73"));
                next.getView().setFill(Paint.valueOf("#0cda73"));
            }
            EdgeDrawable edge = findEdge(solution.get(i), solution.get(i + 1));
            if (edge != null){
                edge.getView().setStroke(Paint.valueOf("#0a9a50"));
            }
        }
    }

    private int stepNumber;

    private boolean eventFlag;

    private ArrayList<Text> heuristics;

    public void startStepByStep() {
        eventFlag = false;
        aStar = new AStar();
        if (chosen1 != null && chosen2 != null) {
            aStar.a_star_public(chosen1, chosen2);
            chosen1 = null;
            chosen2 = null;
            stepNumber = 0;
            ArrayList<Vertex> result = aStar.getPaths().get(stepNumber);
            HashMap<Vertex, Double> fResults = aStar.getF_steps().get(stepNumber);
            VertexDrawable cur = findVertex(result.get(0));

            normalGraphColor();

            if (cur != null) {
                heuristics = new ArrayList<>();
                heuristics.add(new Text(cur.getVertex().getCoordinates().getX(),
                        cur.getVertex().getCoordinates().getY() - 30,
                        "f:" + Math.round(fResults.get(result.get(0)))));

                cur.getView().setFill(Paint.valueOf("#0cda73"));
            }
        }
    }

    public void drawGraphAndLabels(Pane pane) {
        drawGraph(pane);
        if (heuristics != null) {
            for (Text text: heuristics) {
                pane.getChildren().add(text);
            }
        }
    }

    public void toEndStepByStep() {
        int stepSize = aStar.getPaths().size() - 1 - stepNumber;
        doStep(stepSize);
    }

    public void allowEvents() {
        eventFlag = true;
    }

    public void endSBS() {
        heuristics = null;
    }

    public void doStep(int step) {
        if (stepNumber == 0 && step <= -1 || stepNumber == aStar.getPaths().size() - 1 && step >= 1) {
            return;
        } else {
            stepNumber += step;
        }

        if (stepNumber >= 0 && stepNumber < aStar.getPaths().size()) {
            ArrayList<Vertex> result = aStar.getPaths().get(stepNumber);
            HashMap<Vertex, Double> fResults = aStar.getF_steps().get(stepNumber);
            heuristics = new ArrayList<>();

            normalGraphColor();

            if (stepNumber == 0) {
                VertexDrawable cur = findVertex(result.get(0));
                if (cur != null) {
                    cur.getView().setFill(Paint.valueOf("#0cda73"));
                    heuristics.add(new Text(cur.getVertex().getCoordinates().getX(),
                            cur.getVertex().getCoordinates().getY() - 30,
                            "f:" + Math.round(fResults.get(result.get(0)))));
                }
                return;
            }

            for (int i = 0; i < result.size() - 1; i++) {
                VertexDrawable cur = findVertex(result.get(i));
                VertexDrawable next = findVertex(result.get(i + 1));

                if (cur != null && next != null) {
                    cur.getView().setFill(Paint.valueOf("#0cda73"));
                    next.getView().setFill(Paint.valueOf("#0cda73"));
                    heuristics.add(new Text(cur.getVertex().getCoordinates().getX(),
                            cur.getVertex().getCoordinates().getY() - 30,
                            "f:" + Math.round(fResults.get(result.get(i)))));
                    if (i == result.size() - 2) {
                        heuristics.add(new Text(next.getVertex().getCoordinates().getX(),
                                next.getVertex().getCoordinates().getY() - 30,
                                "f:" + Math.round(fResults.get(result.get(i + 1)))));
                    }
                }
                EdgeDrawable edge = findEdge(result.get(i), result.get(i + 1));
                if (edge != null){
                    edge.getView().setStroke(Paint.valueOf("#0a9a50"));
                }
            }
        }
    }

    public boolean isFinalInStepByStep() {
        return (stepNumber == aStar.getPaths().size() - 1);
    }

    public String getPath(){
        if(aStar != null){
            return aStar.getPath();
        }
        return null;
    }

    public double getWeight(){
        if(aStar != null){
            return aStar.getWeight();
        }
        return -1;
    }

    public static Graph readGraphForTests(String string){
        Graph graphTest = new Graph();

        String[] tokens = string.split("[\n ]");
        int numberOfVertexes = Integer.parseInt(tokens[0]);
        for (int i = 1; i < numberOfVertexes * 3; i += 3){
            Vertex vertex = new Vertex(tokens[i].charAt(0), Double.parseDouble(tokens[i + 1]), Double.parseDouble(tokens[i + 2]));
            graphTest.addVertex(vertex);
        }
        for (int i = numberOfVertexes * 3 + 1; i < tokens.length; i+= 3){
            char start, finish;
            double weight;
            start = tokens[i].charAt(0);
            finish = tokens[i + 1].charAt(0);
            weight = Double.parseDouble(tokens[i + 2]);
            Vertex sVertex, fVertex;
            sVertex = graphTest.findVertex(start);
            fVertex = graphTest.findVertex(finish); // добавить исключение, когда одно из них не найдено
            graphTest.addEdge(sVertex, fVertex, weight);
        }
        return graphTest;
    }
    //в readGraphFromWindow, readGraphFromFile добавить исключения, если вводимые данные пустые, плюс для
    // файлов исключение на то, что файл не существует
    public void readGraph(String string) throws NumberFormatException, DataFormatException{
        if (string.isEmpty()) CheckRules.inputIsEmpty();
        vertexesDrawable = new ArrayList<VertexDrawable>();
        edgesDrawable = new ArrayList<EdgeDrawable>();
        if (graph != null) {
            graph.clearGraph();
        } else {
            graph = new Graph();
        }
        String[] tokens = string.split("[\\r\\n ]+");
        int numberOfVertexes = Integer.parseInt(tokens[0]);
        CheckRules.checkNumberOfVertexes(numberOfVertexes);
        for (int i = 1; i < numberOfVertexes * 3; i += 3){
            CheckRules.checkVertexName(tokens[i]);
            CheckRules.checkIfVertexNameIsAvailable(graph, tokens[i]);
            CheckRules.checkCoordinates(tokens[i + 1], tokens[i + 2]);
            Vertex vertex = new Vertex(tokens[i].charAt(0), Double.parseDouble(tokens[i + 1]), Double.parseDouble(tokens[i + 2]));
            graph.addVertex(vertex);
            vertexesDrawable.add(new VertexDrawable(vertex));
        }
        for (int i = numberOfVertexes * 3 + 1; i < tokens.length; i+= 3){
            CheckRules.checkEdge(graph, tokens[i], tokens[i + 1], tokens[i + 2]);
            char start = tokens[i].charAt(0);
            char finish = tokens[i + 1].charAt(0);
            double weight = Double.parseDouble(tokens[i + 2]);
            Vertex startVertex = graph.findVertex(start);
            Vertex finishVertex = graph.findVertex(finish);

            if(findEdge(startVertex, finishVertex) != null){
                //CheckRules.edgeAlreadyExists(tokens[i], tokens[i + 1], tokens[i + 2]);
            }
            graph.addEdge(startVertex, finishVertex, weight);
            boolean flag = false;
            for (EdgeDrawable e: edgesDrawable) {
                if (e.isReverse(startVertex, finishVertex)) {
                    e.setReversedDirection(weight);
                    flag = true;
                }
            }
            if (!flag) {
                edgesDrawable.add(new EdgeDrawable(startVertex, finishVertex, weight));
            }
        }
        setEventHandlers();
    }

    public void readGraphFromFile(String fileName) throws IOException, DataFormatException {
        String data = new String(Files.readAllBytes(Paths.get(fileName)));
        readGraph(data);
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
                    if (eventFlag) {
                        x[0] = v.getView().getLayoutX() - mouseEvent.getSceneX();
                        y[0] = v.getView().getLayoutY() - mouseEvent.getSceneY();
                    }
                }
            });
            v.getView().setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (eventFlag) {
                        double newX, newY;
                        newX = mouseEvent.getSceneX() + x[0];
                        newY = mouseEvent.getSceneY() + y[0];
                        if (newX <= 600 && newY <= 530 && newX >= 0 && newY >= 0){
                            v.getVertex().setCoordinates(newX, newY);
                        }
                        v.moveCircle();
                        for(EdgeDrawable e: edgesDrawable) {
                            e.moveLine();
                        }
                    }
                }
            });
            v.getView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (eventFlag) {
                        if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                            if (chosen1 == v.getVertex()) {
                                VertexDrawable vertex1 = findVertex(chosen1);
                                if(vertex1 != null){
                                    vertex1.getView().setFill(Paint.valueOf("#d5b8e6"));
                                }
                                chosen1 = null;
                                return;
                            } else if (chosen2 == v.getVertex()) {
                                VertexDrawable vertex2 = findVertex(chosen2);
                                if(vertex2 != null){
                                    vertex2.getView().setFill(Paint.valueOf("#d5b8e6"));
                                }
                                chosen2 = null;
                                return;
                            }
                            if(chosen1 == null) {
                                chosen1 = v.getVertex();
                                v.getView().setFill(Paint.valueOf("#5e64d7"));
                            } else if (chosen2 == null) {
                                v.getView().setFill(Paint.valueOf("#a34acd"));
                                chosen2 = v.getVertex();
                            } else {
                                VertexDrawable vertex1 = findVertex(chosen1);
                                VertexDrawable vertex2 = findVertex(chosen2);
                                if(vertex1 != null){
                                    vertex1.getView().setFill(Paint.valueOf("#d5b8e6"));
                                }
                                if (vertex2 != null) {
                                    vertex2.getView().setFill(Paint.valueOf("#d5b8e6"));
                                }
                                chosen1 = v.getVertex();
                                v.getView().setFill(Paint.valueOf("#5e64d7"));
                                chosen2 = null;
                            }
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
            if(edgeDrawable != null) {
                edgesDrawable.remove(edgeDrawable);
                vertex.deleteNeighbour(v);
            }
        }
        vertexesDrawable.remove(vertexDrawable);
        graph.deleteVertex(vertex);
    }

    public void renewEdgesNames() {
        for (EdgeDrawable e : edgesDrawable) {
            e.renewNames();
        }
    }
}
