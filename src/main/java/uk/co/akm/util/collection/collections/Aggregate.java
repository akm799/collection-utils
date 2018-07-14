package uk.co.akm.util.collection.collections;

/**
 * Represents an operation with an aggregate result. The aggregate result is formed by performing the operation
 * defined by this closure on all elements of a collection.
 *
 * Created by Thanos Mavroidis on 24/03/2017.
 */
public interface Aggregate<T, R> extends Closure<T> {

    /**
     * Returns the aggregate result result of the operation defined by this closure.
     * The result returned by this method is formed by performing the operation
     * defined by this closure on all elements of a collection.
     *
     * @return the aggregate result result of the operation defined by this closure
     */
    R getResult();
}
