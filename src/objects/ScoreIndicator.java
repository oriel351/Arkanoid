package objects;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-09-27
 */

import interactions.Counter;

import java.awt.Color;

import level.GameLevel;
import biuoop.DrawSurface;

/**
 * Indicates the score of the player.
 */
public class ScoreIndicator implements Sprite {

    private Sprite shape;
    private Counter score;

    /**
     * constructor.
     * @param shape the shape of the sprite
     * @param score is the score of the player so far
     */
    public ScoreIndicator(Sprite shape, Counter score) {
        this.shape = shape;
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Block b = (Block) this.shape;
        d.setColor(Color.BLACK);
        d.drawText((int) b.getCollisionRectangle().getWidth() / 2 - 35, 16, "Score: " + this.score.getValue(), 20);

    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);

    }

}
