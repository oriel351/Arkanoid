package gameplay;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */

import gamedefinitions.MenusGenerator;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import animations.AnimationRunner;
import animations.MenuAnimation;
import biuoop.GUI;

/**
 * This class is containing the main method that runs the game.
 */
public class Ass6Game {

    /**
     * the tester program main.
     * @param args ignored
     */
    private static final int GUI_WIDTH = 800;
    private static final int GUI_HEIGHT = 600;

    private static File file = new File("HighScores");

    /**
     * @param args is the string that determines the levels order.
     */
    public static void main(String[] args) {
        // for now, might change

        int width = GUI_WIDTH;
        int height = GUI_HEIGHT;
        GUI gui = new GUI("Arkanoid", width, height);
        AnimationRunner runner = new AnimationRunner(gui, 60);
        biuoop.KeyboardSensor key = gui.getKeyboardSensor();

        GameFlow game = new GameFlow(runner, key, file, width, height);
        String levelSet;
        if (args.length == 0) {
            levelSet = "definitions/sets/default_level_sets.txt";
        } else {
            // ** in case we get a wrong derectory of level set file.
            levelSet = args[0];
            Reader br = null;
            LineNumberReader input = null;
            // CREATING SUB - MENU :
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSet);

                br = new InputStreamReader(is);
            } catch (NullPointerException e) {
                System.out.println("Null Pointer Exception(wrong path - playing with default)");
                levelSet = "definitions/sets/default_level_sets.txt";
            }

        }
        MenusGenerator gameMenu = new MenusGenerator(game, runner, key, levelSet);

        MenuAnimation<Task<Void>> menu = gameMenu.getMenu();
        MenuAnimation<Task<Void>> subMenu = gameMenu.getSubMenu();

        play(runner, menu, subMenu);

        gui.close();

    }

    /**
     * starts to play the game.
     * @param runner incharge of animations.
     * @param menu incharge of menus.
     * @param subMenu incharge of subMenus.
     */
    public static void play(AnimationRunner runner, MenuAnimation<Task<Void>> menu, MenuAnimation<Task<Void>> subMenu) {
        while (true) {
            runner.run(menu);
            // A menu with the selections is displayed.
            // Runs until user presses one of the options.

            Task<Void> v = menu.getStatus();
            if (v != null) {
                v.run();
            }
            menu.reset();
            v = subMenu.getStatus();
            if (v != null) {
                v.run();
            }

            subMenu.reset();

        }
    }

}
