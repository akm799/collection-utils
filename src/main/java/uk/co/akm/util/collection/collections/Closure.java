package uk.co.akm.util.collection.collections;

/**
 * Represents an operation to be carried out.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface Closure<T> {

    /**
     * Performs the specified operation.
     *
     * @param input the operation input
     */
    void execute(T input);
}
