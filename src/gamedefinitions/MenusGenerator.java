
/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-24
 */

package gamedefinitions;

import gameplay.GameFlow;
import gameplay.Option;
import gameplay.Task;

import java.awt.Color;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import animations.MenuAnimation;
import biuoop.KeyboardSensor;

public class MenusGenerator {

    private static final String MENU_PATH = "images/background/gezer.jpg";
    private static final String SUB_MENU_PATH = "images/background/mountains.jpg";

    private final String levelSet;
    private final GameFlow game;
    private final KeyboardSensor keyboard;
    private final AnimationRunner runner;

    private MenuAnimation<Task<Void>> mainMenu;
    private MenuAnimation<Task<Void>> subMenu;

    /**
     * constructor.
     * @param game the game flow we are playing.
     * @param runner the animation.
     * @param keyboard we are pressing.
     * @param levelSet path of the levels set file.
     */
    public MenusGenerator(GameFlow game, AnimationRunner runner, KeyboardSensor keyboard, String levelSet) {
        this.levelSet = levelSet;
        this.game = game;
        this.keyboard = keyboard;
        this.runner = runner;
        this.subMenu = generateSubMenu();

        this.mainMenu = generateMainMenu(this.subMenu);
    }


    /**
     * this function generates the sub menu.
     * @return the sub menu.
     */
    private MenuAnimation<Task<Void>> generateSubMenu() {

        Reader br = null;
        LineNumberReader input = null;
        // CREATING SUB - MENU :
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.levelSet);

            br = new InputStreamReader(is);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception(Sub - Menu)");
        }

        MenuAnimation<Task<Void>> subMenu2 =
                new MenuAnimation<Task<Void>>(this.keyboard, this.runner, SUB_MENU_PATH, Color.RED);
        final List<Option<String>> sub = LevelSetsReader.fromReader(br);
        for (int i = 0; i < sub.size(); i++) {
            final String levelDef = sub.get(i).getReturnVal();

            Task<Void> option = new Task<Void>() { // Create a new task (new game).
                        @Override
                        public Void run() {
                            game.generateLevels(levelDef);
                            game.runSingleGame();
                            return null;
                        }
                    };
            subMenu2.addSelection(sub.get(i).getKey(), sub.get(i).getMessage(), option);
        }
        return subMenu2;

    }

    /**
     * this method generates the main menu's options.
     * @param subMenu1 we are defining(main).
     * @return the main menu.
     */
    private MenuAnimation<Task<Void>> generateMainMenu(final MenuAnimation<Task<Void>> subMenu1) {

        MenuAnimation<Task<Void>> menu =
                new MenuAnimation<Task<Void>>(this.keyboard, this.runner, MENU_PATH, Color.BLUE);
        Task<Void> quit = new Task<Void>() { // Create a new task ( quit task).
                    @Override
                    public Void run() {
                        System.exit(1);
                        return null;
                    }
                };
        Task<Void> highScoresTask = new Task<Void>() { // create a high score task.
                    @Override
                    public Void run() {
                        HighScoresAnimation high = new HighScoresAnimation(game.getTable());
                        runner.run(new KeyPressStoppableAnimation(high, keyboard, KeyboardSensor.SPACE_KEY));
                        return null;
                    }
                };

        menu.addSubMenu("s", "Start a new  Game", subMenu);
        menu.addSelection("h", "see high scores", highScoresTask);
        menu.addSelection("q", "quit", quit);

        return menu;
    }

    /**
     *
     * @return the menu.
     */
    public MenuAnimation<Task<Void>> getMenu() {
        return this.mainMenu;
    }

    /**
     *
     * @return the sub menu.
     */
    public MenuAnimation<Task<Void>> getSubMenu() {
        return this.subMenu;
    }

}
