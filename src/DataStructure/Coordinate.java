package src.DataStructure;

public class Coordinate {
    private double x;
    private double y;
    private String type;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        this.type = "";
    }

    public Coordinate(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type + "(" + x + ", " + y + ')';
    }
}
