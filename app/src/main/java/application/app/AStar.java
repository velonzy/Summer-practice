package application.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AStar {
    int[] closed; //Список пройдённых вершин
    int[] open; // Очередь вершин
    HashMap<Vertex, Double> f = new HashMap<>();
    HashMap<Vertex, Double> g = new HashMap<>();
    HashMap<Vertex, Boolean> in_open = new HashMap<>();
    HashMap<Vertex, Boolean> in_closed = new HashMap<>();
    HashMap<Vertex, Vertex> from = new HashMap<>();

    private double h(Vertex a, Vertex b){ // Эвристическая функция
        return Math.abs( Math.pow(a.getCoordinates().getX() - b.getCoordinates().getX(), 2) +
                Math.pow(a.getCoordinates().getY() - b.getCoordinates().getY(), 2));
    }

    private int find(char lttr, ArrayList<Vertex> graph){
        for (int i = 0; i < graph.size(); i++){
            if (graph.get(i).getName() == lttr)
                return i;
        }
        return -1;
    }

    private void delete_open( int index){
        for (int i = 0; i < open.length; i++){
            if (open[i] == index)
            {
                int[] result = new int[open.length - 1];
                System.arraycopy(open, 0, result, 0, i);
                System.arraycopy(open, i + 1, result, i, open.length - i - 1);
                open = result;
            }
        }
    }
    private int min_f(ArrayList<Vertex> graph){
        if (open.length <= 0) return -1;
        Double min = f.get(graph.get(open[0]));
        int index_min = open[0];
        for(int i = 0; i < open.length; i++){
            if (f.get(graph.get(open[i])) < min ||
                    ( f.get(graph.get(open[i])).equals(min) &&
                            (int)(graph.get(open[i]).getName()) <
                                    (int)(graph.get(open[i]).getName()))) {
                min = f.get(graph.get(open[i]));
                index_min = open[i];
            }
        }
        return index_min;
    }

    private int a_star(Vertex start, Vertex finish, ArrayList<Vertex> graph){
        int current_ind;
        Vertex current;
        open = new int[1];
        closed = new int[0];
        open[0] = find(start.getName(), graph);
        in_open.put(start, true);
        g.put(start, (double)0); f.put(start, g.get(start) + h(start, finish));
        while (open.length > 0){
            current_ind = min_f(graph);
            current = graph.get(current_ind);
            if (current == finish) return current_ind;
            delete_open(current_ind);
            in_open.put(current, false);
            int[] destArray2 = Arrays.copyOf(closed, closed.length + 1);
            destArray2[closed.length] = current_ind;
            closed = destArray2;
            in_closed.put(current, true);
            for (HashMap.Entry<Vertex, Double> neighbour : current.getNeighbours().entrySet()) {
                int neigh_index = find(neighbour.getKey().getName(), graph);
                double temp_g = g.get(current) + neighbour.getValue();
                if (( !in_open.containsKey(neighbour.getKey()) || in_open.containsKey(neighbour.getKey()) && !in_open.get(neighbour.getKey()))
                        && ( !in_closed.containsKey(neighbour.getKey()) || in_closed.containsKey(neighbour.getKey())&& !in_closed.get(neighbour.getKey()))
                        || (g.containsKey(neighbour.getKey()) && temp_g < g.get(neighbour.getKey()))){
                    from.put(neighbour.getKey(), current);
                    g.put(neighbour.getKey(), temp_g);
                    f.put(neighbour.getKey(), g.get(neighbour.getKey()) + h(neighbour.getKey(), finish));
                }
                if (( !in_open.containsKey(neighbour.getKey()) || in_open.containsKey(neighbour.getKey()) && !in_open.get(neighbour.getKey()))
                        && ( !in_closed.containsKey(neighbour.getKey()) || in_closed.containsKey(neighbour.getKey())&& !in_closed.get(neighbour.getKey()))){
                    in_open.put(neighbour.getKey(), true);
                    int[] destArray3 = Arrays.copyOf(open, open.length + 1);
                    open = destArray3;
                    open[open.length - 1] = neigh_index;
                }
            }
        }
        return -1;
    }

    public ArrayList<Vertex> a_star_public(Vertex start, Vertex finish, ArrayList<Vertex> graph){
        ArrayList<Vertex> solution = new ArrayList<Vertex>();
        int goal = a_star(start, finish, graph);
        if (goal < 0) return solution;
        Vertex current = graph.get(goal);
        solution.add(current);
        while(from.containsKey(current)){
            solution.add(0, from.get(current));
            current = from.get(current);
        }
        return solution;
    }

}