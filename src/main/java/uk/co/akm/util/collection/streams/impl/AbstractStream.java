package uk.co.akm.util.collection.streams.impl;

import uk.co.akm.util.collection.collections.Closure;
import uk.co.akm.util.collection.collections.CollectionUtils;
import uk.co.akm.util.collection.collections.Predicate;
import uk.co.akm.util.collection.collections.Transformer;
import uk.co.akm.util.collection.collections.impl.CollectionUtilsFactory;
import uk.co.akm.util.collection.streams.Stream;
import uk.co.akm.util.collection.streams.StreamAware;
import uk.co.akm.util.collection.streams.impl.factory.CollectionFactory;
import uk.co.akm.util.collection.streams.impl.factory.impl.CollectionFactorySource;

import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 24/03/2017.
 */
abstract class AbstractStream <C extends Collection> implements Stream {
    private Collection collection;
    private final Class<C> collectionClass;
    private final boolean stopOnError;

    private final CollectionUtils collectionUtils = CollectionUtilsFactory.getInstance();
    private final CollectionFactory collectionFactory = CollectionFactorySource.instance();

    /**
     * @param collectionClass the class of the underlying collection of this stream
     * @param collection the collection whose elements will be contained in the initial collection
     *                   of this stream. Please note that this input collection will not necessarily
     *                   be of the same type of the initial stream collection.
     */
    AbstractStream(Class<C> collectionClass, Collection collection, boolean stopOnError) {
        if (collection == null) {
            throw new IllegalArgumentException("Input collection cannot be null.");
        }

        this.stopOnError = stopOnError;
        this.collectionClass = collectionClass;
        this.collection = copy(collection); // Use a copy of the input collection, rather than the original.
    }

    public final C getCollection() {
        return collectionClass.cast(collection);
    }

    public final Stream filter(Predicate include) {
        if (include != null) {
            initOperatorIfRequired(include);
            final Collection filtered = newInstance(collection.size());
            collectionUtils.filter(collection, include, filtered, stopOnError);
            this.collection = filtered;
        }

        return this;
    }

    public final Stream operate(Closure closure) {
        if (closure != null) {
            initOperatorIfRequired(closure);
            collectionUtils.operate(collection, closure, stopOnError);
        }

        return this;
    }

    public final Stream transform(Transformer transformer) {
        if (transformer != null) {
            initOperatorIfRequired(transformer);
            final Collection transformed = newInstance(collection.size());
            collectionUtils.transform(collection, transformer, transformed, stopOnError);
            this.collection = transformed;
        }

        return this;
    }

    private void initOperatorIfRequired(Object operator) {
        if (operator instanceof StreamAware) {
            initOperator((StreamAware)operator);
        }
    }

    private void initOperator(StreamAware operator) {
        final Collection copy = copy(collection);
        operator.init(copy); // Use a copy of the collection that will be processed, so we do not interfere with such processing.
    }

    private Collection copy(Collection collection) {
        final Collection copy = newInstance(collection.size());
        copy.addAll(collection);

        return copy;
    }

    private Collection newInstance(int size) {
        return collectionFactory.newInstance(collectionClass, size);
    }
}
