package uk.co.akm.util.collection.streams.impl;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
final class LinkedHashSetStream extends AbstractStream<LinkedHashSet> {

    LinkedHashSetStream(Collection collection, boolean stopOnError) {
        super(LinkedHashSet.class, collection, stopOnError);
    }
}
