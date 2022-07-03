package application.app;

//import project.application.model.Vertex;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeDrawable {
    private double weight;
    private Vertex first, second;
    private Line line;
    private Text text;
    private final static double radiusOfVertexCircle = 15.0;

    public EdgeDrawable(Vertex first, Vertex second, double weight){
        this.first = first;
        this.second = second;
        this.weight = weight;
        line = new Line();
        text = new Text();
        double x1, x2, y1, y2;
        if ((Double) first.getCoordinates().getX() > (Double) second.getCoordinates().getX()) {
            x1 = (Double) second.getCoordinates().getX() + radiusOfVertexCircle;
            x2 = (Double) first.getCoordinates().getX() - radiusOfVertexCircle;
            y1 = (Double) second.getCoordinates().getY();
            y2 = (Double) first.getCoordinates().getY();
        } else {
            x2 = (Double) second.getCoordinates().getX() + radiusOfVertexCircle;
            x1 = (Double) first.getCoordinates().getX() - radiusOfVertexCircle;
            y2 = (Double) second.getCoordinates().getY();
            y1 = (Double) first.getCoordinates().getY();
        }
        line.setStartX(x1);
        line.setEndX(x2);
        line.setStartY(y1);
        line.setEndY(y2);
        text.setX(x2 - 2.0);
        text.setY(y2 - 3.0);
    }
}
