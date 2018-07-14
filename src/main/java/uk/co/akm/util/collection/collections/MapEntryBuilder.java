package uk.co.akm.util.collection.collections;

import java.util.Map;

/**
 * Special category of transformer that transforms an object into a map entry.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface MapEntryBuilder<T, K, V> {

    /**
     * Transforms the input argument into a map entry.
     *
     * @param arg the object that is to be converted to a map entry
     *
     * @return the map entry result of the transformation
     */
    Map.Entry<K, V> buildMapEntry(T arg);
}
