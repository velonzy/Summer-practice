package application.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Tests {
    public static void RunTests() throws InterruptedException {
        //FirstTestMinF(atest) + "\n" +
        Thread.sleep(1000);
        System.out.println(FirstTestH());
        Thread.sleep(1000);
        System.out.println(SecondTestH());
        Thread.sleep(1000);
        System.out.println(ThirdTestH());
        Thread.sleep(1000);
        AStar atest = new AStar();
        System.out.println(FirstTestAStar(atest));
        Thread.sleep(1000);
        System.out.println(FirstTestPath(atest));
        Thread.sleep(1000);
        System.out.println(FirstTestWeight(atest));
        Thread.sleep(1000);
        atest = new AStar();
        System.out.println(SecondTestAStar(atest));
        Thread.sleep(1000);
        System.out.println(SecondTestPath(atest));
        Thread.sleep(1000);
        System.out.println(SecondTestWeight(atest));
    }

    private static String FirstTestH() {
        if (AStar.h(new Vertex('a', 0, 0), new Vertex('b', 0, 0)) == 0.0)
            return "First test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "First test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String SecondTestH() {
        if (Math.abs(AStar.h(new Vertex('a', 100.234, 2), new Vertex('b', 189.2, 0)) - 88.98847765862723) < 0.0000000001)
            return "Second test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Second test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String ThirdTestH() {
        if (Math.abs(AStar.h(new Vertex('a', 100, 300), (new Vertex('b', 100 + Math.sqrt(600), 305))) - 25) < 0.0000000001)
            return "Third test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Third test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String FirstTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("2\na 0 0\nb 0 0\na b 0");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('b'), graph.getVertexes());
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('b'));
        if (test.equals(array)) return "First test: AStar." + "\tAnswer is correst. Test: OK";
        else return "First test: AStar." + "\tAnswer is incorrest. Test failed";
    }

    private static String FirstTestPath(AStar n) {
        if (n.getPath().equals("ab")) return "First test: Path." + "\tAnswer is correst. Test: OK";
        else return "First test: Path." + "\tAnswer is incorrest. Test failed";
    }

    private static String FirstTestWeight(AStar n) {
        if (Math.abs(n.getWeight() - 0) < 0.0000000001) return "First test: Weight." + "\tAnswer is correst. Test: OK";
        else return "First test: Weight." + "\tAnswer is incorrest. Test failed";
    }



    private static String SecondTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("5\na 100.234 2\nb 189.2 0\nc 200 300\n" +
                "d 100 300\ne 500 500\na b 3\nb c 1\nc d 1\na d 5\nd e 1");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('e'), graph.getVertexes());
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('d'));
        array.add(graph.findVertex('e'));
        if (test.equals(array)) return "Second test: AStar." + "\tAnswer is correst. Test: OK";
        else return "Second test: AStar." + "\tAnswer is incorrest. Test failed";
    }
    private static String SecondTestPath(AStar n) {
        if (n.getPath().equals("ade")) return "Second test: Path." + "\tAnswer is correst. Test: OK";
        else return "Second test: Path." + "\tAnswer is incorrest. Test failed";
    }
    private static String SecondTestWeight(AStar n) {
        if (Math.abs(n.getWeight() - 6) < 0.0000000001) return "Second test: Weight." + "\tAnswer is correst. Test: OK";
        else return "Second test: Weight." + "\tAnswer is incorrest. Test failed";
    }
}