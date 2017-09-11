package physics;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */
import java.util.List;
import java.util.ArrayList;

/**
 * Rectangle class. this class defines the characters of a rectangle.
 */
public class Rectangle {

    private Point upperLeft;

    private double width;

    private double height;

    private Line[] side;

    /**
     * constructor. Create a new rectangle with location and width/height.
     * Creating the borders ( in form of an Array of lines, for further
     * calculations.
     * @param upperLeft The upper left corner of the rectangle.
     * @param width of the rectangle.
     * @param height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {

        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;

        // creating the lines :
        this.side = new Line[4];

        double xUpp = this.upperLeft.getX();
        double yUpp = this.upperLeft.getY();

        // top border :
        this.side[0] = Line.floorLine(xUpp, yUpp, xUpp + width, yUpp);

        // right border :
        this.side[1] = Line.floorLine(xUpp + width, yUpp, xUpp + width, yUpp + height);

        // bottom border :
        this.side[2] = Line.floorLine(xUpp, yUpp + height, xUpp + width, yUpp + height);

        // left border :
        this.side[3] = Line.floorLine(xUpp, yUpp, xUpp, yUpp + height);

    }

    /**
     * Return the width of the rectangle.
     * @return the rectangle's width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return upperLeft point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * setting the upper-left point of the rectangle.
     * @param newX is the new value of x
     * @param newY is the new value of y
     */
    public void setUpperLeft(double newX, double newY) {
        this.upperLeft = new Point(newX, newY);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified
     * line.
     * @param line the specified line
     * @return list/fixed a list of the points.
     */
    public java.util.List intersectionPoints(Line line) {

        List list = new ArrayList();

        Line l2 = line.floorLine();

        for (int i = 0; i < 4; i++) {
            Line l1 = this.side[i].floorLine();

            Point inter = l1.intersectionWith(l2);
            if (inter != null) {
                list.add(inter);
            }
        }

        if (list.size() <= 2) {
            return list;
        } else {
            // in case the line mitlaked with the diagonal of the rectangle
            // so we have 3 or 4 points (one for each side)

            // Point[] arrayList = (Point[]) list.toArray();

            int[] equal = new int[2];
            int c = 0;
            List fixed = new ArrayList();
            for (int i = 0; i < list.size() - 1; i++) {
                Point p1 = (Point) list.get(i);
                for (int j = i + 1; j < list.size(); j++) {

                    Point p2 = (Point) list.get(j);
                    if (p1.equals(p2)) {
                        // if (list.get(i).equals(list.get(j))) {
                        list.remove(j);
                        j--;
                    }
                }
                int hhh = 6;
            }
            // fixed.add(list.get(equal[0]));
            // fixed.add(list.get(equal[1]));
            return list;

        } // end of else

    }

    /**
     * This function gets a point we already know intersected with the
     * rectangle. returns the a number for the side represented as follows:
     * 1 - top side
     * 2 - right side
     * 3 - bottom side
     * 4 - left side
     * 0 - exception
     * @param intersect the intersection point
     * @return the number representing the side.
     */
    public int sideIntersectPoint(Point intersect) {

        double x = intersect.getX();
        double y = intersect.getY();

        for (int i = 0; i < this.side.length; i++) {
            if (x <= Math.max(this.side[i].start().getX(), this.side[i].end().getX())
                    && x >= Math.min(this.side[i].start().getX(), this.side[i].end().getX())
                    && y <= Math.max(this.side[i].start().getY(), this.side[i].end().getY())
                    && y >= Math.min(this.side[i].start().getY(), this.side[i].end().getY())) {
                return i;
            }

        }
        return 1; // if for some reason the point is not on none of the lines.
    }

}