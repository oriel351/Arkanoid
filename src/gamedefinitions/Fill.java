
/**
 * this interface is in charge of the backgroung of the game.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-15
 */

package gamedefinitions;

import biuoop.DrawSurface;

public interface Fill {


    /**
     * this function draws on the game surface the required background/ image.
     * @param surface we are drawing at
     * @param x of uper left
     * @param y of uper left
     * @param width of shape
     * @param height of shape
     */
    void drawInside(DrawSurface surface, int x, int y, int width, int height);
}


