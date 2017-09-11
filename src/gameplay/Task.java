package gameplay;

/**
 * this interface creates tasks.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-11
 * @param <T> the task we create
 */
public interface Task<T> {

    /**
     * @return the task that should run at the moment.
     */
    T run();
}
