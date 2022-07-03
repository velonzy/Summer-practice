package application.model;

public class Coordinates <T>{
    private final T x, y;
    Coordinates (T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX(){
        return x;
    }

    public T getY(){
        return y;
    }
}
