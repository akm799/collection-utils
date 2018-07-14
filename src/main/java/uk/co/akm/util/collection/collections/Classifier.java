package uk.co.akm.util.collection.collections;

import java.util.Comparator;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public interface Classifier<T, Object> {

    Transformer<T, Object> categoryExtractor();

    boolean hasOrderDefinition();

    Comparator<Object> orderDefinition();
}
