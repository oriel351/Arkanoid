package gameplay;

/**
 * this interface is in charge of choosing an option from the menu.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-11
 * @param <T> the task
 */
public class Option<T> {

    private String key;
    private String message;
    private T returnVal;

    /**
     * constructor.
     * @param key the key the player pressed.
     * @param message the massage sent.
     * @param returnVal the value that connects as to the chosen task.
     */
    public Option(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * @return the key to press.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the message displayed.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the task returned.
     */
    public T getReturnVal() {
        return this.returnVal;
    }

}
