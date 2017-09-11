package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Creates and manages the high score table.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-11
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * constructor.
     * @param animation the animation that runs right now.
     * @param sensor the keyboard sensor.
     * @param key the key pressed.
     */
    public KeyPressStoppableAnimation(Animation animation, KeyboardSensor sensor, String key) {
        this.keyboard = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        this.animation.doOneFrame(d, dt); // showing the current animation
        if (this.animation instanceof HighScoresAnimation) {
            d.drawText(10, (int) (d.getHeight() / 1.5), "Press ".concat(this.key).concat(" to continue"), 32);
        } else {
            d.drawText(10, d.getHeight() / 2, "Press ".concat(this.key).concat(" to continue"), 32);
        }

        if (this.keyboard.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }

}