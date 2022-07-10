package application.app;

import java.util.ArrayList;
import java.util.HashMap;

public class Tests {
    public static final double DOUBLE_CONST= 0.0000000001;
    public static void RunTests() throws InterruptedException {
        System.out.println(FirstTestH());
        Thread.sleep(500);
        System.out.println(SecondTestH());
        Thread.sleep(500);
        System.out.println(ThirdTestH());
        Thread.sleep(500);
        AStar atest = new AStar();
        System.out.println(FirstTestAStar(atest));
        Thread.sleep(500);
        System.out.println(FirstTestPath(atest));
        Thread.sleep(500);
        System.out.println(FirstTestWeight(atest));
        Thread.sleep(500);
        atest = new AStar();
        System.out.println(SecondTestAStar(atest));
        Thread.sleep(500);
        System.out.println(SecondTestPath(atest));
        Thread.sleep(500);
        System.out.println(SecondTestWeight(atest));
        Thread.sleep(500);
        atest = new AStar();
        System.out.println(ThirdTestAStar(atest));
        Thread.sleep(500);
        System.out.println(ThirdTestPath(atest));
        Thread.sleep(500);
        System.out.println(ThirdTestWeight(atest));
        Thread.sleep(500);
        atest = new AStar();
        System.out.println(FourthTestAStar(atest));
        Thread.sleep(500);
        System.out.println(FourthTestPath(atest));
        Thread.sleep(500);
        System.out.println(FourthTestWeight(atest));
        Thread.sleep(500);
        atest = new AStar();
        System.out.println(FifthTestAStar(atest));
        Thread.sleep(500);
        System.out.println(FifthTestPath(atest));
        Thread.sleep(500);
        System.out.println(FifthTestWeight(atest));
        Thread.sleep(500);
    }

    private static String FirstTestH() {
        if (AStar.h(new Vertex('a', 0, 0), new Vertex('b', 0, 0)) == 0.0)
            return "First test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "First test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String SecondTestH() {
        if (Math.abs(AStar.h(new Vertex('a', 100.234, 2), new Vertex('b', 189.2, 0)) - 88.98847765862723) < DOUBLE_CONST)
            return "Second test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Second test: heuristic." + "\tAnswer is incorrest. Test failed";
    }

    private static String ThirdTestH() {
        if (Math.abs(AStar.h(new Vertex('a', 100, 300), (new Vertex('b', 100 + Math.sqrt(600), 305))) - 25) < DOUBLE_CONST)
            return "Third test: heuristic." + "\tAnswer is correst. Test: OK";
        else return "Third test: heuristic." + "\tAnswer is incorrest. Test failed";
    }


    private static String FirstTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("2\na 0 0\nb 0 0\na b 0");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('b'));
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
        if (Math.abs(n.getWeight() - 0) < DOUBLE_CONST) return "First test: Weight." + "\tAnswer is correst. Test: OK";
        else return "First test: Weight." + "\tAnswer is incorrest. Test failed";
    }
    private static String SecondTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("5\na 100.234 2\nb 189.2 0\nc 200 300\n" +
                "d 100 300\ne 500 500\na b 3\nb c 1\nc d 1\na d 5\nd e 1");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('e'));
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
        if (Math.abs(n.getWeight() - 6) < DOUBLE_CONST) return "Second test: Weight." + "\tAnswer is correst. Test: OK";
        else return "Second test: Weight." + "\tAnswer is incorrest. Test failed";
    }
    private static String ThirdTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("8\na 100 300\nb 400 500\nc 350 250\n" +
                "d 80 90\ne 330 220\nx 105 375\ny 296 100\nz 500 150\na b 3\na c 5\na d 7\na e 1\nx z 1\ny z 4");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('d'));
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('d'));
        if (test.equals(array)) return "Third test: AStar." + "\tAnswer is correst. Test: OK";
        else return "Third test: AStar." + "\tAnswer is incorrest. Test failed";
    }
    private static String ThirdTestPath(AStar n) {
        if (n.getPath().equals("ad")) return "Third test: Path." + "\tAnswer is correst. Test: OK";
        else return "Third test: Path." + "\tAnswer is incorrest. Test failed";
    }
    private static String ThirdTestWeight(AStar n) {
        if (Math.abs(n.getWeight() - 7) < DOUBLE_CONST) return "Third test: Weight." + "\tAnswer is correst. Test: OK";
        else return "Third test: Weight." + "\tAnswer is incorrest. Test failed";
    }
    private static String FourthTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("6\na 1 1\nb 1 2\nc 2 3\n" +
                "d 3 4\ne 4 5\nf 5 6\na b 1\nb c 2\nc d 3\nd e 4\ne f 5\na f 34");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('f'));
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('b'));
        array.add(graph.findVertex('c'));
        array.add(graph.findVertex('d'));
        array.add(graph.findVertex('e'));
        array.add(graph.findVertex('f'));
        if (test.equals(array)) return "Fourth test: AStar." + "\tAnswer is correst. Test: OK";
        else return "Fourth test: AStar." + "\tAnswer is incorrest. Test failed";
    }
    private static String FourthTestPath(AStar n) {
        if (n.getPath().equals("abcdef")) return "Fourth test: Path." + "\tAnswer is correst. Test: OK";
        else return "Fourth test: Path." + "\tAnswer is incorrest. Test failed";
    }
    private static String FourthTestWeight(AStar n) {
        if (Math.abs(n.getWeight() - 15) < DOUBLE_CONST) return "Fourth test: Weight." + "\tAnswer is correst. Test: OK";
        else return "Fourth test: Weight." + "\tAnswer is incorrest. Test failed";
    }
    private static String FifthTestAStar(AStar n) {
        Graph graph = GraphController.readGraphForTests("4\na 300 300\nb 200 200\nd 400 200\ne 300 100" +
                "\na b 4\na d 5\nb e 1\nd e 0.5");
        ArrayList<Vertex> test = n.a_star_public(graph.findVertex('a'), graph.findVertex('e'));
        ArrayList<Vertex> array = new ArrayList<>();
        array.add(graph.findVertex('a'));
        array.add(graph.findVertex('b'));
        array.add(graph.findVertex('e'));
        if (test.equals(array)) return "Fifth test: AStar." + "\tAnswer is correst. Test: OK";
        else return "Fifth test: AStar." + "\tAnswer is incorrest. Test failed";
    }
    private static String FifthTestPath(AStar n) {
        if (n.getPath().equals("abe")) return "Fifth test: Path." + "\tAnswer is correst. Test: OK";
        else return "Fifth test: Path." + "\tAnswer is incorrest. Test failed";
    }
    private static String FifthTestWeight(AStar n) {
        if (Math.abs(n.getWeight() - 5) < DOUBLE_CONST) return "Fifth test: Weight." + "\tAnswer is correst. Test: OK";
        else return "Fifth test: Weight." + "\tAnswer is incorrest. Test failed";
    }
}