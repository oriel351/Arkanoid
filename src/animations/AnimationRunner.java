package animations;

/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-09-27
 */
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * This class is in charge of running the animation.
 */
public class AnimationRunner {

    private GUI gui;
    private int framesPerSecond;
    private double dt;
    private Sleeper sleeper;

    /**
     * Constructor.
     * @param gui is the gui.
     * @param nFramesPerSecond is the number of frames per second.
     */
    public AnimationRunner(GUI gui, int nFramesPerSecond) {

        this.gui = gui;
        this.sleeper = new biuoop.Sleeper();
        this.framesPerSecond = nFramesPerSecond;
        this.dt = 1.0 / (double) nFramesPerSecond;
    }

    /**
     * this function makes the animation of the game.
     * @param animation is the animation.
     */
    public void run(Animation animation) {

        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d, this.dt);
            this.gui.show(d);

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;

            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep); // sleeper
            }
        }
    }

    /**
     * @return the frames per second member.
     */
    public double getDt() {
        return this.dt;
    }
  /**
   *
   * @return the gui of the game.
   */
    public GUI getGUI() {
        return this.gui;
    }

}
