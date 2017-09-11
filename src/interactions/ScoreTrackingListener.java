package interactions;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-08-10
 */
import objects.Ball;
import objects.Block;

/**
 * this class updates a counter for the score of the player when blocks are being hit and removed.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter score;

    /**
     * @param score of the player
     */
    public ScoreTrackingListener(Counter score) {

        this.score = score;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

        if (beingHit.getHits() == 1) {
            this.score.increase(15); // increasing score by 5.
        } else {
            this.score.increase(5); // setting the new value(-1)

        }

    }

}
