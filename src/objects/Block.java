package objects;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */

import biuoop.DrawSurface;
import gamedefinitions.Fill;
import interactions.HitListener;
import interactions.HitNotifier;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import physics.Point;
import physics.Rectangle;
import physics.Velocity;
import level.GameLevel;

/**
 * class Block. defines the members and behaviors of a block in the game.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle shape;
    private int hits;
    private Map<Integer, Fill> fills; // background of the rectangle.
    private Color stroke;
    private Color borderColor;
    private List<HitListener> hitListeners;

    /**
     * constructor.use only for Block border no need for hits..
     * @param x of the block
     * @param width of the block
     * @param height of the block
     * @param color of the block.
     * @param y is the y coordinate of the point of the block
     */
    public Block(double x, double y, double width, double height, Color color) {

        this.shape = (new Rectangle(new Point(x, y), width, height));
        this.hits = 0;
        this.fills = null;
        this.stroke = null; // if equals null, then we just don't draw it.

        this.borderColor = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * second constructor. builds a new block in the game with x,y coordinates instead of point.
     * @param width of the block
     * @param height of the block
     * @param hits the number of hits initializing.
     * @param upperLeft the upper left corner of the rectangle.
     * @param fills the background.
     * @param stroke the background/color of the block after it gets hit for the last time.
     */
    public Block(Point upperLeft, double width, double height, int hits, Map<Integer, Fill> fills, Color stroke) {
        this.shape = (new Rectangle(upperLeft, width, height));
        this.hits = hits;
        this.fills = fills;
        this.stroke = stroke; // if equals null, then we just don't draw it.
        this.borderColor = null;
        this.hitListeners = new ArrayList<HitListener>();

    }

    /**
     * @return the shape of the object (always rectangle in this game).
     */

    @Override
    public Rectangle getCollisionRectangle() {

        return this.shape;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        int direction = this.shape.sideIntersectPoint(collisionPoint);

        this.notifyHit(hitter);

        switch (direction) {

        case 0:
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));

        case 1:
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());

        case 2:
            return new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));

        case 3:
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());

        default: // this case shouldn't happen , but if it does - exception.
            return null;
        }
    }

    /**
     * @return the current number of hits that the block was hit by the ball.
     */
    public int getHits() {
        return this.hits;
    }

    /**
     * @param newHit the new state of number of hits of the current block by the ball.
     */
    public void setHits(int newHit) {
        this.hits = newHit;
    }

    /**
     * @param surface GUI which we are drawing on.
     */

    @Override
    public void drawOn(DrawSurface surface) {

        // drawing the inside of the block.

        if (this.fills != null) { // if its a game block.
            Fill bg = this.fills.get(this.hits);
            bg.drawInside(surface, (int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());
        } else { // its a border
            // drawing the inside of the block.

            surface.setColor(this.borderColor);
            surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());
        }

        // drawing the borders.
        if (this.stroke != null) {
            surface.setColor(java.awt.Color.BLACK);
            surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());
        }

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * @param g the game we are removing from the block.
     * @return the game we are removing from the block
     */
    public boolean removeFromGame(GameLevel g) {
        // we want to be sure that it succeeded
        return g.removeCollidable(this) && g.removeSprite(this);
    }

    /**
     * for now, does nothing.
     * @param dt the the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * whenever a hit occurs, notifiers all of the registered HitListener objects by calling their hitEvent method.
     * @param hitter is the ball that hit the object
     */

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }

    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @return list of the listeners.
     */
    public List<HitListener> getHitListeners() {
        return hitListeners;
    }

}
