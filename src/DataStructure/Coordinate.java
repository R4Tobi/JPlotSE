package src.DataStructure;

/**
 * Class Coordinate
 * used to Store and Access Coordinates. Also supports naming Coordinates.
 */
public class Coordinate {
    private double x;
    private double y;
    private final String type;

    /**
     * Constructor for class Coordinate
     * @param x x-Coordinate
     * @param y y-Coordinate
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        this.type = "";
    }

    /**
     * Constructor for class Coordinate
     * @param x x-coordinate
     * @param y y-coordinate
     * @param type Name of Coordinate, e.g. "Extremum"
     */
    public Coordinate(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * get value of x
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * set value of x
     * @param x x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * get value of y
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * set value of y
     * @param y y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * get value of name / type of Coordinate
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * get String of Coordinate
     * @return Coordinate with name as String
     */
    @Override
    public String toString() {
        return type + " (" + x + ", " + y + ')';
    }
}
