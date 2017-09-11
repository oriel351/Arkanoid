/**
 * this class hold the information for a single game's score.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-10
 */

package score;

import java.io.Serializable;

/**
 * Information about the highest scores of players in the game.
 */
public class ScoreInfo implements Serializable {

    private String name;
    private int score;

    /**
     * constructor.
     * @param name the name of the player.
     * @param score the final score of the player.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;

    }

    /**
     * @return the player name.
     */
    public String getName() {
        return this.name;

    }

    /**
     * @return the player's score.
     */
    public int getScore() {
        return this.score;

    }

}