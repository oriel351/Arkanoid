package physics;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-05
 */


/**
 * this class simulates the speed of a given object (Ball).
 */
public class Velocity {

    private double dx;

    private double dy;

    /**
     * constructor.
     * @param dx is the change in position in x
     * @param dy is the change in position in y
     */
    public Velocity(double dx, double dy) {

        this.dx = dx;
        this.dy = dy;

    }

    /**
     * @return the dx value of current velocity object.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the dy value of current velocity object.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * @param angle the angle which the ball will move in
     * @param speed the speed the ball will go that direction
     * @return Velocity ( as a vector actually)
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        double toRad = Math.toRadians(angle); // the java Math cos/sin etc. uses Radians.
        double dx = speed * Math.cos(toRad);
        double dy = speed * Math.sin(toRad);
        return new Velocity(dx, dy);

    }

    /**
     * Take a point with position (x,y) and return. a new point with position (x
     * + dx, y + dy)
     * @param p is the point we get to add.
     * @return a new point
     */
    public Point applyToPoint(Point p) {
        return new Point(this.dx + p.getX(), this.dy + p.getY());
    }
}
