package application.app;

//import project.application.model.Vertex;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeDrawable {
    private double weight;
    private Vertex first, second;
    private Line vertexLine;
    private Text weightView;
    private final static double radiusOfVertexCircle = 15.0;

    public EdgeDrawable(Vertex first, Vertex second, double weight){
        this.first = first;
        this.second = second;
        this.weight = weight;
        vertexLine = new Line();
        weightView = new Text(Double.toString(weight));
        double x1, x2, y1, y2;
        x1 = (Double) second.getCoordinates().getX();
        x2 = (Double) first.getCoordinates().getX();
        y1 = (Double) second.getCoordinates().getY();
        y2 = (Double) first.getCoordinates().getY();
        if (x1 > x2) {
            x1 -= radiusOfVertexCircle;
            x2 += radiusOfVertexCircle;
        } else {
            x2 -= radiusOfVertexCircle;
            x1 += radiusOfVertexCircle;
        }
//        if ((Double) first.getCoordinates().getX() > (Double) second.getCoordinates().getX()) {
//            x1 = (Double) second.getCoordinates().getX() + radiusOfVertexCircle;
//            x2 = (Double) first.getCoordinates().getX() - radiusOfVertexCircle;
//            y1 = (Double) second.getCoordinates().getY();
//            y2 = (Double) first.getCoordinates().getY();
//        } else {
//            x2 = (Double) second.getCoordinates().getX() - radiusOfVertexCircle;
//            x1 = (Double) first.getCoordinates().getX() + radiusOfVertexCircle;
//            y2 = (Double) second.getCoordinates().getY();
//            y1 = (Double) first.getCoordinates().getY();
//        }
        vertexLine.setStartX(x1);
        vertexLine.setEndX(x2);
        vertexLine.setStartY(y1);
        vertexLine.setEndY(y2);
        if (x1 > x2) {
            weightView.setX(x1 - Math.abs(x2 - x1) / 2);
        } else {
            weightView.setX(x2 - Math.abs(x2 - x1) / 2);
        }
        weightView.setY((y1 + y2) / 2 - 5);

    }

    public Text getName(){
        return weightView;
    }

    public Line getView() { return vertexLine; }
}

/*
3
a 200 300
b 150 400
c 600 200
a b 5
b c 4.7
c a 20
 */
