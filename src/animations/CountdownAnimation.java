package animations;

import physics.SpriteCollection;
import biuoop.DrawSurface;

/**
 * this class makes the animation in the beginning of each level.
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int milliSecPassed;
    private boolean stop;

    /**
     * constructor.
     * @param numOfSeconds time the animation will last.
     * @param countFrom which number to start counting from.
     * @param gameScreen our collection from the game ( the blocks ).
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.milliSecPassed = 0;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        countdownLoop(d);
        this.milliSecPassed += 16;
        if (this.milliSecPassed >= numOfSeconds * 1000) {
            this.stop = true;
        }

    }

    /**
     * we have the interval variable which is the calculation of how long(in milliseconds every number should
     * appear,
     * regarding that we have 60 fps and each time doOneFrom fucntion is called, we treat a single frame.
     * @param d the surface.
     */
    public void countdownLoop(DrawSurface d) {
        double interval = this.numOfSeconds * 1000 / this.countFrom;
        for (int i = 0; i < this.countFrom; i++) {
            if ((this.milliSecPassed >= i * interval) && (this.milliSecPassed <= interval * (i + 1))) {
                d.drawText(400, d.getHeight() / 2, Integer.toString(this.countFrom - i), 46);
            }

        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
