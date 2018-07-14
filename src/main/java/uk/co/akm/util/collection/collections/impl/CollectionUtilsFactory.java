package uk.co.akm.util.collection.collections.impl;

import uk.co.akm.util.collection.collections.CollectionUtils;
import uk.co.akm.util.collection.log.Logger;
import uk.co.akm.util.collection.log.impl.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public class CollectionUtilsFactory {
    private static CollectionUtils instance;

    public static CollectionUtils getInstance() {
        if (instance == null) {
            instance = new CollectionUtilsImpl(LoggerFactory.getLoggerInstance());
        }

        return instance;
    }

    CollectionUtilsFactory() {}
}
