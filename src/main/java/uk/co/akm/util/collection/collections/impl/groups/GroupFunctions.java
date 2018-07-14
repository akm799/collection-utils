package uk.co.akm.util.collection.collections.impl.groups;


import uk.co.akm.util.collection.collections.Group;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 11/07/2018.
 */
final class GroupFunctions {

    GroupFunctions() {}

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
