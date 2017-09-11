
/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-08-10
 */

/**
 * a BallRemover is in charge of removing balls from the game, as well as keeping count
 * of the number of balls that were removed.
 */


package interactions;

import objects.Ball;
import objects.Block;
import level.GameLevel;

public class BallRemover implements HitListener {

    private GameLevel game;
    private Counter removedBalls;

    /**
     * @param game the game which we are removing balls from.
     * @param removedBalls the counter that holds the number of balls remained.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.removedBalls = removedBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.removedBalls.decrease(1);
        hitter.removeFromGame(this.game); // removing the ball

    }
}