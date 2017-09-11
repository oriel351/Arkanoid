package objects;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-27
 */

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

import physics.Point;
import physics.Rectangle;
import physics.Velocity;
import level.GameLevel;

/**
 * This class implements a paddle to be played in the game. it's members and
 * behaviors.
 */
public class Paddle implements Sprite, Collidable {

    private KeyboardSensor keyboard;

    private Rectangle shape;

    private java.awt.Color color;

    private double height;

    private double speed;

    private GameLevel game;

    /**
     * constructor.
     * @param shape the rectangle which is the shape of the paddle.
     * @param game the game(level) we are playing.
     * @param speed of movement of paddle.
     * @param color of the paddle.
     * @param keyboard that controls the paddle.
     */
    public Paddle(Rectangle shape, Color color, double speed, GameLevel game, KeyboardSensor keyboard) {

        // initial location and size of the paddle:

        this.shape = shape;
        this.speed = speed;
        this.color = color;
        this.keyboard = keyboard;
        // a reference to the game.
        this.game = game;

    }

    /**
     * this function moves the paddle to the left.
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        // checking it is in the borders of the game
        if (this.shape.getUpperLeft().getX() >= this.game.getBorderSize()) {
            double b = this.speed * dt;

            this.shape =
                    new Rectangle(new Point(this.shape.getUpperLeft().getX() - this.speed * dt, this.shape
                            .getUpperLeft().getY()), this.shape.getWidth(), this.shape.getHeight());
        }
    }

    /**
     * this function moves the paddle to the right.
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {
        // checking it is in the borders of the game
        if (this.shape.getUpperLeft().getX() + this.shape.getWidth() <= this.game.getWidth()
                - this.game.getBorderSize()) {

            double b = this.speed * dt;
            Point p = new Point(this.shape.getUpperLeft().getX() + (this.speed * dt), this.shape.getUpperLeft().getY());

            this.shape = new Rectangle(p, this.shape.getWidth(), this.shape.getHeight());
        }

    }

    /**
     * Notifies the paddle time passed and moves to the propiate direction.
     * @param dt the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {

        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        }

        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * @param surface the surface we are drawing at.
     */
    @Override
    public void drawOn(DrawSurface surface) {

        // drawing the inside of the Paddle.

        surface.setColor(this.color);
        surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());

        // drawing the borders.

        surface.setColor(java.awt.Color.BLACK);
        surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());
    }

    /**
     * @return the rectangle that consist the currently colliding by the ball
     *         object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double leftX = this.getCollisionRectangle().getUpperLeft().getX();

        // dividing the paddle into 5 regions :
        double reg1 = leftX + this.shape.getWidth() / 5;
        double reg2 = reg1 + this.shape.getWidth() / 5;
        double reg3 = reg2 + this.shape.getWidth() / 5;
        double reg4 = reg3 + this.shape.getWidth() / 5;

        // our previous speed:

        double angleRad = Math.abs(Math.atan(currentVelocity.getDy() / currentVelocity.getDx()));
        double prevSpeed = Math.abs(currentVelocity.getDy() / Math.sin(angleRad));

        // the collision point:

        double colX = collisionPoint.getX();

        // we use the same method checking that the collision was on the upper side of the paddle,
        // and then check in which region ( switch)

        int direction = this.shape.sideIntersectPoint(collisionPoint);

        switch (direction) {

        case 0:
            if (colX >= leftX && colX <= reg1) {
                return Velocity.fromAngleAndSpeed(210, prevSpeed);
            }
            if (colX >= reg1 && colX <= reg2) {
                return Velocity.fromAngleAndSpeed(240, prevSpeed);
            }
            if (colX >= reg2 && colX <= reg3) {
                return new Velocity(Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
            }
            if (colX >= reg3 && colX <= reg4) {
                return Velocity.fromAngleAndSpeed(300, prevSpeed);
            }
            if (colX >= reg4 && colX <= leftX + this.shape.getWidth()) {
                return Velocity.fromAngleAndSpeed(330, prevSpeed);
            }
        case 1:
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());

        case 2: // not a possible case ! exception!
            return new Velocity(Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));

        case 3:
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());

        default:
            return null;
        }
    }

    /**
     * Add this paddle to the game.
     * @param g the game we are playing
     */
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

}