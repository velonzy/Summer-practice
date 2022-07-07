package application.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Tests {
    public static String RunTests() {
        AStar atest = new AStar();
        return FirstTestH() + "\n" +
                SecondTestH() + "\n" +
                ThirdTestH() + "\n" +
                //FirstTestMinF(atest) + "\n" +
                FirstTestAStar(atest) + "\n" +
                FirstTestPath(atest) + "\n" +
                FirstTestWeight(atest) +"\n";
    }

    private static String FirstTestH() {
        System.out.println();
        if (AStar.h(new Vertex('a', 0, 0), new Vertex('b', 0, 0)) == 0.0)
            return "First test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "First test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String SecondTestH() {
        System.out.println();
        if (Math.abs(AStar.h(new Vertex('a', 100.234, 2), new Vertex('b', 189.2, 0)) - 88.98847765862723) < 0.0000000001)
            return "Second test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Second test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String ThirdTestH() {
        System.out.println();
        if (Math.abs(AStar.h(new Vertex('a', 100, 300), (new Vertex('b', 100 + Math.sqrt(600), 305))) - 25) < 0.0000000001)
            return "Third test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Third test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    /*    private static String FirstTestMinF(AStar n) {
        System.out.println();
        Vertex a = new Vertex('a', 0, 0);
        Vertex b = new Vertex('b', 0, 0);
        ArrayList<Vertex> open = new ArrayList<>();
        open.add(a);
        open.add(b);
        HashMap<Vertex, Double> ab = new HashMap<>();
        ab.put(a, 105.0);
        ab.put(b, 205.0);
        n.SetF(ab);
        n.SetInOpen(open);
        if (n.min_f() == a) {
            n.SetF(new HashMap<>());
            return "First test: MinF." + "\tAnswer is correst. Test: OK";
        } else {
            n.SetF(new HashMap<>());
            return "First test: MinF." + "\tAnswer is incorrest. Test failed";
        }
    }
*/
    private static String FirstTestAStar(AStar n) {
        System.out.println();
        Graph graph = GraphController.readGraphForTests("2\na 0 0\nb 0 0\na b 0");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('b'), graph.getVertexes());
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('b'));
        if (test.equals(array)) return "First test: AStar." + "\tAnswer is correst. Test: OK";
        else return "First test: AStar." + "\tAnswer is incorrest. Test failed";
    }

    private static String FirstTestPath(AStar n) {
        System.out.println();
        if (n.getPath().equals("ab")) return "First test: Path." + "\tAnswer is correst. Test: OK";
        else return "First test: Path." + "\tAnswer is incorrest. Test failed";
    }

    private static String FirstTestWeight(AStar n) {
        System.out.println();
        if (Math.abs(n.getWeight() - 0) < 0.0000000001) return "First test: Weight." + "\tAnswer is correst. Test: OK";
        else return "First test: Weight." + "\tAnswer is incorrest. Test failed";
    }

//    private static String FirstTestOpen() {
//        System.out.println();
//        ArrayList<String> strings = new ArrayList<>();
//        ArrayList<String> test_strings = new ArrayList<>();
//    }
}