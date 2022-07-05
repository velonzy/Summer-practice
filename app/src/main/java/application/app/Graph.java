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

    public Character getAvailableName(){
        for(char letter = 'a'; letter <= 'z'; letter++){
            if (availableNames.get(letter).equals(true)) {
                return letter;
            }
        }
        return '*';
    }

    void addVertex(char vertex, double x, double y){
        vertexes.add(new Vertex(vertex, x, y));
    }

    void addVertex(Vertex vertex){
        vertexes.add(vertex);
    }

    void addEdge(Vertex first, Vertex second, double weight){
        first.addNeighbour(second, weight);
    }

    void deleteVertex(char vertex){
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

    void clearGraph(){
        vertexes.clear();
    }
}