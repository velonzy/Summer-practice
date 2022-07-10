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

    public void deleteAvailableName(char name){
        availableNames.put(name, false);
    }

    public void addAvailableName(char name){
        availableNames.put(name, true);
    }

    void addVertex(char vertex, double x, double y){
        vertexes.add(new Vertex(vertex, x, y));
        availableNames.put(vertex, false);
    }

    void addVertex(Vertex vertex){
        vertexes.add(vertex);
        availableNames.put(vertex.getName(), false);
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
                availableNames.put(vertex, true);
                vertexes.remove(v);
                return;
            }
        }
    }

    void deleteVertex(Vertex vertex){
        for(Vertex subV : vertexes){
            subV.getNeighbours().remove(vertex);
        }
        availableNames.put(vertex.getName(), true);
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

    public boolean isNameAvailable(char name){
        return availableNames.get(name);
    }

    void clearGraph(){
        vertexes.clear();
    }
}