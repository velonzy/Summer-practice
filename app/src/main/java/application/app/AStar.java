package application.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AStar {
    private HashMap<Vertex, Double> f;
    private HashMap<Vertex, Double> g;
    private ArrayList<Vertex> in_open;
    private ArrayList<Vertex> in_closed;
    private HashMap<Vertex, Vertex> from;
    private ArrayList<HashMap<Vertex, Double>> f_steps;
    private List<ArrayList<Vertex>> paths;
    private ArrayList<Vertex> solution;
    private String path = "";
    public AStar(){
        f = new HashMap<Vertex, Double>();
        g = new HashMap<Vertex, Double>();
        in_open = new ArrayList<Vertex>();
        in_closed = new ArrayList<Vertex>();
        from = new HashMap<Vertex, Vertex>();
        f_steps = new ArrayList();
        paths = new ArrayList();
        solution = new ArrayList<Vertex>();
    }
    public static double h(Vertex a, Vertex b){ // Р­РІСЂРёСЃС‚РёС‡РµСЃРєР°СЏ С„СѓРЅРєС†РёСЏ
        return Math.sqrt( Math.pow(a.getCoordinates().getX() - b.getCoordinates().getX(), 2) +
                Math.pow(a.getCoordinates().getY() - b.getCoordinates().getY(), 2));
    }

    public Vertex min_f(){
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
            current = min_f();
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
            HashMap<Vertex,Double> f_step = new HashMap<>(f);
            f_steps.add(f_step);
            paths.add(getStepPath(current));
        }
        return null;
    }

    public ArrayList<Vertex> a_star_public(Vertex start, Vertex finish, ArrayList<Vertex> graph){
        Vertex goal = a_star(start, finish, graph);
        if (goal == null) return solution;
        solution.add(goal);
        while(from.containsKey(goal)){
            solution.add(0, from.get(goal));
            goal = from.get(goal);
        }
        return solution;
    }

    public String getPath(){
        for (Vertex i : solution)
            path += i.getName();
        return path;
    }
    public ArrayList<Vertex> getStepPath(Vertex n){
        ArrayList<Vertex> step_path = new ArrayList<>();
        while(from.containsKey(n)){
            step_path.add(0, from.get(n));
            n = from.get(n);
        }
        return step_path;
    }

    public double getWeight(){
        double weight = 0;
        for (int i = 0; i < solution.size() - 1; i++)
            weight += solution.get(i).getNeighbours().get(solution.get(i + 1));
        return weight;
    }

    public ArrayList<HashMap<Vertex, Double>> getF_steps(){
        return f_steps;}
    public List<ArrayList<Vertex>> getPaths(){
        return paths;
    }
}