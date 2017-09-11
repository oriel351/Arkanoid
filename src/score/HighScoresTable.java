package score;


/**
 * Creates and manages the high score table.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-10
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighScoresTable implements Serializable {

    private List<ScoreInfo> list;
    private int numOfScores;

    /**
     * constructor.
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size the size of the high score table.
     */
    public HighScoresTable(int size) {
        this.list = new ArrayList<ScoreInfo>();
        this.numOfScores = size;
    }

    /**
     * Add a high-score.
     * @param score to add.
     */
    public void add(ScoreInfo score) {
        int i;
        for (i = 0; i < this.list.size(); i++) {
            if (score.getScore() >= this.list.get(i).getScore()) {
                break;
            }
        }
        this.list.add(i, score);
        fixList();

    }

    /**
     * @return table size.
     */
    public int size() {
        return this.numOfScores;
    }

    /**
     * The list is sorted such that the highest scores come first.
     * @return the current high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.list;
    }

    /**
     * getting the rank of the player.
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     * @param score the player final score
     * @return the rank of the current score and where will it be on the list if added.
     */
    public int getRank(int score) {

        int i;
        for (i = 0; i < this.list.size(); i++) {
            if (score >= this.list.get(i).getScore()) {
                return i + 1; // the index 0 means location 1, and so on,
            }
        }
        return this.list.size() + 1; // if we iterated all the list and none is lower, it will be last one.
    }

    /**
     * this function makes sure there is no more fields of scores that allowed (size of table).
     */
    public void fixList() {
        while (this.list.size() > this.numOfScores) {
            this.list.remove(this.list.size() - 1);
        }
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename the name of the file.
     * @throws IOException if anything goes wrong.
     */
    public void load(File filename) throws IOException {

        this.clear();
        ObjectInputStream input = null;
        HighScoresTable newScore = null;

        try {
            input = new ObjectInputStream(new FileInputStream(filename));
            try {
                newScore = (HighScoresTable) input.readObject();
                this.list = new ArrayList<ScoreInfo>(newScore.list);
                this.numOfScores = newScore.size();

            } catch (ClassNotFoundException e) {
                throw new IOException("there was some problem reading the object");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file was not found");
        }

    }

    /**
     * Save table data to the specified file.
     * @param filename the name of the file.
     * @throws IOException if something went wrong.
     */
    public void save(File filename) throws IOException {

        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(filename.getName()));
            output.writeObject(this);
        } catch (IOException e) {
            throw new IOException("Failed saving object");
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                System.out.println("problem closing high score file");
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     * @param filename the name of the file.
     * @return a list of high scores from a file.
     */
    public static HighScoresTable loadFromFile(File filename) {

        ObjectInputStream input = null;
        HighScoresTable newScore = null;

        try {
            input = new ObjectInputStream(new FileInputStream(filename));
            try {
                newScore = (HighScoresTable) input.readObject();
                return newScore;

            } catch (ClassNotFoundException e) {
                System.out.println("there was some problem reading the object");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        } catch (IOException e1) {
            System.out.println("there was some IO problem.");
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                System.out.println("could not close the high score table file");
            }
        }
        return new HighScoresTable(100);
    }

}