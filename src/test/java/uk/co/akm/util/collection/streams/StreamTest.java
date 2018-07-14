package uk.co.akm.util.collection.streams;


import org.junit.Assert;
import org.junit.Test;
import uk.co.akm.util.collection.collections.Closure;
import uk.co.akm.util.collection.collections.Predicate;
import uk.co.akm.util.collection.collections.Transformer;
import uk.co.akm.util.collection.collections.impl.CollectionUtilsFactory;
import uk.co.akm.util.collection.streams.impl.StreamFactory;

import java.util.*;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
public class StreamTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotProcessNullCollection() {
        StreamFactory.listStreamInstance(null, true);
    }

    @Test
    public void shouldNotCrashWithNullProcessingArguments() {
        final Collection<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Stream<List> underTest = StreamFactory.listStreamInstance(input, true);
        final List<String> output = underTest.filter(null).operate(null).transform(null).getCollection();

        Assert.assertNotNull(output);
        Assert.assertEquals(input, output);
    }

    @Test
    public void shouldProcessCollection() {
        final Predicate<MutableInteger> selectOddNumbers = new Predicate<MutableInteger>() {
            public boolean test(MutableInteger integer) {
                return (integer.value%2 != 0);
            }
        };

        final Closure<MutableInteger> multiplyBy10 = new Closure<MutableInteger>() {
            public void execute(MutableInteger integer) {
                integer.value = 10*integer.value;
            }
        };

        final Transformer<MutableInteger, String> convertToString = new Transformer<MutableInteger, String>() {
            public String transform(MutableInteger integer) {
                return Integer.toString(integer.value);
            }
        };

        final Collection<MutableInteger> input = buildCollection(MutableInteger.builder, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Stream<List> underTest = StreamFactory.listStreamInstance(input, true);
        final List<String> output = underTest.filter(selectOddNumbers).operate(multiplyBy10).transform(convertToString).getCollection();

        final Collection<String> expectedOutput = Arrays.asList("10", "30", "50", "70", "90");
        Assert.assertNotNull(output);
        Assert.assertEquals(expectedOutput, output);
    }

    private <T> Collection<T> buildCollection(Transformer<Integer, T> transformer, Integer... values) {
        final Collection<Integer> intValues = Arrays.asList(values);
        final Collection<T> collection = new ArrayList<T>(values.length);
        CollectionUtilsFactory.getInstance().transform(intValues, transformer, collection, true);

        return collection;
    }

    @Test
    public void shouldTransformCollectionAsSet() {
        final Transformer<Integer, String> convertToCategoryString = new Transformer<Integer, String>() {
            public String transform(Integer integer) {
                return (integer%2 == 0 ? "even" : "odd");
            }
        };

        final Collection<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Stream<Set> underTest = StreamFactory.setStreamInstance(input, true);
        final Set<String> output = underTest.transform(convertToCategoryString).getCollection();

        Assert.assertNotNull(output);
        Assert.assertEquals(2, output.size());
        Assert.assertTrue(output.contains("even"));
        Assert.assertTrue(output.contains("odd"));
    }

    @Test
    public void shouldBeAwareOfCollectionDuringProcessing() {
        final Transformer<MarkableInteger, String> convertToStringHalfWay = new HalfWayStringConverter();
        final Collection<MarkableInteger> input = buildCollection(MarkableInteger.builder, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Stream<List> underTest = StreamFactory.listStreamInstance(input, true);
        final List<String> output = underTest.transform(convertToStringHalfWay).getCollection();

        final Collection<String> expectedOutput = Arrays.asList("0", "1", "2", "3", "4", "", "", "", "", ""); // Converter was aware of collection during processing and stopped converting half-way.
        Assert.assertNotNull(output);
        Assert.assertEquals(expectedOutput, output);
    }

    private static final class MutableInteger {
        static Transformer<Integer, MutableInteger> builder = new Transformer<Integer, MutableInteger>() {
            public MutableInteger transform(Integer value) {
                return new MutableInteger(value);
            }
        };

        int value;

        private MutableInteger(int value) {
            this.value = value;
        }
    }

    private static final class MarkableInteger {
        static Transformer<Integer, MarkableInteger> builder = new Transformer<Integer, MarkableInteger>() {
            public MarkableInteger transform(Integer value) {
                return new MarkableInteger(value);
            }
        };

        final int value;
        private boolean marked;

        private MarkableInteger(int value) {
            this.value = value;
        }

        boolean isMarked() {
            return marked;
        }

        void mark() {
            marked = true;
        }
    }

    private static final class HalfWayStringConverter implements Transformer<MarkableInteger, String>, StreamAware<MarkableInteger> {
        private int halfWay;
        private Collection<MarkableInteger> collection;

        private final Predicate<MarkableInteger> markedCounter = new Predicate<MarkableInteger>() {
            public boolean test(MarkableInteger arg) {
                return arg.isMarked();
            }
        };

        public void init(Collection<MarkableInteger> collection) {
            this.halfWay = collection.size()/2;
            this.collection = collection;
        }

        public String transform(MarkableInteger integer) {
            final int marked = CollectionUtilsFactory.getInstance().countElements(collection, markedCounter, true);
            integer.mark();

            return (marked < halfWay ? Integer.toString(integer.value) : ""); // Converter quits converting after half-elements have already been marked.
        }
    }
}