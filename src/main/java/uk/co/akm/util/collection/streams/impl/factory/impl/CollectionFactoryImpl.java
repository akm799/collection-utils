package uk.co.akm.util.collection.streams.impl.factory.impl;

import uk.co.akm.util.collection.log.Logger;
import uk.co.akm.util.collection.streams.impl.factory.CollectionFactory;

import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
final class CollectionFactoryImpl implements CollectionFactory {
    private static final String TAG = CollectionFactoryImpl.class.getSimpleName();

    private final Logger logger;

    CollectionFactoryImpl(Logger logger) {
        this.logger = logger;
    }

    public <C extends Collection> C newInstance(Class<C> clazz, int size) {
        if (clazz == null || size < 0) {
            logger.log(TAG, "Illegal argument(s) (clazz=" + clazz + ", size=" + size + "). The class argument cannot be null and the int argument cannot be negative.");

            return null;
        }

        final C preSizedCollection = invokeSizeConstructor(clazz, size);

        return (preSizedCollection == null ? invokeDefaultConstructor(clazz) : preSizedCollection);
    }

    private <C extends Collection> C invokeSizeConstructor(Class<C> clazz, int size) {
        final Constructor<C> sizeConstructor = findSizeConstructor(clazz);
        if (sizeConstructor == null) {
            return null;
        }

        try {
            return sizeConstructor.newInstance(size);
        } catch (Exception e) {
            logger.log(TAG, "Error when invoking single int argument constructor of collection class " + clazz, e);
            return null;
        }
    }

    private <C extends Collection> Constructor<C> findSizeConstructor(Class<C> clazz) {
        try {
            return clazz.getConstructor(int.class);
        } catch (Exception e) {
            logger.log(TAG, "Could not find suitable single int argument constructor for collection class " + clazz, e);
            return null;
        }
    }

    private <C extends Collection> C invokeDefaultConstructor(Class<C> clazz) {
        try {
            return clazz.getConstructor(null).newInstance(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
