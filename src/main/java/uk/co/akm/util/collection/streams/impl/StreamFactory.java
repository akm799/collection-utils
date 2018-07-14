package uk.co.akm.util.collection.streams.impl;

import uk.co.akm.util.collection.streams.Stream;

import java.util.Collection;

/**
 * Provides instances of streams backed by lists or sets.
 *
 * Created by Thanos Mavroidis on 27/03/2017.
 */
public class StreamFactory {
    /**
     * Returns a #Stream implementation that uses an #ArrayList as the underlying stream collection.
     *
     * @param list the collection whose elements will be contained in the initial collection
     *             of this stream. Please note that initial stream collection will be an #ArrayList
     *             instance, regardless of the input collection type.
     * @param stopOnError if set to false all exceptions during processing are ignored and if set to
     *                    true processing stops if an exception occurs and the latter wrapped in a
     *                    #RuntimeException and re-thrown
     *
     * @return a #Stream implementation that uses an #ArrayList as the underlying stream collection
     */
    public static Stream listStreamInstance(Collection list, boolean stopOnError) {
        return new ArrayListStream(list, stopOnError);
    }

    /**
     * Returns a #Stream implementation that uses an #ArrayList as the underlying stream collection.
     *
     * @param set the collection whose elements will be contained in the initial collection
     *            of this stream. Please note that initial stream collection will be an #LinkedHashSet
     *            instance, regardless of the input collection type.
     * @param stopOnError if set to false all exceptions during processing are ignored and if set to
     *                    true processing stops if an exception occurs and the latter wrapped in a
     *                    #RuntimeException and re-thrown
     *
     * @return a #Stream implementation that uses an #ArrayList as the underlying stream collection
     */
    public static Stream setStreamInstance(Collection set, boolean stopOnError) {
        return new LinkedHashSetStream(set, stopOnError);
    }

    private StreamFactory() {}
}
