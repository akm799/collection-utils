package uk.co.akm.util.collection.collections;

/**
 * Transformer interface that transforms one type of object into another.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface Transformer<I, O> {

    /**
     * Returns the result of the transformations of the input argument
     *
     * @param input the object to be transformed
     * @return the result of the transformation
     */
    O transform(I input);
}
