package uk.co.akm.util.collection.streams.impl.factory;

import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
public interface CollectionFactory {

    <C extends Collection> C newInstance(Class<C> clazz, int size);
}
