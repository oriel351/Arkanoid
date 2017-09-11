
/**
 * this interface makes menu.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-11
 * @param <T> is the task
 */

package gameplay;

import animations.Animation;


public interface Menu<T> extends Animation {

    /**
     * add an option to the menu.
     * @param key pressed to start the selection.
     * @param message to be shown
     * @param returnVal the T will be returned(task).
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the status(the choice).
     */
    T getStatus();

    /**
     *
     * @param key needed to be pressed to enter current sub menu.
     * @param message text displayed in the main menu.
     * @param subMenu the task
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}
