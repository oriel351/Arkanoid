package gameplay;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-01
 */

import gamedefinitions.LevelSpecificationReader;
import interactions.Counter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import score.HighScoresTable;
import score.ScoreInfo;
import level.GameLevel;
import level.LevelInformation;
import animations.AnimationRunner;
import animations.EndGameAnimation;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;

/**
 * This class is in charge of creating the different levels, and moving from one level to the next.
 */
public class GameFlow {

    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private File file;
    private int gameWidth;
    private int gameHeight;
    private List<LevelInformation> levelList;
    private int startingNumLives;
    private Counter numOfLives;
    private Counter score;
    private HighScoresTable table;

    private static final String MENU_PATH = "images/background/gezer.jpg";
    private static final String SUB_MENU_PATH = "images/background/mountains.jpg";

    /**
     * constructor.
     * @param ar is the animation runner
     * @param ks is the keyboard
     * @param file the file we are taking the levels information from
     * @param width is the game width
     * @param height is the game height
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, File file, int width, int height) {

        this.runner = ar;
        this.keyboard = ks;
        this.file = file;
        this.levelList = new ArrayList<LevelInformation>();
        this.gameWidth = width;
        this.gameHeight = height;
        this.startingNumLives = 7;
        this.numOfLives = new Counter(this.startingNumLives);
        this.score = new Counter(0);
        this.table = getTable();
    }

    /**
     * this function is generating the levels.
     * @param levelsDefinition the string that decides the order of the levels.
     */
    public void generateLevels(String levelsDefinition) {

        Reader br;

        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelsDefinition);

            br = new InputStreamReader(is);

            this.levelList = new LevelSpecificationReader().fromReader(br);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception ( problem with level definitions file)");
        }

    }

    /**
     * running the levels by order of the list.
     */
    public void runSingleGame() {
        int levelCount = 0;
        for (LevelInformation levelInfo : this.levelList) {

            GameLevel level = new GameLevel(this.keyboard, this.runner, levelInfo, this.numOfLives, this.score);

            level.initialize();

            while (level.getRemainingBlocks() != 0 && this.numOfLives.getValue() != 0) {

                level.playOneTurn();

            }

            if (this.numOfLives.getValue() == 0) {
                EndGameAnimation end = new EndGameAnimation(this.score, 0);
                this.runner.run(new KeyPressStoppableAnimation(end, this.keyboard, KeyboardSensor.SPACE_KEY));
                // 36782 authority
            }
            levelCount++;
        } // END OF FOR

        // validating player finished the levels.
        if (levelCount == this.levelList.size() && this.numOfLives.getValue() > 0) {
            EndGameAnimation end = new EndGameAnimation(this.score, 1);
            this.runner.run(new KeyPressStoppableAnimation(end, this.keyboard, KeyboardSensor.SPACE_KEY));
        }

        this.table = highScoreApply();
        HighScoresAnimation high = new HighScoresAnimation(this.table);
        this.runner.run(new KeyPressStoppableAnimation(high, this.keyboard, KeyboardSensor.SPACE_KEY));

        // reseting the score and lives for next game.
        resetGame();
    } // END OF runLevels

    /**
     * getting the high score table.
     * @return the high score table.
     */
    public HighScoresTable getTable() {

        HighScoresTable scores = null;
        if (this.file.exists()) {
            scores = HighScoresTable.loadFromFile(this.file);
        } else {
            try {
                scores = new HighScoresTable(5);
                scores.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return scores;
    }

    /**
     * applying the final score to the high score table.
     * @return the high score table.
     */
    public HighScoresTable highScoreApply() {
        HighScoresTable scores = getTable();

        if (scores.getRank(this.score.getValue()) <= scores.size()) {
            DialogManager dialog = this.runner.getGUI().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");

            scores.add(new ScoreInfo(name, this.score.getValue()));
            try {
                scores.save(this.file);
            } catch (IOException e) {

                System.out.println("IO exception");
            }
        }
        scores.fixList();
        return scores;
    }

    /**
     * this function is reseting all the values to start a new game.
     */
    public void resetGame() {
        // reseting all the values to start a new game.
        this.score.decrease(this.score.getValue());
        this.numOfLives = new Counter(this.startingNumLives);
        this.levelList = new ArrayList<LevelInformation>();
    }

}