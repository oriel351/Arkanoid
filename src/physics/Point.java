/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-05
 */

package physics;

/**
 * This class defines a Point, with its member and behaviors.
 */
public class Point {
    /**
     * A Point has an x and y values. It can measure the distance to other
     * points, and can tell if it's equal to another point.
     */

    private double x;
    private double y;

    /**
     * Construct a point given other point.
     * @param other is a point which we make a new point from.
     */
    public Point(Point other) {

        this.x = other.getX();
        this.y = other.getY();

    }

    /**
     * Construct a point given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {

        this.x = x;
        this.y = y;
    }

    /**
     * This function returns the distance of this point to the other point.
     * @param other a point to measure the distance to
     * @return the distance to the other point
     */

    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * This function return 'true' if the points are equal, 'false' otherwise.
     * @param other another given point
     * @return 'true' if the points are equal, 'false' otherwise
     */
    public boolean equals(Point other) {
        return (this.x == other.x && this.y == other.y);
    }

    /**
     * @return the sum of the coordinates.
     */
    @Override
    public int hashCode() {
        return (int) (this.x + this.y);
    }
}
