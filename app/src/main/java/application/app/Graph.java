package application.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<Vertex> vertexes;
    private HashMap<Character, Boolean> availableNames;

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public Graph(){
        vertexes = new ArrayList<Vertex>();
        availableNames = new HashMap<Character, Boolean>();
        for(char letter = 'a'; letter <= 'z'; letter++){
            availableNames.put(letter, true);
        }
    }

    void addVertex(char vertex, double x, double y){
        availableNames.put(vertex, false);
        vertexes.add(new Vertex(vertex, x, y));
    }

    void addVertex(Vertex vertex){
        availableNames.put(vertex.getName(), false);
        vertexes.add(vertex);
    }

    void addEdge(Vertex first, Vertex second, double weight){
        first.addNeighbour(second, weight);
    }

    void deleteVertex(char vertex){
        availableNames.put(vertex, true);
        for(Vertex v : vertexes){
            if (v.getName() == vertex){
                for(Vertex subV : vertexes){
                    subV.getNeighbours().remove(v);
                }
                vertexes.remove(v);
                return;
            }
        }
    }

    void deleteVertex(Vertex vertex){
        availableNames.put(vertex.getName(), true);
        for(Vertex subV : vertexes){
            subV.getNeighbours().remove(vertex);
        }
        vertexes.remove(vertex);
    }

    void deleteEdge(char first, char second){ //добавить проверку на наличие вершин; first != second
        Vertex firstVertex = null, secondVertex = null;
        for(Vertex v : vertexes){
            if (v.getName() == first){
                firstVertex = v;
            }
            if (v.getName() == second){
                secondVertex = v;
            }
        }
        if (firstVertex != null && secondVertex != null){
            firstVertex.deleteNeighbour(secondVertex);
        }
    }

    void deleteEdge(Vertex first, Vertex second){ //добавить проверку на наличие вершин; first != second
        if (first != null && second != null){
            first.deleteNeighbour(second);
        }
    }

    Vertex findVertex(char name) {
        for (Vertex v : vertexes) {
            if (v.getName() == name) {
                return v;
            }
        }
        return null;
    }

    public Character getAvailableName(){
        for(char letter = 'a'; letter <= 'z'; letter++){
            if (availableNames.get(letter).equals(true)) {
                return letter;
            }
        }
        return '*';
    }

    void clearGraph(){
        vertexes.clear();
    }
}