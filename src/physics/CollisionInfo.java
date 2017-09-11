package physics;

import objects.Collidable;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */


/**
 * This class get the information about the current colliding object with the ball.
 */
public class CollisionInfo {

    private Collidable collisionInfo;

    private Point collisionPoint;

    /**
     * @param object which the ball might get into it.
     * @param intersect the intersection point
     */
    public CollisionInfo(Collidable object, Point intersect) { // maybe second argument:  'Line trajectory'  ??
        this.collisionInfo = object;
        this.collisionPoint = intersect;
    }

    /**
     * the point at which the collision occurs.
     * @return the point of which the collision occurs
     */
    public Point collisionPoint() {
        return this.collisionPoint;

    }

    /**
     * the collidable object involved in the collision.
     * @return the collidable.
     */
    public Collidable collisionObject() {

        return this.collisionInfo;
    }
}