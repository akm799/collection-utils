package uk.co.akm.util.collection.streams.impl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
final class ArrayListStream extends AbstractStream<ArrayList> {

    ArrayListStream(Collection collection, boolean stopOnError) {
        super(ArrayList.class, collection, stopOnError);
    }
}
