package level;

import gameplay.PauseScreen;
import interactions.BallRemover;
import interactions.BlockRemover;
import interactions.Counter;
import interactions.HitListener;
import interactions.ScoreTrackingListener;

import java.awt.Color;
import java.util.Random;
import java.util.List;

import objects.Ball;
import objects.Block;
import objects.Collidable;
import objects.LevelIndicator;
import objects.LivesIndicator;
import objects.Paddle;
import objects.ScoreIndicator;
import objects.Sprite;
import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import physics.GameEnvironment;
import physics.Point;
import physics.Rectangle;
import physics.SpriteCollection;

/**
 * class Game. this is where the game happens.
 */
public class GameLevel implements Animation {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private LevelInformation levelInfo;
    private AnimationRunner runner;
    private boolean stop;
    private int borderSize;
    private Paddle paddle;
    private Counter remBlocks;
    private Counter remBalls;
    private Counter score;
    private Counter numOfLives;
    private biuoop.KeyboardSensor keyboard;

    /**
     * generating the game level.
     * @param keys is the keyboard.
     * @param animation is the animation.
     * @param info is the level info.
     * @param numOfLives is the number of lives the player has.
     * @param score is the player's score so far.
     */
    public GameLevel(KeyboardSensor keys, AnimationRunner animation, LevelInformation info, Counter numOfLives,
            Counter score) {

        this.environment = new GameEnvironment();
        this.levelInfo = info;
        this.runner = animation;
        this.keyboard = keys;
        this.sprites = new SpriteCollection();
        this.borderSize = 20;
        this.remBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());
        this.remBalls = new Counter(0);
        this.score = score;
        this.numOfLives = numOfLives;

    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add
     * them to the game.
     */
    public void initialize() {

        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        // *********** CREATING THE LISTENERS ***************

        HitListener hlBlocks = new BlockRemover(this, this.remBlocks);

        HitListener hlScore = new ScoreTrackingListener(this.score);

        HitListener hlBalls = new BallRemover(this, this.remBalls);

        // **************** ADDING THE BACKGROUND *********************

        this.levelInfo.getBackground().addToGame(this);

        // *********** CREATING THE BORDERS **************

        double size = this.borderSize;

        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        new Block(size, size, WIDTH - size, size, color).addToGame(this);

        new Block(WIDTH - size, size, size, HEIGHT - size, color).addToGame(this);

        new Block(0, size, size, HEIGHT - size, color).addToGame(this);

        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        // score & lives indicator & game level:

        color = Color.gray;
        Block b = new Block(0, 0, WIDTH, size, color);
        b.addToGame(this);
        Sprite scoreIndicator = new ScoreIndicator(b, this.score);
        scoreIndicator.addToGame(this);
        Sprite livesIndicator = new LivesIndicator(b, this.numOfLives);
        livesIndicator.addToGame(this);

        Sprite levelIndicator = new LevelIndicator(b, levelInfo.levelName());
        levelIndicator.addToGame(this);

        // this is the block indicating a ball should die:

        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        Block bord = new Block(0, HEIGHT, WIDTH, size, color);
        bord.addToGame(this);
        bord.addHitListener(hlBalls);

        // *****************RETRIEVING THE BLOCKS AND ADDING THEM TO ALL THE LISTS, INCLUDING LISTENER *****************

        List<Block> blocks = this.levelInfo.blocks();
        for (Block bl : blocks) {
            bl.addToGame(this);
            bl.addHitListener(hlScore);
            bl.addHitListener(hlBlocks);

        }

    } // END OF INITIALIZE

    /**
     *
     */
    public void playOneTurn() {

        // ************ CREATING THE PADDLE *****************

        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        Rectangle rec =
                new Rectangle(new Point(((WIDTH) / 2 - (this.levelInfo.paddleWidth() / 2)), HEIGHT - this.borderSize
                        - HEIGHT / 24), this.levelInfo.paddleWidth(), HEIGHT / 24);

        this.paddle = new Paddle(rec, color, this.levelInfo.paddleSpeed(), this, this.keyboard);
        this.paddle.addToGame(this);

        // ********** CREATING THE BALLS *************

        this.createBallsOnTopOfPaddle(this.levelInfo.numberOfBalls());

        // **************STARTING TO RUN **********

        this.stop = true;
        // use our runner to run the current animation -- which is one turn of the game.

        // ************ COUNTDWON ANIMATION **********

        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        // **********GAME RUNNING: ************

        this.runner.run(this);

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        if (this.remBalls.getValue() == 0) {
            this.paddle.removeFromGame(this);
            this.numOfLives.decrease(1);
            this.stop = false;
        }

        if (this.remBlocks.getValue() == 0) {
            this.score.increase(100);
            this.paddle.removeFromGame(this);
            this.stop = false;
        }
        // WE LOST THE GAME

        // if the player wants to take a break
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(new PauseScreen(), this.keyboard, KeyboardSensor.SPACE_KEY));
        }

    }

    @Override
    public boolean shouldStop() {
        return !this.stop;
    }

    /**
     * This method creates the balls on the paddle.
     * @param numberOfBalls the number of balls.
     */
    private void createBallsOnTopOfPaddle(int numberOfBalls) {

        HitListener hlScore = new ScoreTrackingListener(this.score);

        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        for (int i = 0; i < numberOfBalls; i++) {

            int radius = 6;
            int xCoord =
                    (int) (paddle.getCollisionRectangle().getUpperLeft().getX() + paddle.getCollisionRectangle()
                            .getWidth() / 2);

            int yCoord = (int) (paddle.getCollisionRectangle().getUpperLeft().getY() + radius);

            // random color:
            color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

            Ball ball =
                    new Ball(xCoord, yCoord, radius, color, this.environment, levelInfo.initialBallVelocities().get(i));
            ball.addToGame(this);
            this.remBalls.increase(1);
            ball.addHitListener(hlScore);
        }

    }

    /**
     * @param c the collidable we want to add to the game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * @param c the collidable we want to remove.
     * @return the collidable objects that is in the environment
     */
    public boolean removeCollidable(Collidable c) {
        return this.environment.removeCollidable(c);

    }

    /**
     * @param s the sprite object we are adding to the game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * @param s the sprite we want to remove.
     * @return the sprite that we want to remove.
     */
    public boolean removeSprite(Sprite s) {
        return this.sprites.removeCollidable(s);
    }

    /**
     * @return the width of the surface.
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * @return the height of the surface.
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * @return the size of the blocks that build the borders of the current game.
     */
    public int getBorderSize() {
        return this.borderSize;
    }

    /**
     * @return the number of remaining blocks to end the level.
     */
    public int getRemainingBlocks() {
        return this.remBlocks.getValue();
    }

}
