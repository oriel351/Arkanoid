package interactions;

import objects.Ball;
import objects.Block;
import level.GameLevel;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that were removed.
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter removedBlocks;

    /**
     * @param game the game which we are removing blocks from.
     * @param removedBlocks the counter that holds the number of blocks remained.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.removedBlocks = removedBlocks;
    }

    @Override
    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game.
     */
    public void hitEvent(Block beingHit, Ball hitter) {

        if (beingHit.getHits() == 1) {
            beingHit.setHits(beingHit.getHits() - 1);
            this.removedBlocks.decrease(1); // remaining blocks decreased by 1.
            beingHit.removeFromGame(this.game); // removing the block
        } else {
            beingHit.setHits((beingHit.getHits() - 1)); // setting the new value(-1)

        }

    }
}
