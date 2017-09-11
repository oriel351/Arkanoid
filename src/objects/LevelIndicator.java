package objects;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-09-27
 */

import java.awt.Color;

import level.GameLevel;
import biuoop.DrawSurface;

/**
 * Indicates the name of the level we are currently playing.
 */
public class LevelIndicator implements Sprite {

    private Sprite shape;
    private String level;

    /**
     * constructor.
     * @param shape the shape of the sprite
     * @param level is the level
     */
    public LevelIndicator(Sprite shape, String level) {
        this.shape = shape;
        this.level = level;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Block b = (Block) this.shape;
        d.setColor(Color.BLACK);
        d.drawText((int) b.getCollisionRectangle().getWidth() - 200, 16, "Level: " + this.level, 20);

    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);

    }

}
