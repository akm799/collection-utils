package uk.co.akm.util.collection.collections;

import java.util.Collection;

/**
 * Represents group of elements of type #T which have been grouped in a set categories represented by a set of keys.
 *
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public interface Group<T> {

    /**
     * The type of the category in this group.
     *
     * @return type of the category in this group
     */
    Class getCategoryType();

    /**
     * Returns all the categories in the set of categories in this group. All objects returned by this method will be
     * an instance of the class returned by the {@link #getCategoryType()} method.
     *
     * @return all the categories in the set of categories in this group
     */
    Collection<Object> getCategories();

    /**
     * All elements belonging to the category specified by the input object. An input to this method must be one of the
     * outputs returned by the {@link #getCategories()} method.
     *
     * @param category the category for which all elements will be returned
     * @return elements belonging to the category specified by the input object
     */
    Collection<T> getElementsOfCategory(Object category);

    /**
     * Returns true if the (already grouped) elements in this subgroup are grouped into further sub-categories, or
     * false otherwise.
     *
     * @return true if the (already grouped) elements in this subgroup are grouped into further sub-categories, or
     * false otherwise
     */
    boolean hasSubGroups();

    /**
     * Returns all the subgroups by which elements of this group belonging to the category specified by the input object,
     * are sub-grouped. This method should only be called if the {@link #hasSubGroups()} method has returned true. An
     * input to this method must be one of the outputs returned by the {@link #getCategories()} method.
     *
     * @param category the category of this group for whose elements the further sub-categories will be returned
     * @return all the subgroups by which elements of this group belonging to the category specified by the input object,
     * are sub-grouped
     */
    Collection<Group<T>> getSubGroups(Object category);
}
