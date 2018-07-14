package uk.co.akm.util.collection.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * Useful functions on collections.
 *
 * Created by Thanos Mavroidis on 23/03/2017.
 */
public interface CollectionUtils {

    /**
     * Returns true if the input collection is not null and it contains at least one element, or false otherwise.
     *
     * @param collection the collection to be tested
     * @return true if the input collection is not null and it contains at least one element, or false otherwise
     */
    boolean isNeitherNullNorEmpty(Collection collection);

    /**
     * Returns true if the input collection is either null or empty, or false otherwise.
     *
     * @param collection the collection to be tested
     * @return true if the input collection is either null or empty, or false otherwise
     */
    boolean isNullOrEmpty(Collection collection);

    /**
     * Returns the 1st element encountered in the input iterable that matches the input predicate,
     * or null if no such element is encountered.
     *
     * @param iterable the iterable in which we want to find a particular element
     * @param matcher the predicate used to find the element in the iterable
     * @param stopOnError if set to true the 1st exception that occurs during the predicate evaluation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the iterable element type
     * @return the 1st element encountered in the input iterable that matches the input predicate,
     * or null if no such element is encountered
     */
    <T> T findFirst(Iterable<T> iterable, Predicate<T> matcher, boolean stopOnError);

    /**
     * Returns the only element in the input iterable that matches the input predicate, or null if no such element exists.
     * If a 2nd element that matches the input predicate is encountered, then an @IllegalStateException will be thrown.
     *
     * @param iterable the iterable in which we want to find a particular element
     * @param matcher the predicate used to find the element in the iterable
     * @param stopOnError if set to true the 1st exception that occurs during the predicate evaluation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the iterable element type
     * @return the 1st element encountered in the input iterable that matches the input predicate,
     * or null if no such element is encountered
     * @throws IllegalStateException if more than one elements in the input iterable match the input predicate
     */
    <T> T findUnique(Iterable<T> iterable, Predicate<T> matcher, boolean stopOnError) throws IllegalStateException;

    /**
     * Adds all elements of the first iterable that pass the input predicate to the input collection.
     *
     * @param iterable the iterable that is to be filtered
     * @param include the filter that selects elements
     * @param filtered the collection containing the filtered elements
     * @param stopOnError if set to true the 1st exception that occurs during the predicate evaluation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the iterable element type
     */
    <T> void filter(Iterable<T> iterable, Predicate<T> include, Collection<T> filtered, boolean stopOnError);

    /**
     * Operates on the elements of the input iterable with the input closure. Typically, this method will be used to
     * modify mutable elements of the input iterable. However, the specified closure action some independent action
     * that does not modify any elements.
     *
     * @param iterable the iterable whose elements are to be operated upon
     * @param closure the closure to be executed on all elements of the input iterable
     * @param stopOnError if set to true the 1st exception that occurs during the closure execution will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the iterable element type
     */
    <T> void operate(Iterable<T> iterable, Closure<T> closure, boolean stopOnError);

    /**
     * Transforms all the elements of type E of the first iterable into elements of type T and then
     * adds all the new elements to the input collection. The transformation is accomplished by using
     * the input transformer. Input elements that transform to a null value, should not be added to
     * the output collection.
     *
     * @param iterable the iterable that is to be transformed
     * @param transformer the transformer used to transform the elements
     * @param transformed the collection that will hold the transformed elements
     * @param stopOnError if set to true the 1st exception that occurs during the transformer operation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <I> the type of elements that are to be transformed
     * @param <O> the type of elements resulting from the transformation
     */
    <I, O> void transform(Iterable<I> iterable, Transformer<I, O> transformer, Collection<O> transformed, boolean stopOnError);

    /**
     * Transforms all elements if type E in the input iterable to map entries and then adds all
     * such entries to the input map. The transform of elements to map entries is achieved by using
     * the input map entry builder. Input elements that transform to a null map-entry, cannot be
     * added to the output map.
     *
     * @param iterable the iterable that will be transformed to a map
     * @param entryBuilder the map entry builder used to transform the iterable elements to map entries
     * @param map the map which will contain the transformed elements
     * @param stopOnError if set to true the 1st exception that occurs during the entry-builder operation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the type of the elements in the iterable
     * @param <K> the type of keys in the resulting map
     * @param <V> the type of values in the resulting map
     */
    <T, K, V> void transform(Iterable<T> iterable, MapEntryBuilder<T, K, V> entryBuilder, Map<K, V> map, boolean stopOnError);

    /**
     * Groups all elements of type T in the input iterable into subgroups each sharing the same category of type K. The
     * input classifier extracts the category (of type K) from every element of type T. The groups are added to the
     * supplied, empty, map. Null categories returned by the classifier will be ignored. Thus, any elements for which
     * the classifier returns a null category will not be grouped.
     *
     * @param iterable the iterable whose elements will be grouped
     * @param classifier the transformer which will extract the classification category from each element
     * @param groups the map containing all the grouped elements
     * @param stopOnError if set to true the 1st exception that occurs during the transformer category-extracting operation
     *                    will be wrapped as a #RuntimeException and re-thrown or it set to false all such exceptions will
     *                    be ignored
     * @param <T> the type of the elements being grouped
     * @param <K> the type of the category by which the input elements are grouped
     */
    <T, K> void group(Iterable<T> iterable, Transformer<T, K> classifier, Map<K, Collection<T>> groups, boolean stopOnError);

    <T> Group<T> group(Collection<T> collection, Comparator<T> elementOrder, Classifier<T, Object>... classifiers);

    /**
     * Returns the count of elements in the input iterable that pass the filter specified by the
     * input predicate.
     *
     * @param iterable the iterable which elements are to be conditionally counted
     * @param predicate  the filter which must be satisfied by an element in order for the latter to be counted
     * @param stopOnError if set to true the 1st exception that occurs during the predicate evaluation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T>        the type of elements in the iterable.
     * @return the count of elements in the input iterable that pass the filter specified by the
     * input predicate
     */
    <T> int countElements(Iterable<T> iterable, Predicate<T> predicate, boolean stopOnError);

    /**
     * Splits the iterable presented in the first arguments into two separate collections according
     * to the input predicate.
     *
     * @param iterable the iterable to be split into two parts
     * @param predicate the predicate used to split the iterable into two parts
     * @param predicateTrue the collection that will contain all iterable elements that pass the predicate filter
     * @param predicateFalse the collection that will contain all iterable elements that fail the predicate filter
     * @param stopOnError if set to true the 1st exception that occurs during the predicate evaluation will be wrapped
     *                    as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the type of elements in the iterable
     */
    <T> void split(Iterable<T> iterable, Predicate<T> predicate, Collection<T> predicateTrue, Collection<T> predicateFalse, boolean stopOnError);

    /**
     * Reduces the elements of the input iterable to a single object. Example of a reduction operation is the sum of a
     * collection of integers. In this case, the single object, which is the reduction result, in an integer holding the
     * value of the sum.
     *
     * @param iterable the iterable whose elements are to be reduced
     * @param aggregate the reduction operation (e.g. a sum)
     * @param stopOnError if set to true the 1st exception that occurs during the reduction operation execution will be
     *                    wrapped as a #RuntimeException and re-thrown or it set to false all such exceptions will be ignored
     * @param <T> the type of elements in the iterable
     * @param <R> the type of the result of the reduction operation
     * @return
     */
    <T, R> R reduce(Iterable<T> iterable, Aggregate<T, R> aggregate, boolean stopOnError);
}
