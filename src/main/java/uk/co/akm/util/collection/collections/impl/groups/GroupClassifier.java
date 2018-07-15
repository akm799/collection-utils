package uk.co.akm.util.collection.collections.impl.groups;


import uk.co.akm.util.collection.collections.Classifier;
import uk.co.akm.util.collection.collections.CollectionUtils;
import uk.co.akm.util.collection.collections.Group;

import java.util.*;


/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class GroupClassifier {
    private final GroupFunctions groupFunctions = new GroupFunctions();

    private final CollectionUtils collectionUtils;

    public GroupClassifier(CollectionUtils collectionUtils) {
        this.collectionUtils = collectionUtils;
    }

    public <T> Group<T> groupBy(Collection<T> collection, Comparator<T> elementOrder, Classifier<T, Object>... classifiers) {
        Group<T> root = null;

        for (Classifier classifier : classifiers) {
            if (root == null) {
                root = groupBy(collection, classifier, elementOrder);
            } else {
                groupByStartingFromRoot(root, classifier, elementOrder);
            }
        }

        return root;
    }

    private <T> Group<T> groupBy(Collection<T> collection, Classifier<T, Object> classifier, Comparator<T> elementOrder) {
        final Map<Object, Collection<T>> map = mapInstance(classifier);
        collectionUtils.group(collection, classifier.categoryExtractor(), map, true);

        return new GroupImpl(map, classifier.orderDefinition(), elementOrder);
    }

    private <T> Map<Object, Collection<T>> mapInstance(Classifier<T, Object> classifier) {
        if (classifier.hasOrderDefinition()) {
            return new TreeMap(classifier.orderDefinition());
        } else {
            return new LinkedHashMap();
        }
    }

    private <T> void groupByStartingFromRoot(Group<T> root, Classifier<T, Object> classifier, Comparator<T> elementOrder) {
        final Collection<Group<T>> lowestLevelSubGroups = groupFunctions.findLowestLevelSubGroups(root);
        for (Group<T> lowestLevelSubGroup: lowestLevelSubGroups) {
            groupBy(lowestLevelSubGroup, classifier, elementOrder);
        }
    }

    private <T> void groupBy(Group<T> parent, Classifier<T, Object> classifier, Comparator<T> elementOrder) {
        for (Object parentCategory : parent.getCategories()) {
            final Collection<T> parentElementsOfCategory = parent.getElementsOfCategory(parentCategory);
            final Group<T> subGroup = groupBy(parentElementsOfCategory, classifier, elementOrder);
            ((MutableGroup)parent).addSubGroup(parentCategory, subGroup);
        }
    }
}
