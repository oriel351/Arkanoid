package objects;

/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-04-05
 */

import biuoop.DrawSurface;
import interactions.HitListener;
import interactions.HitNotifier;

import java.util.List;
import java.util.ArrayList;

import physics.CollisionInfo;
import physics.GameEnvironment;
import physics.Line;
import physics.Point;
import physics.Velocity;
import level.GameLevel;

/**
 * this class uses the GUI from biuoop 1.4 and makes several experiments.
 */
public class Ball implements Sprite, HitNotifier {

    private Point center;

    private int radius;

    private java.awt.Color color;

    private Velocity velocity;

    private GameEnvironment envi;

    private Line trajectory;

    private List<HitListener> hitListeners;

    /**
     * a constructor in case we have a point instead of coordinates, and a given
     * environment.
     * @param x coordination of the center of the ball.
     * @param y coordination of the center of the ball.
     * @param r the radius
     * @param velocity of the ball
     * @param color the color
     * @param envi the environment which the game is played.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment envi, Velocity velocity) {

        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.envi = envi;
        this.velocity = new Velocity(velocity.getDx(), velocity.getDy());
        this.hitListeners = new ArrayList<HitListener>();
        // the initial trajectory
        setTrajectory();
    }

    /**
     * @return the x coordinate of the center
     */
    public int getX() {

        return (int) this.center.getX();

    }

    /**
     * @return the y coordinate of the center
     */
    public int getY() {
        return (int) this.center.getY();

    }

    /**
     * @return the size of the radius.
     */
    public int getSize() {
        return this.radius;

    }

    /**
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return the velocity of current ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * @param v is the given velocity we add to the current ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * @param dx is the difference in x values.
     * @param dy is the difference in y values.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * this function sets the ball's new trajectory after each move.
     * private - so only the ball class itself could change the trajectory.
     */
    private void setTrajectory() {
        Point end = new Point(this.center.getX() + this.velocity.getDx(), this.center.getY() + this.velocity.getDy());
        this.trajectory = new Line(this.center, end);
    }

    /**
     * this function moves the ball one step - frame.
     * @param dt the the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {

        CollisionInfo obj = this.envi.getClosestCollision(trajectory);

        if (obj == null) {
            // Point start = new Point(this.center);
            this.center = this.velocity.applyToPoint(this.center);
            setTrajectory(); //
        } else {

            Point col = obj.collisionPoint();
            int collisionSide = obj.collisionObject().getCollisionRectangle().sideIntersectPoint(col);

            /* as described in Rectangle.sideIntersectPoint , we get a number
             * defining the collision side: 0 - top side, 1 - right side,
             * 2 - bottom side, 3 - left side. */
            if (collisionSide == 0) {
                this.center = new Point(col.getX(), col.getY() - this.radius);
            }
            if (collisionSide == 1) {
                this.center = new Point(col.getX() + this.radius, col.getY());
            }
            if (collisionSide == 2) {
                this.center = new Point(col.getX(), col.getY() + this.radius);
            }
            if (collisionSide == 3) {
                this.center = new Point(col.getX() - this.radius, col.getY());
            }

            // the change of velocity of ball :
            setVelocity(obj.collisionObject().hit(this, col, this.velocity));

            setTrajectory();

        }

    }

    /**
     * @param surface is a given surface this function draws the current ball on
     *        a given surface
     */

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        surface.setColor(java.awt.Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * @param g the game the current ball is being added to.
     */
    @Override
    public void addToGame(GameLevel g) {

        g.addSprite(this);

    }

    /**
     * @param g the game we are removing from the ball.
     * @return boolean if we managed to remove.
     */
    public boolean removeFromGame(GameLevel g) {
        // we want to be sure that it succeeded
        return g.removeSprite(this);
    }

    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);

    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

}
