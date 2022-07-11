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

    public Character getAvailableName() {
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

    public void addVertex(char vertex, double x, double y){
        vertexes.add(new Vertex(vertex, x, y));
        availableNames.put(vertex, false);
    }

    public void addVertex(Vertex vertex){
        vertexes.add(vertex);
        availableNames.put(vertex.getName(), false);
    }

    public void addEdge(Vertex first, Vertex second, double weight){
        first.addNeighbour(second, weight);
    }

    public void deleteVertex(char vertex){
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

    public void deleteVertex(Vertex vertex){
        for(Vertex subV : vertexes){
            subV.getNeighbours().remove(vertex);
        }
        availableNames.put(vertex.getName(), true);
        vertexes.remove(vertex);
    }

    public void deleteEdge(char first, char second){
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

    public void deleteEdge(Vertex first, Vertex second){
        if (first != null && second != null){
            first.deleteNeighbour(second);
        }
    }

    public Vertex findVertex(char name) {
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

    public boolean isEdgeAlreadyExists(Vertex first, Vertex second){
        return first.getNeighbours().containsKey(second);
    }

    public void clearGraph(){
        vertexes.clear();
    }
}