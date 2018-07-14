package uk.co.akm.util.collection.log.impl;

import uk.co.akm.util.collection.log.Logger;

/**
 * Created by Thanos Mavroidis on 24/04/2017.
 */
final class SafeLogger implements Logger {
    private final Logger logger;
    private final Logger defaultLogger;

    public SafeLogger(Logger logger, Logger defaultLogger) {
        this.logger = logger;
        this.defaultLogger = defaultLogger;
    }

    public void log(String tag, String error) {
        try {
            logger.log(tag, error);
        } catch (Exception e) {
            defaultLogger.log(tag, error);
        }
    }

    public void log(String tag, String error, Exception ex) {
        try {
            logger.log(tag, error, ex);
        } catch (Exception e) {
            defaultLogger.log(tag, error, ex);
        }
    }
}
