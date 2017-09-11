package interactions;

import objects.Ball;
import objects.Block;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-05-27
 */

/**
 * interface HitListener.
 * Objects that want to be notified of hit events, should implement the HitListener interface, and register themselves
 * with a HitNotifier object using its addHitListener method.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     *  The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit is the objects being hit
     * @param hitter the object that hit
     */
    void hitEvent(Block beingHit, Ball hitter);

}
