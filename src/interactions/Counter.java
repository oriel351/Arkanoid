package interactions;

/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-08-10
 */

/**
 * Counter is a simple class that is used for counting things.
 */
public class Counter {

    private int value;

    /**
     * Constructor.
     * @param value the number of Blocks remained.
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * add number to current count.
     * @param number is the given number.
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * subtract number from current count.
     * @param number is the given number.
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * get current count.
     * @return the value
     */
    public int getValue() {
        return this.value;
    }
}