package physics;
/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-27
 */

import java.util.List;
import java.util.ArrayList;

import objects.Sprite;
import biuoop.DrawSurface;

/**
 * Class spriteCollection.
 */
public class SpriteCollection {

    private List gameObjects;

    /**
     * constructor. builds the list of sprites.
     */
    public SpriteCollection() {

        this.gameObjects = new ArrayList();
    }

    /**
     * @param s the sprite object, add it to the list.
     */
    public void addSprite(Sprite s) {

        this.gameObjects.add(s);

    }

    /**
     * @param s the sprite object we want to remove.
     * @return the sprite object that we want to remove.
     */
    public boolean removeCollidable(Sprite s) {
        return this.gameObjects.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt the the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> spc = new ArrayList<Sprite>(this.gameObjects);

        for (Sprite sp : spc) {
            sp.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d the surface which we are drawing in
     */
    public void drawAllOn(DrawSurface d) {

        for (int i = 0; i < this.gameObjects.size(); i++) {
            Sprite b = (Sprite) this.gameObjects.get(i);
                b.drawOn(d);
            }

        }
    }

