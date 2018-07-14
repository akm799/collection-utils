package uk.co.akm.util.collection.streams;

import uk.co.akm.util.collection.collections.Closure;
import uk.co.akm.util.collection.collections.Predicate;
import uk.co.akm.util.collection.collections.Transformer;

import java.util.Collection;

/**
 * Implementations of this interface hold an underlying collection which can be transformed in
 * various ways using methods defined in this interface. At each stage, the user can obtain the
 * underlying collection at its current state.
 *
 * @param <C> the type of the underlying collection held by the stream
 *
 * Created by Thanos Mavroidis on 24/03/2017.
 */
public interface Stream<C extends Collection> {

    /**
     * Returns the collection reflecting the current stream state.
     *
     * @return the collection reflecting the current stream state
     */
    C getCollection();

    /**
     * Filters the stream collection according to the input predicate.
     *
     * @param include the predicate specifying elements that will pass the filter
     * @return this stream
     */
    Stream<C> filter(Predicate include);

    /**
     * Operates on the elements of the stream collection using the input closure.
     *
     * @param closure the closure that operates on the collection elements
     * @return this stream
     */
    Stream<C> operate(Closure closure);

    /**
     * Transforms the stream collection into another collection of elements of a (usually)
     * different type.
     *
     * @param transformer the transformer used to transform the elements of the stream collection
     * @return this stream
     */
    Stream<C> transform(Transformer transformer);
}
