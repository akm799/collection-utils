package uk.co.akm.util.collection.streams.impl.factory.impl;

import uk.co.akm.util.collection.log.Logger;
import uk.co.akm.util.collection.log.impl.LoggerFactory;
import uk.co.akm.util.collection.streams.impl.factory.CollectionFactory;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
public class CollectionFactorySource {
    private static CollectionFactory instance;

    public static CollectionFactory instance() {
        if (instance == null) {
            instance = new CollectionFactoryImpl(LoggerFactory.getLoggerInstance());
        }

        return instance;
    }

    private CollectionFactorySource() {}
}
