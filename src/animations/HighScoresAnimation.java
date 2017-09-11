/**
 * this class shows the score fro a game.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-10
 */

package animations;

import score.HighScoresTable;
import biuoop.DrawSurface;

/**
 * in charge of presenting the high scores table.
 */
public class HighScoresAnimation implements Animation {

    private boolean stop;
    private HighScoresTable scores;

    /**
     * constructor.
     * @param scores the scores.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.stop = false;
        this.scores = scores;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(java.awt.Color.BLUE);
        d.drawText(d.getWidth() / 2 - 130, 60, "High Scores", 40);
        d.drawText(d.getWidth() / 2 - 250, 130, "Player Name", 30);
        d.drawText(d.getWidth() / 2 + 100, 130, "Score", 30);
        int i;
        for (i = 30; i <= this.scores.getHighScores().size() * 30; i = i + 30) {
            d.drawText(
                    d.getWidth() / 2 - 250,
                    140 + i,
                    String.valueOf(i / 30).concat(")  ")
                            .concat(this.scores.getHighScores().get((i - 30) / 30).getName()), 25);
            d.drawText(d.getWidth() / 2 + 100, 140 + i,
                    String.valueOf(this.scores.getHighScores().get((i - 30) / 30).getScore()), 25);
        }

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}