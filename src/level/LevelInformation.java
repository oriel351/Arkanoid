package level;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */

import java.util.List;

import objects.Block;
import objects.Sprite;
import physics.Velocity;

/**
 * this interface provides methods and characteristics for making levels.
 */
public interface LevelInformation {

    /**
     * @return the number of balls.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * @return a list of the balls velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return the initial speed of the paddle.
     */
    int paddleSpeed();

    /**
     * @return the initial width of the paddle.
     */
    int paddleWidth();

    /**
     * the level name that displayed at the top of the screen.
     * @return the level name
     */
    String levelName();

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     * @return a list of the blocks
     */
    List<Block> blocks();

    /**
     * Number of levels that should be removed before the level is considered to be "cleared".
     * @return the number of blocks the player should remove.
     */
    int numberOfBlocksToRemove();

    /**
     * @return the background.
     */
    Sprite getBackground();

}