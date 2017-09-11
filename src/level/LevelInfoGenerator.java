package level;

import java.util.ArrayList;
import java.util.List;

import objects.Background;
import objects.Block;
import objects.Sprite;
import physics.Velocity;

/**
 * this class is in charge of the first level in the game.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-09-30 @edited 2015-10-15
 */
public class LevelInfoGenerator implements LevelInformation {

    private String levelName;
    private int numberOfBalls;
    private List<Velocity> initialBallsVelocities;
    private Sprite background;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Block> blockList;
    private int numBlocksRemove;

    /**
     * constructor.
     * @param levelName the level name.
     * @param initialBalls the number of balls in the beginning.
     * @param background the background path.
     * @param paddleSpeed the paddle speed.
     * @param paddleWidth the paddle width.
     * @param numBlocks the number of blocks in the beginning.
     * @param blockList the list of the different blocks.
     */
    public LevelInfoGenerator(String levelName, String initialBalls, String background, int paddleSpeed,
            int paddleWidth, int numBlocks, List<Block> blockList) {

        this.levelName = levelName;

        this.initialBallsVelocities = generateVelocities(initialBalls);
        this.numberOfBalls = this.initialBallsVelocities.size();

        this.background = new Background(background);

        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blockList = blockList;
        this.numBlocksRemove = numBlocks;
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallsVelocities;
    }

    /**
     * generates the velocity list.
     * @param initialBalls the string which we take the balls from.
     * @return the list of velocities.
     */
    public List<Velocity> generateVelocities(String initialBalls) {

        List<Velocity> list = new ArrayList<Velocity>();
        String[] velocities = initialBalls.split(",");
        for (int i = 0; i < velocities.length; i++) {
            list.add(Velocity.fromAngleAndSpeed(190 + ((140 / velocities.length) * i),
                    Integer.parseInt(velocities[i])));

        }
        return list;

    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {

        return this.levelName;
    }

    @Override
    public List<Block> blocks() {
        return this.blockList;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocksRemove;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

}
