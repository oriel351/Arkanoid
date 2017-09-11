package physics;

import java.util.List;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-05
 */

/**
 * Line class. this class generates a line, with its starting and ending point.
 */
public class Line {

    private Point start;
    private Point end;

    /**
     * constructor.
     * @param start is the starting point
     * @param end is the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructor.
     * @param x1 is x coordination of the start point
     * @param y1 is y coordination of the start point
     * @param x2 is x coordination of the end point
     * @param y2 is y coordination of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * this method calculates the length of a given line.
     * @return the length of the line
     */
    public double length() {

        return this.start.distance(this.end);
    }

    /**
     * this method calculates the middle point of a given line.
     * @return the middle point of the line
     */

    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2.0;
        double y = (this.start.getY() + this.end.getY()) / 2.0;
        Point middle = new Point(x, y);
        return middle;
    }

    /**
     * @return the start point of the line
     */

    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other is the other line
     * @return true/false
     */

    public boolean isIntersecting(Line other) {
        return !(this.intersectionWith(other) == null);
    }

    /**
     * Returns the intersection point if the lines intersect, and null
     * otherwise.
     * @param other is the other line
     * @return the instersection point
     */
    public Point intersectionWith(Line other) {

        /* now we find the coefficients of the equation Ax + By + C = 0 that
         * represent the equation , so we can find the intersection point */

        // first line:

        double a1 = this.end.getY() - this.start.getY();
        double b1 = this.start.getX() - this.end.getX();
        double c1 = (a1 * this.start.getX()) + (b1 * this.start.getY());

        // second line:

        double a2 = other.end.getY() - other.start.getY();
        double b2 = other.start.getX() - other.end.getX();
        double c2 = (a2 * other.start.getX()) + (b2 * other.start.getY());

        // delta:

        double det = (a1 * b2) - (a2 * b1);

        if (det == 0) {
            // Lines are parallel
            return null;
        } else {

            // double x = Math.floor(((b2 * c1) - (b1 * c2)) / det); // rounding up - applicated for the ball game.
            // double y = Math.floor(((a1 * c2) - (a2 * c1)) / det);
            double x = ((b2 * c1) - (b1 * c2)) / det;
            double y = ((a1 * c2) - (a2 * c1)) / det;

            // checking if the intersection point is not on the first's line
            // segment
            if ((x > Math.max(this.start.getX(), this.end.getX()))
                    || (x < Math.min(this.start.getX(), this.end.getX()))
                    || (y > Math.max(this.start.getY(), this.end.getY()))
                    || (y < Math.min(this.start.getY(), this.end.getY()))) {

                return null;
            }
            // checking if the intersection point is not on the second's line
            // segment
            if ((x > Math.max(other.start.getX(), other.end.getX()))
                    || (x < Math.min(other.start.getX(), other.end.getX()))
                    || (y > Math.max(other.start.getY(), other.end.getY()))
                    || (y < Math.min(other.start.getY(), other.end.getY()))) {

                return null;
            }
            Point intersection = new Point(x, y);
            return intersection;

        } // end of else
    } // end of method

    /**
     * equals -- return true is the lines are equal, false otherwise.
     * @param other is the other line
     * @return true/false
     */
    public boolean equals(Line other) {
        return (this.start == other.start && this.end == other.end)
                || (this.start == other.end && this.end == other.start);
    }

    /**
     * hashCode returns the sum of the coordinates of a given line. if the lines
     * are equal - then the hashCode of both should be the same as well(but not
     * in the opposite direction)
     * @return sum of the coordinates
     */
    @Override
    public int hashCode() {
        double sumX = this.start.getX() + this.end.getX();
        double sumY = this.start.getY() + this.end.getY();
        return (int) (sumX + sumY);
    }

    // Assignment 3 addition :

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the
     * line.
     * @param rect the intersected rectangle
     * @return Point the closest one to intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        List<Point> list = rect.intersectionPoints(this);

        if (list.size() == 0) { // in case the line is not intersecting - empty list.
            return null;
        }
        // In general: we have 2 options only : either there is 1 intersection point or 2 intersection points.

        if (list.size() == 1) {
            return (Point) list.get(0);
        } else {
            Point p0 = (Point) list.get(0);
            Point p1 = (Point) list.get(1);
            // if the points are the same we choose to return p1.
            if (p0.equals(p1)) {
                return p1;
            } else {
                if (this.start.distance(p0) > this.start.distance(p1)) {
                    return p1;
                } else {
                    return p0;
                }
            }
        }
    }

    /**
     * Rounds the values of the start and end points of the line.
     * @return the rounded values of the start and the end points of the line
     */
    public Line floorLine() {
        return new Line(Math.floor(this.start.getX()), Math.floor(this.start.getY()), Math.floor(this.end.getX()),
            Math.floor(this.end.getY()));
    }

    /**
     * this function is rounding the values to their nearest integer values.
     * @param x1 is the first line x coordinate.
     * @param y1 is the first line y coordinate.
     * @param x2 is the second line x coordinate.
     * @param y2 is the second line y coordinate.
     * @return the rounded values.
     */
    public static Line floorLine(double x1, double y1, double x2, double y2) {
        return new Line(Math.floor(x1), Math.floor(y1), Math.floor(x2), Math.floor(y2));
    }
}
