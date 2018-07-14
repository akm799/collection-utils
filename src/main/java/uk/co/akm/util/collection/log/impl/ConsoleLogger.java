package uk.co.akm.util.collection.log.impl;

import uk.co.akm.util.collection.log.Logger;

/**
 * Created by Thanos Mavroidis on 23/03/2017.
 */
final class ConsoleLogger implements Logger {

    ConsoleLogger() {}

    public void log(String tag, String error) {
        log(buildTagPrefixedError(tag, error));
    }

    public void log(String tag, String error, Exception ex) {
        log(buildTagPrefixedError(tag, error), ex);
    }

    private void log(String error) {
        System.err.println(error);
    }

    private void log(String error, Exception ex) {
        log(error);
        if (ex != null) {
            ex.printStackTrace(System.err);
        }
    }

    private String buildTagPrefixedError(String tag, String error) {
        if (tag == null) {
            return error;
        } else {
            return (tag + ": " + error);
        }
    }
}
