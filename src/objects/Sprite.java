package objects;
/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-27
 */
import level.GameLevel;
import biuoop.DrawSurface;
/**
 *
 * this interface defines the behaviour of sprites.
 *
 */
public interface Sprite {

    /**
     * draw the sprite to the screen.
     * @param d the surface that we are drawing the sprite on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);

    /**
     * @param g the game we are playing.
     */
    void addToGame(GameLevel g);
}