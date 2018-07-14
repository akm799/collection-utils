package uk.co.akm.util.collection.collections;

import org.junit.Assert;
import org.junit.Test;
import uk.co.akm.util.collection.collections.group.GroupPrinter;
import uk.co.akm.util.collection.collections.group.GroupTestData;
import uk.co.akm.util.collection.collections.impl.CollectionUtilsFactory;
import uk.co.akm.util.collection.model.Day;
import uk.co.akm.util.collection.model.Visit;

import java.util.Comparator;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class CollectionUtilsGroupTest {
    private Transformer<Visit, Object> countryCategory = new Transformer<Visit, Object>() {
        public Object transform(Visit input) { return input.venue.city.country; }
    };

    private Transformer<Visit, Object> cityCategory = new Transformer<Visit, Object>() {
        public Object transform(Visit input) { return input.venue.city; }
    };

    private Transformer<Visit, Object> dateCategory = new Transformer<Visit, Object>() {
        public Object transform(Visit input) { return input.date; }
    };

    private Transformer<Visit, Object> typeCategory = new Transformer<Visit, Object>() {
        public Object transform(Visit input) { return input.venue.type; }
    };

    private Transformer<Visit, Object> venueCategory = new Transformer<Visit, Object>() {
        public Object transform(Visit input) { return input.venue; }
    };

    private Comparator<Object> countryOrder = new BaseComparator();
    private Comparator<Object> cityOrder = new BaseComparator();
    private Comparator<Object> dateOrder = new DayDescComparator();
    private Comparator<Object> typeOrder = new BaseComparator();
    private Comparator<Object> venueOrder = new BaseComparator();

    private Classifier<Visit, Object> byCountry = instance(countryCategory, countryOrder);
    private Classifier<Visit, Object> byCity = instance(cityCategory, cityOrder);
    private Classifier<Visit, Object> byDate = instance(dateCategory, dateOrder);
    private Classifier<Visit, Object> byType = instance(typeCategory, typeOrder);
    private Classifier<Visit, Object> byVenue = instance(venueCategory, venueOrder);
    private Comparator<Visit> visitOrder = new VisitComparator();

    private final GroupPrinter printer = new GroupPrinter();

    private Classifier<Visit, Object> instance(final Transformer<Visit, Object> categoryExtractor, final Comparator<Object> orderDefinition) {
        return new Classifier<Visit, Object>() {
            public Transformer<Visit, Object> categoryExtractor() {
                return categoryExtractor;
            }

            public boolean hasOrderDefinition() {
                return (orderDefinition != null);
            }

            public Comparator<Object> orderDefinition() {
                return orderDefinition;
            }
        };
    }

    @Test
    public void shouldClassifyElements() {
        final GroupTestData data = new GroupTestData();
        final CollectionUtils underTest = CollectionUtilsFactory.getInstance();

        final Group<Visit> root = underTest.group(data.visits, visitOrder, byCountry, byCity, byDate, byType, byVenue);
        Assert.assertNotNull(root);
        printer.print(root, System.out);
    }

    private static final class BaseComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }

    private static final class DayDescComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            final Day d1 = (Day)o1;
            final Day d2 = (Day)o2;

            final int yearDiff = d2.year - d1.year;
            if (yearDiff != 0) {
                return yearDiff;
            }

            final int monthDiff = d2.month - d1.month;
            if (monthDiff != 0) {
                return monthDiff;
            }

            return d2.day - d1.day;
        }
    }

    private static final class VisitComparator implements Comparator<Visit> {
        public int compare(Visit v1, Visit v2) {
            return v1.venue.toString().compareTo(v2.venue.toString());
        }
    }
}
