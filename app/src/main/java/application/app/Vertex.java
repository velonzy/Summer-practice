package application.app;

import java.util.HashMap;

public class Vertex {
    private char name;

    private Coordinates<Double> coordinates;

    private HashMap<Vertex, Double> neighbours;

    public Vertex(char name, double x, double y) {
        this.name = name;
        coordinates = new Coordinates<>(x, y);
        neighbours = new HashMap<Vertex, Double>();
    }

    public void addNeighbour(Vertex vertex, double distance){
        neighbours.put(vertex, distance);
    }

    public void deleteNeighbour(Vertex vertex){
        neighbours.remove(vertex);
    }

    public char getName(){
        return name;
    }

    public void setName(char name){
        this.name = name;
    }

    public HashMap<Vertex, Double> getNeighbours(){
        return neighbours;
    }

    public Coordinates<Double> getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(Double x, Double y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }
}
