package IdeaProjects.untitled;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    static ArrayList<Edge> graph = new ArrayList<Edge>();//Список вершин
    static int[] closed; //Список пройдённых вершин
    static int[] open; // Очередь вершин
    static int start, finish;
    static char char_start, char_finish;

    public static float h(char a, char b){ // Эвристическая функция
        return Math.abs((int)(a) - (int)(b));
    }

    public static int find( char lttr){
        for (int i = 0; i < graph.size(); i++){
            if (graph.get(i).letter == lttr)
                return i;
        }
        return -1;
    }

    public static void delete_open( int index){
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
    public static int min_f(){
        if (open.length <= 0) return -1;
        float min = graph.get(open[0]).f;
        int index_min = open[0];
        for(int i = 0; i < open.length; i++){
            if (graph.get(open[i]).f < min ||
                    ( graph.get(open[i]).f == min &&
                            (int)(graph.get(open[i]).letter) <
                    (int)(graph.get(open[i]).letter))) {
                min = graph.get(open[i]).f;
                index_min = open[i];
            }
        }
        return index_min;
    }
    public static int a_star(){
        int current;
        char neighbour_char;
        float neighbour_float;
        open = new int[1];
        closed = new int[0];
        open[0] = start;
        graph.get(start).open = true;
        graph.get(start).g = 0;  graph.get(start).f =  graph.get(start).g
                + h(char_start, char_finish);
        while (open.length > 0){
            current = min_f();
            if (current == finish) return current;
            delete_open(current);
            graph.get(current).open = false;
            int[] destArray2 = Arrays.copyOf(closed, closed.length + 1);
            destArray2[closed.length] = current;
            closed = destArray2;
            graph.get(current).closed = true;
            int count_neigh = 0;
            while (graph.get(current).neighbour_char.length > count_neigh) {
                int neigh_index = find(graph.get(current).neighbour_char[count_neigh]);
                float temp_g = graph.get(current).g + graph.get(current).neighbour_distance[count_neigh];
                if (!graph.get(neigh_index).open  && !graph.get(neigh_index).closed
                        || temp_g < graph.get(neigh_index).g){
                    graph.get(neigh_index).from = current;
                    graph.get(neigh_index).g = temp_g;
                    graph.get(neigh_index).f = graph.get(neigh_index).g +
                            h(graph.get(neigh_index).letter, char_finish);
                }
                if (!graph.get(neigh_index).open && !graph.get(neigh_index).closed) {
                    graph.get(neigh_index).open = true;
                    int[] destArray3 = Arrays.copyOf(open, open.length + 1);
                    open = destArray3;
                    open[open.length - 1] = neigh_index;
                }
                count_neigh++;
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        ArrayList<Edge> result = new ArrayList<Edge>();//Список вершин
        char_start = args[0].charAt(0);
        char_finish =  args[1].charAt(0);
        for (int i = 2; i < args.length; i = i + 3){
            Edge node = new Edge(args[i].charAt(0));
            char node2 = args[i+1].charAt(0);
            float distance = Float.parseFloat(args[i+2]);
            int findletter = find(node.letter);
            if (findletter >= 0) {
                graph.get(findletter).setNeighbours(node2,distance);
            }
            else {
                node.setNeighbours(node2,distance);
                graph.add(node);
            }
            int findletter2 = find(node2);
            if (findletter2 < 0) {
                Edge new_node = new Edge(node2);
                graph.add(new_node);
            }

        }
        start = find(char_start);
        finish = find(char_finish);
        for (Edge nodd : graph)
            nodd.print();
        int goal = a_star();
        if ( goal < 0 ) return;

        result.add(graph.get(goal));
        while(graph.get(goal).from >= 0){
            goal = graph.get(goal).from;
            result.add(graph.get(goal));
        }
        for (int loop = result.size() - 1; loop >= 0; loop--)
            System.out.print(result.get(loop).letter);
    }

    private static class Edge{
        char letter;
        char[] neighbour_char;
        float[] neighbour_distance;
        float g = 0;
        float f = 0;
        boolean open;
        boolean closed;
        int from = -1;
        Edge(char name){
            this.letter = name;
            neighbour_char = new char[0];
            neighbour_distance = new float[0];
        }

        void setNeighbours(char neigh, float dist){
            char[] destArray = Arrays.copyOf(neighbour_char, neighbour_char.length + 1);
            neighbour_char = destArray;
            neighbour_char[neighbour_char.length - 1] = neigh;
            float[] destArray2 = Arrays.copyOf(neighbour_distance, neighbour_distance.length + 1);
            neighbour_distance = destArray2;
            neighbour_distance[neighbour_distance.length - 1] = dist;

        }
        void print(){
            System.out.println(letter);
            for (int i = 0; i < neighbour_char.length; i++)
            {
                System.out.print("{ "+ neighbour_char[i] + " - " + neighbour_distance[i] + " }\n");
            }
        }
    }
}
