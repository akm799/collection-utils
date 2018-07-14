package uk.co.akm.util.collection.collections.impl.groups;


import uk.co.akm.util.collection.collections.Group;

import java.util.*;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class GroupImpl<T> implements MutableGroup<T> {
    private final Map<Object, Collection<T>> map;
    private final Collection<Object> categories;
    private final Map<Object, Collection<Group<T>>> subGroups;

    private Class categoryType;

    GroupImpl(Map<Object, Collection<T>> map, Comparator<Object> categoryOrder, Comparator<T> elementOrder) {
        this.map = buildOrderedMap(map, categoryOrder, elementOrder);
        this.categories = buildOrderedCategories(map.keySet(), categoryOrder);
        this.categoryType = categories.iterator().next().getClass();
        this.subGroups = mapInstance(categoryOrder);
    }

    private Map<Object, Collection<T>> buildOrderedMap(Map<Object, Collection<T>> unorderedMap, Comparator<Object> categoryOrder, Comparator<T> elementOrder) {
        final Map<Object, Collection<T>> orderedMap = mapInstance(categoryOrder);
        for (Object category : unorderedMap.keySet()) {
            final Collection<T> orderedElements = orderElements(unorderedMap.get(category), elementOrder);
            orderedMap.put(category, orderedElements);
        }

        return orderedMap;
    }

    private Collection<T> orderElements(Collection<T> unorderedElements, Comparator<T> elementOrder) {
        final Collection<T> orderedElements = collectionInstance(elementOrder);
        orderedElements.addAll(unorderedElements);

        return orderedElements;
    }

    private Collection<Object> buildOrderedCategories(Collection<Object> unorderedCategories, Comparator<Object> categoryOrder) {
        final Collection<Object> orderedCategories = collectionInstance(categoryOrder);
        orderedCategories.addAll(unorderedCategories);

        return orderedCategories;
    }

    public void addSubGroup(Object category, Group<T> subGroup) {
        if (!subGroups.containsKey(category)) {
            subGroups.put(category, new ArrayList());
        }

        subGroups.get(category).add(subGroup);
    }

    private Map mapInstance(Comparator categoryOrder) {
        if (categoryOrder == null) {
            return new LinkedHashMap();
        } else {
            return new TreeMap(categoryOrder);
        }
    }

    private Collection collectionInstance(Comparator order) {
        if (order == null) {
            return new ArrayList();
        } else {
            return new TreeSet(order);
        }
    }

    public Class getCategoryType() {
        return categoryType;
    }

    public Collection<Object> getCategories() {
        return categories;
    }

    public boolean hasSubGroups() {
        return !subGroups.isEmpty();
    }

    public Collection<Group<T>> getSubGroups(Object key) {
        return subGroups.get(key);
    }

    public Collection<T> getElementsOfCategory(Object key) {
        return map.get(key);
    }
}
