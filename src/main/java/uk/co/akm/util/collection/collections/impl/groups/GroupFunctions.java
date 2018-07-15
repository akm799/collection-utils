package uk.co.akm.util.collection.collections.impl.groups;


import uk.co.akm.util.collection.collections.Group;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper functions for groups.
 *
 * Created by Thanos Mavroidis on 11/07/2018.
 */
final class GroupFunctions {

    GroupFunctions() {}

    /**
     * Returns a collection with all leaf-groups connected to the input group, i.e. all groups that can be reached from
     * the inout root group that themselves do not have any subgroups.
     *
     * @param root the root group
     * @param <T> the type of elements in the group
     * @return all leaf-groups connected to the input group
     */
    <T> Collection<Group<T>> findLowestLevelSubGroups(Group<T> root) {
        final Collection<Group<T>> lowestLevelSubGroups = new ArrayList();
        fillLowestLevelSubGroups(root, lowestLevelSubGroups);

        return lowestLevelSubGroups;
    }

    private <T> void fillLowestLevelSubGroups(Group<T> group, Collection<Group<T>> lowestLevelSubGroups) {
        if (group.hasSubGroups()) {
            for (Object key : group.getCategories()) {
                for (Group<T> subGroup: group.getSubGroups(key)) {
                    fillLowestLevelSubGroups(subGroup, lowestLevelSubGroups); // Recursive call.
                }
            }
        } else {
            lowestLevelSubGroups.add(group);
        }
    }
}
