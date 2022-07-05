package application.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AStar {
    HashMap<Vertex, Double> f;
    HashMap<Vertex, Double> g;
    ArrayList<Vertex> in_open;
    ArrayList<Vertex> in_closed;
    HashMap<Vertex, Vertex> from;
    List<ArrayList<Vertex>> open;
    List<ArrayList<Vertex>> closed;

    public AStar(){
        f = new HashMap<Vertex, Double>();
        g = new HashMap<Vertex, Double>();
        in_open = new ArrayList<Vertex>();
        in_closed = new ArrayList<Vertex>();
        from = new HashMap<Vertex, Vertex>();
        open = new ArrayList();
        closed = new ArrayList();
    }
    private double h(Vertex a, Vertex b){ // Р­РІСЂРёСЃС‚РёС‡РµСЃРєР°СЏ С„СѓРЅРєС†РёСЏ
        return Math.abs( Math.pow(a.getCoordinates().getX() - b.getCoordinates().getX(), 2) +
                Math.pow(a.getCoordinates().getY() - b.getCoordinates().getY(), 2));
    }

    private Vertex min_f(ArrayList<Vertex> graph){
        Double min = f.get(in_open.get(0));
        Vertex min_v = in_open.get(0);
        for(int i = 0; i < in_open.size(); i++){
            if (f.get(in_open.get(i)) < min) {
                min = f.get(in_open.get(i));
                min_v = in_open.get(i);
            }
        }
        return min_v;
    }

    private Vertex a_star(Vertex start, Vertex finish, ArrayList<Vertex> graph){
        Vertex current;
        in_open.add(start);
        g.put(start, (double)0); f.put(start, g.get(start) + h(start, finish));
        while (in_open.size() > 0){
            current = min_f(graph);
            if (current == finish) return finish;
            in_open.remove(current);
            in_closed.add(current);
            for (HashMap.Entry<Vertex, Double> neighbour : current.getNeighbours().entrySet()) {
                double temp_g = g.get(current) + neighbour.getValue();
                if ( !in_open.contains(neighbour.getKey())
                        &&  !in_closed.contains(neighbour.getKey())
                        || (g.containsKey(neighbour.getKey()) && temp_g < g.get(neighbour.getKey()))){
                    from.put(neighbour.getKey(), current);
                    g.put(neighbour.getKey(), temp_g);
                    f.put(neighbour.getKey(), g.get(neighbour.getKey()) + h(neighbour.getKey(), finish));
                }
                if (!in_open.contains(neighbour.getKey())
                        &&  !in_closed.contains(neighbour.getKey())){
                    in_open.add(neighbour.getKey());
                }
            }
            ArrayList <Vertex> arr_open = new ArrayList<>(in_open);
            open.add(arr_open);
            ArrayList <Vertex> arr_closed = new ArrayList<>(in_closed);
            closed.add(arr_closed);
        }
        return null;
    }

    public ArrayList<Vertex> a_star_public(Vertex start, Vertex finish, ArrayList<Vertex> graph){
        ArrayList<Vertex> solution = new ArrayList<Vertex>();
        Vertex goal = a_star(start, finish, graph);
        if (goal == null) return solution;
        solution.add(goal);
        while(from.containsKey(goal)){
            solution.add(0, from.get(goal));
            goal = from.get(goal);
        }
        return solution;
    }

    public List<ArrayList<Vertex>> getOpen(){
        return open;
    }
    public List<ArrayList<Vertex>> getClose(){
        return closed;
    }
}