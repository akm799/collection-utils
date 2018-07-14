package uk.co.akm.util.collection.collections.impl.groups;


import uk.co.akm.util.collection.collections.Group;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public interface MutableGroup<T> extends Group<T> {

    void addSubGroup(Object category, Group<T> subGroup);
}
