package application.app;

//import project.application.model.Vertex;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeDrawable {
    private double weightFromFirstToSecond, weightFromSecondToFirst;
    private Vertex first, second; // first - source, second - destination or versa
    private boolean directionFromFirstToSecond, directionFromSecondToFirst;
    private Line edgeLine;
    private Text weightView;
    private final static double radiusOfVertexCircle = 15.0;

    public EdgeDrawable(Vertex first, Vertex second, double weight){
        this.first = first;
        this.second = second;
        weightFromFirstToSecond = weight;
        directionFromFirstToSecond = true;
        edgeLine = new Line();
        weightView = new Text("|" + Double.toString(weightFromFirstToSecond) + ">");
        double x1, x2, y1, y2;
        x1 = first.getCoordinates().getX();
        x2 = second.getCoordinates().getX();
        y1 = first.getCoordinates().getY();
        y2 = second.getCoordinates().getY();
        if (x1 > x2) {
            x1 -= radiusOfVertexCircle;
            x2 += radiusOfVertexCircle;
        } else {
            x2 -= radiusOfVertexCircle;
            x1 += radiusOfVertexCircle;
        }
        edgeLine.setStartX(x1);
        edgeLine.setEndX(x2);
        edgeLine.setStartY(y1);
        edgeLine.setEndY(y2);
        if (x1 > x2) {
            weightView.setX(x1 - Math.abs(x2 - x1) / 2);
        } else {
            weightView.setX(x2 - Math.abs(x2 - x1) / 2);
        }
        weightView.setY((y1 + y2) / 2 - 5);
        edgeLine.setStroke(Paint.valueOf("#3fbab4"));
    }

    public void setReversedDirection(double weight){
        weightFromSecondToFirst = weight;
        directionFromSecondToFirst = true;
        weightView.setText("<" + Double.toString(weightFromSecondToFirst) + "|" + Double.toString(weightFromFirstToSecond) + ">");
    }

    public void deleteOneWay(Vertex first, Vertex second){
        if (first == this.first & second == this.second) {
            directionFromFirstToSecond = false;
            weightFromFirstToSecond = 0;
            weightView.setText("<" + Double.toString(weightFromSecondToFirst) + "|");
        } else if(first == this.second & second == this.first) {
            directionFromSecondToFirst = false;
            weightFromSecondToFirst = 0;
            weightView.setText("|" + Double.toString(weightFromFirstToSecond) + ">");
        }
    }

    public boolean isOnlyOneWay() {
        return directionFromFirstToSecond ^ directionFromSecondToFirst;
    }

    public boolean isReverse(Vertex first, Vertex second) {
        return (this.first == second && this.second == first);
    }

    public boolean cmpFirst(Vertex other) {
        return first == other;
    }

    public boolean cmpSecond(Vertex other) {
        return second == other;
    }

    public Text getName(){
        return weightView;
    }

    public Line getView() { return edgeLine; }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }

    public boolean cmpBoth(Vertex first, Vertex second) {
        return cmpFirst(first) && cmpSecond(second);
    }

    public void moveLine() {
        double x1, x2, y1, y2;
        x1 = first.getCoordinates().getX();
        x2 = second.getCoordinates().getX();
        y1 = first.getCoordinates().getY();
        y2 = second.getCoordinates().getY();
        if (x1 > x2) {
            x1 -= radiusOfVertexCircle;
            x2 += radiusOfVertexCircle;
        } else {
            x2 -= radiusOfVertexCircle;
            x1 += radiusOfVertexCircle;
        }
        edgeLine.setStartX(x1);
        edgeLine.setEndX(x2);
        edgeLine.setStartY(y1);
        edgeLine.setEndY(y2);
        if (x1 > x2) {
            weightView.setX(x1 - Math.abs(x2 - x1) / 2);
        } else {
            weightView.setX(x2 - Math.abs(x2 - x1) / 2);
        }
        weightView.setY((y1 + y2) / 2 - 5);
    }
}

/*
3
a 200 300
b 150 400
z 600 200
a b 5
b z 4.7
z a 20
a z 11
 */