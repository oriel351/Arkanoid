package animations;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-09-27
 */

import biuoop.DrawSurface;

/**
 * interface Animation. is in charge of something.
 */
public interface Animation {

    /**
     * this function handles the stopping condition.
     * @param d the surface currently we are drawing
     * @param dt the time difference.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return if either it should or shouldn't stop.
     */
    boolean shouldStop();
}
