package uk.co.akm.util.collection.collections;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface Predicate<T> {

    /**
     * Returns true if the input argument matches the predicate or false otherwise.
     *
     * @param arg the input argument
     * @return true if the input argument matches the predicate or false otherwise
     */
    boolean test(T arg);
}
