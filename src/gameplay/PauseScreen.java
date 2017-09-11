package gameplay;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-09-30
 */

import animations.Animation;
import biuoop.DrawSurface;

/**
 * This class is in charge of pausing the game and introducing pause screen.
 */
public class PauseScreen implements Animation {

    private boolean stop;

    /**
     * constructor.
     */
    public PauseScreen() {

        this.stop = false;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }



    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // TODO Auto-generated method stub

    }

}