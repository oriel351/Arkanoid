package objects;

import physics.Point;
import physics.Rectangle;
import physics.Velocity;


/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */


/**
 * interface collidable - defines the behavior of a collidable object.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with given velocity.
     * @param hitter the object that inflicted on us
     * @param collisionPoint the collidable object was hit by the ball
     * @param currentVelocity of the ball
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}