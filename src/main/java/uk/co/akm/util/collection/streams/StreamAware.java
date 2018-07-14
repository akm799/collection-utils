package uk.co.akm.util.collection.streams;

import java.util.Collection;

/**
 * This interface should be implemented by every operator that will act on the stream (filter,
 * modifier, etc) that needs to be aware of the collection held in the stream for its processing.
 * Before the operator is used in the stream, the latter will pass to the former a copy of the
 * collection that is held, at that moment, in the stream.
 *
 * @param <T> the type of the element in the underlying collection of the stream
 *
 * Created by Thanos Mavroidis on 24/03/2017.
 */
public interface StreamAware<T> {

    /**
     * Initializes the operator (implementing this interface) with a copy of the collection that is
     * currently held in the stream.
     *
     * @param collection a copy of the collection that is currently held in the stream
     */
    void init(Collection<T> collection);
}
