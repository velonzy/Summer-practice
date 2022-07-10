package application.app;

public class CheckRules {
    public static void inputIsEmpty() throws DataFormatException {
        throw new DataFormatException("You haven't entered any data");
    }

    public static void checkNumberOfVertexes(Integer numberOfVertexes) throws NumberFormatException, DataFormatException {
        if (numberOfVertexes < 0){
            throw new DataFormatException("Number of vertex can't be negative");
        }
    }

    public static void checkVertexName(String vertex) throws DataFormatException{
        if (vertex.length() > 1)
            throw new DataFormatException("Vertex name must be one character, name '" + vertex + "' is invalid");
        if (!('a' <= vertex.charAt(0) && vertex.charAt(0) <= 'z'))
            throw new DataFormatException("Name of the vertex must be the English letter, '" + vertex + "' is invalid");
    }

    public static void checkIfVertexNameIsAvailable(Graph graph, String vertex) throws DataFormatException{
        if (!graph.isNameAvailable(vertex.charAt(0)))
            throw new DataFormatException("Duplication of  vertex name, '" + vertex.charAt(0) + "' already exists");
    }

    public static void checkCoordinates(String inputX, String inputY) throws NumberFormatException, DataFormatException{
        try {
            double x = Double.parseDouble(inputX);
            double y = Double.parseDouble(inputY);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        if (Double.parseDouble(inputX) < 0 || Double.parseDouble(inputX) > 600)
            throw new DataFormatException("Error in coordinates, x must be: 0 ≤ x ≤ 600");
        if (Double.parseDouble(inputY) < 0 || Double.parseDouble(inputY) > 530)
            throw new DataFormatException("Error in coordinates, y must be: 0 ≤ y ≤ 530");
    }

    public static void checkEdgeWeight(String weight) throws NumberFormatException, DataFormatException {
        double weightNumber = Double.parseDouble(weight);
        if (weightNumber < 0) throw new DataFormatException("Edge's weight can't be negative");
        if (weightNumber > Double.MAX_VALUE) throw new DataFormatException("Invalid edge value");
    }

    public static void checkEdge(Graph graph, String first, String second, String weight)
            throws NumberFormatException, DataFormatException {
        checkVertexName(first);
        checkVertexName(second);
        Vertex firstVertex = graph.findVertex(first.charAt(0));
        Vertex secondVertex = graph.findVertex(second.charAt(0));
        if (firstVertex == null || secondVertex == null){
            StringBuilder edge = new StringBuilder();
            edge.append(("Edge '" + first + " " + second + " " + weight + "'").toString());
            throw new DataFormatException(edge.toString() + " can't be created, one or two vertexes don't exist");
        } else if (firstVertex == secondVertex) {
            StringBuilder edge = new StringBuilder();
            edge.append(("Edge '" + first + " " + second + " " + weight + "'").toString());
            throw new DataFormatException(edge.toString() + " can't be created, vertexes must be different");
        }
        checkEdgeWeight(weight);
    }

    public static void edgeAlreadyExists(String first, String second, String weight) throws DataFormatException{
        StringBuilder edge = new StringBuilder();
        edge.append(("Edge '" + first + " " + second + " " + weight + "'").toString());
        throw new DataFormatException(edge.toString() + " can't be created, such edge already exists");
    }
}
