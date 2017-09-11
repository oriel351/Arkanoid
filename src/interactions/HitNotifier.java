package interactions;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-05-27
 */

/**
 * a HitNotifier interface.
 * indicate that objects that implement it send notifications when they are being hit.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl is a listener.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl is a listener.
     */
    void removeHitListener(HitListener hl);

}