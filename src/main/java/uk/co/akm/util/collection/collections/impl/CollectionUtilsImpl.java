package uk.co.akm.util.collection.collections.impl;

import uk.co.akm.util.collection.collections.*;
import uk.co.akm.util.collection.collections.impl.groups.GroupClassifier;
import uk.co.akm.util.collection.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by Thanos Mavroidis on 23/03/2017.
 */
final class CollectionUtilsImpl implements CollectionUtils {
    private static final String TAG = CollectionUtilsImpl.class.getSimpleName();

    private final Logger logger;
    private final GroupClassifier groupClassifier;

    CollectionUtilsImpl(Logger logger) {
        this.logger = logger;
        this.groupClassifier = new GroupClassifier(this);
    }

    public boolean isNeitherNullNorEmpty(Collection collection) {
        return !(isNullOrEmpty(collection));
    }

    public boolean isNullOrEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public <T> T findFirst(Iterable<T> iterable, Predicate<T> matcher, boolean stopOnError) {
        if (iterable != null && matcher != null) {
            for (T element : iterable) {
                try {
                    if (matcher.test(element)) {
                        return element;
                    }
                } catch (Exception e) {
                    handleError("Error when searching for iterable element.", e, stopOnError);
                }
            }
        }

        return null;
    }

    public <T> T findUnique(Iterable<T> iterable, Predicate<T> matcher, boolean stopOnError) throws IllegalStateException {
        T found = null;

        if (iterable != null && matcher != null) {
            for (T element : iterable) {
                if (matches(element, matcher, stopOnError)) {
                    if (found == null) {
                        found = element;
                    } else {
                        throw new IllegalStateException("Found more than one elements matching the input predicate.");
                    }
                }
            }
        }

        return found;
    }

    private <T> boolean matches(T element, Predicate<T> matcher, boolean stopOnError) {
        try {
            return matcher.test(element);
        } catch (Exception e) {
            handleError("Error when searching for collection element.", e, stopOnError);
            return false;
        }
    }

    public <T> void filter(Iterable<T> iterable, Predicate<T> include, Collection<T> filtered, boolean stopOnError) {
        if (iterable != null && include != null && filtered != null) {
            for (T element : iterable) {
                try {
                    if (include.test(element)) {
                        filtered.add(element);
                    }
                } catch (Exception e) {
                    handleError("Error when filtering collection element.", e, stopOnError);
                }
            }
        }
    }

    public <T> void operate(Iterable<T> iterable, Closure<T> closure, boolean stopOnError) {
        if (iterable != null && closure != null) {
            for (T element : iterable) {
                try {
                    closure.execute(element);
                } catch (Exception e) {
                    handleError("Error when modifying collection element.", e, stopOnError);
                }
            }
        }
    }

    public <I, O> void transform(Iterable<I> iterable, Transformer<I, O> transformer, Collection<O> transformed, boolean stopOnError) {
        if (iterable != null && transformer != null && transformed != null) {
            for (I element : iterable) {
                try {
                    final O transformResult = transformer.transform(element);
                    if (transformResult != null) {
                        transformed.add(transformResult);
                    }
                } catch (Exception e) {
                    handleError("Error when transforming collection element.", e, stopOnError);
                }
            }
        }
    }

    public <T, K, V> void transform(Iterable<T> iterable, MapEntryBuilder<T, K, V> entryBuilder, Map<K, V> map, boolean stopOnError) {
        if (iterable != null && entryBuilder != null && map != null) {
            for (T element : iterable) {
                try {
                    final Map.Entry<K, V> mapEntry = entryBuilder.buildMapEntry(element);
                    if (mapEntry != null) {
                        map.put(mapEntry.getKey(), mapEntry.getValue());
                    }
                } catch (Exception e) {
                    handleError("Error when transforming collection element to a map entry.", e, stopOnError);
                }
            }
        }
    }

    public <T, K> void group(Iterable<T> iterable, Transformer<T, K> classifier, Map<K, Collection<T>> groups, boolean stopOnError) {
        if (iterable != null && classifier != null && groups != null) {
            for (T element : iterable) {
                try {
                    final K category = classifier.transform(element);
                    if (category != null) {
                        getGroupForCategory(groups, category).add(element);
                    }
                } catch (Exception e) {
                    handleError("Error when extracting collection element category.", e, stopOnError);
                }
            }
        }
    }

    private <T, K> Collection<T> getGroupForCategory(Map<K, Collection<T>> groups, K categoty) {
        if (!groups.containsKey(categoty)) {
            groups.put(categoty, new ArrayList<T>());
        }

        return groups.get(categoty);
    }

    public <T> Group<T> group(Collection<T> collection, Comparator<T> elementOrder, Classifier<T, Object>[] classifiers) {
        return groupClassifier.groupBy(collection, elementOrder, classifiers);
    }

    public <T> int countElements(Iterable<T> iterable, Predicate<T> predicate, boolean stopOnError) {
        int count = 0;

        if (iterable != null && predicate != null) {
            for (T element : iterable) {
                try {
                    if (predicate.test(element)) {
                        count++;
                    }
                } catch (Exception e) {
                    handleError("Error when counting collection element.", e, stopOnError);
                }
            }
        }

        return count;
    }

    public <T> void split(Iterable<T> iterable, Predicate<T> predicate, Collection<T> predicateTrue, Collection<T> predicateFalse, boolean stopOnError) {
        if (iterable != null && predicate != null && predicateTrue != null && predicateFalse != null) {
            for (T element : iterable) {
                try {
                    if (predicate.test(element)) {
                        predicateTrue.add(element);
                    } else {
                        predicateFalse.add(element);
                    }
                } catch (Exception e) {
                    handleError("Error when splitting collection.", e, stopOnError);
                }
            }
        }
    }

    public <T, R> R reduce(Iterable<T> iterable, Aggregate<T, R> aggregate, boolean stopOnError) {
        R result = null;

        if (iterable != null && aggregate != null) {
            for (T element : iterable) {
                try {
                    aggregate.execute(element);
                } catch (Exception e) {
                    handleError("Error when reducing collection.", e, stopOnError);
                }
            }

            try {
                result = aggregate.getResult();
            } catch (Exception e) {
                handleError("Error when getting final result of collection reduction.", e, stopOnError);
            }
        }

        return result;
    }

    private void handleError(String errorMessage, Exception ex, boolean stopOnError) {
        logger.log(TAG, errorMessage, ex);
        if (stopOnError) {
            throw new RuntimeException(errorMessage, ex);
        }
    }
}
