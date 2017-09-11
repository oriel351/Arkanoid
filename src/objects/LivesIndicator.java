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
 *
 * Indicates how many lives does the player has.
 *
 */
public class LivesIndicator implements Sprite {

    private Sprite shape;
    private Counter lives;

    /**
     * constructor.
     * @param shape the shape of the sprite
     * @param lives is the number of lives the player has.
     */
    public LivesIndicator(Sprite shape, Counter lives) {
        this.shape = shape;
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Block b = (Block) this.shape;
        d.setColor(Color.BLACK);
        d.drawText((int) b.getCollisionRectangle().getWidth() / 5 - 7, 16, "Lives: " + this.lives.getValue(), 20);

    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);

    }

}
