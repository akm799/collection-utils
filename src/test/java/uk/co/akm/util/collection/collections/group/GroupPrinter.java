package uk.co.akm.util.collection.collections.group;


import uk.co.akm.util.collection.collections.Group;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class GroupPrinter {
    private final String paddingUnit = "  ";
    private final Map<Integer, String> paddings = new HashMap();

    public void print(Group group, PrintStream out) {
        print(group, 0, out);
    }

    private void print(Group group, int level, PrintStream out) {
        final String padding = getPadding(level);

        for (Object category : group.getCategories()) {
            if (group.hasSubGroups()) {
                out.println(padding + category);
                printSubGroupsOfCategoryForGroup(category, group, level, out);
            } else {
                printCategoryElementsForGroup(padding, category, group, out);
            }
        }
    }

    private void printSubGroupsOfCategoryForGroup(Object category, Group group, int level, PrintStream out) {
        final int subGroupLevel = level + 1;
        final Collection<Group> subGroups = group.getSubGroups(category);
        for (Group subGroup : subGroups) {
            print(subGroup, subGroupLevel, out); // Recursive call.
        }
    }

    private void printCategoryElementsForGroup(String padding, Object category, Group group, PrintStream out) {
        for (Object value : group.getElementsOfCategory(category)) {
            out.println(padding + value);
        }
    }

    private String getPadding(Integer level) {
        if (!paddings.containsKey(level)) {
            paddings.put(level, generatePadding(level));
        }

        return paddings.get(level);
    }

    private String generatePadding(int level) {
        final StringBuilder sb = new StringBuilder(level*paddingUnit.length());
        for (int i=0 ; i<level ; i++) {
            sb.append(paddingUnit);
        }

        return sb.toString();
    }
}
