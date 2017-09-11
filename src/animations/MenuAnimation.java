/**
 * this interface makes menu.
 * @author Oriel Gaash 
 * @version 1.7
 * @param <T>
 * @since 2015-10-11
 */


package animations;

import gameplay.Menu;
import gameplay.Option;
import gameplay.Task;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import objects.Background;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


public class MenuAnimation<T> implements Menu<T> {

    private List<Option<T>> options;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private T status;
    private boolean stop;
    private java.awt.Color textColor;
    private Background background;

    /**
     * constructor.
     * @param keyboard we are pressing.
     * @param runner the animation.
     * @param path the path of the background.
     * @param color of the text.
     */
    public MenuAnimation(KeyboardSensor keyboard, AnimationRunner runner, String path, Color color) {

        this.options = new ArrayList<Option<T>>();
        this.keyboard = keyboard;
        this.status = null;
        this.stop = false;
        this.textColor = color;
        this.background = new Background("image(" + path + ")"); // the imgParser should receive path in
        this.runner = runner; // 'image(path...)' format
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.background.drawOn(d);
        d.setColor(this.textColor);

        for (int i = 0; i < this.options.size(); i++) {
            d.drawText(d.getWidth() / 2 - 180, 60 + 50 * i, this.options.get(i).getKey() + " - ", 40);
            d.drawText(d.getWidth() / 2 - 130, 60 + 50 * i, this.options.get(i).getMessage(), 40);
        }
        for (int i = 0; i < this.options.size(); i++) {

            if (this.keyboard.isPressed(this.options.get(i).getKey())) {
                this.stop = true;
                this.status = this.options.get(i).getReturnVal();
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        Option<T> op = new Option<T>(key, message, returnVal);
        this.options.add(op);

    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, final Menu<T> subMenu) {

        final AnimationRunner run = this.runner;

        Task<Void> beginGame = new Task<Void>() { // the sub - menu with level sets:

                    @Override
                    public Void run() {
                        runner.run(subMenu);
                        return null;
                    }
                };
        @SuppressWarnings("unchecked")
        Option<T> op = new Option<T>(key, message, (T) beginGame);
        this.options.add(op);

    }

    /**
     * a reset function that reseting a game once it over.
     */
    public void reset() {
        this.stop = false;
        this.status = null;
    }

}
