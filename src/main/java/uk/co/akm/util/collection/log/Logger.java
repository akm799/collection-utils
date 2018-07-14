package uk.co.akm.util.collection.log;

/**
 * Logger interface that will log errors that occur during collection/stream processing.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface Logger {

    void log(String tag, String error);

    void log(String tag, String error, Exception ex);
}
