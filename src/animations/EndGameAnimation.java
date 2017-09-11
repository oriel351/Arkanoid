package animations;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-01
 */

import interactions.Counter;
import biuoop.DrawSurface;

/**
 * this class will make the animation for the end of the game.
 */
public class EndGameAnimation implements Animation {

    private Counter score;
    private boolean stop;
    private int end;

    /**
     * @param score the currently score in the game
     * @param end the type of animation:
     *        0 for game over
     *        1 for winning game.
     */
    public EndGameAnimation(Counter score, int end) {

        this.score = score;
        this.stop = false;
        this.end = end;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        // *******GAME OVER CASE : **********

        if (this.end == 0) {
            d.drawText(10, d.getHeight() / 4, "Game Over. Your score is " + this.score.getValue(), 32);
        }

        // ******* WIN GAME CASE: ************

        if (end == 1) {
            d.drawText(10, d.getHeight() / 4, "You Win! Your score is " + this.score.getValue(), 32);
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
