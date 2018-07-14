package uk.co.akm.util.collection.log.impl;

import uk.co.akm.util.collection.log.Logger;

/**
 * Factory to obtain #Logger instances. The default #Logger instance will simply log errors on the system console.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public class LoggerFactory {
    private static final Logger DEFAULT_LOGGER = new ConsoleLogger();

    private static Logger logger;

    public static Logger getLoggerInstance() {
        return (logger == null ? DEFAULT_LOGGER : logger);
    }

    /**
     * Sets a #Logger implementation to be used when logging errors. Please note that null input values are ignored.
     *
     * @param inputLogger the #Logger implementation to use
     */
    public static void setLoggerInstance(Logger inputLogger) {
        logger = inputLogger;
    }

    private LoggerFactory() {}
}
