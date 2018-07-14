package uk.co.akm.util.collection.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.akm.util.collection.collections.impl.CollectionUtilsFactory;
import uk.co.akm.util.collection.log.Logger;
import uk.co.akm.util.collection.log.impl.LoggerFactory;

import java.util.*;

/**
 * Created by Thanos Mavroidis on 23/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionUtilsTest {
    private final boolean ignoreErrors = false;
    private final boolean abortOnError = true;
    private final RuntimeException testError = new RuntimeException("Test Error");

    @Mock
    private Logger testLogger;

    private CollectionUtils underTest;

    @Before
    public void setUp() {
        LoggerFactory.setLoggerInstance(testLogger); // Set mock logger to prevent logger output in the unit tests.
        underTest = CollectionUtilsFactory.getInstance();
    }

    @Test
    public void shouldIdentifyNullOrEmptyCollections() {
        final Collection nonEmpty = Arrays.asList(new Integer[]{1});

        Assert.assertTrue(underTest.isNullOrEmpty(null));
        Assert.assertTrue(underTest.isNullOrEmpty(Collections.EMPTY_LIST));
        Assert.assertFalse(underTest.isNullOrEmpty(nonEmpty));

        Assert.assertTrue(underTest.isNeitherNullNorEmpty(nonEmpty));
        Assert.assertFalse(underTest.isNeitherNullNorEmpty(null));
        Assert.assertFalse(underTest.isNeitherNullNorEmpty(Collections.EMPTY_SET));
    }

    @Test
    public void shouldFindElement() {
        final Integer expected = 5;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> fiveFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer == expected);
            }
        };

        Assert.assertEquals(expected, underTest.findFirst(collection, fiveFinder, ignoreErrors));
        Assert.assertEquals(expected, underTest.findUnique(collection, fiveFinder, ignoreErrors));
    }

    @Test
    public void shouldFindElementIgnoringNulls() {
        final Integer expected = 5;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, null, 7, 8, 9);
        final Predicate<Integer> fiveFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer != null && integer == expected);
            }
        };

        Assert.assertEquals(expected, underTest.findFirst(collection, fiveFinder, ignoreErrors));
        Assert.assertEquals(expected, underTest.findUnique(collection, fiveFinder, ignoreErrors));
    }

    @Test
    public void shouldFindElementDespiteError() {
        final Integer expected = 5;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyFiveFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 2) {
                    throw testError;
                }

                return (integer == expected);
            }
        };

        Assert.assertEquals(expected, underTest.findFirst(collection, pickyFiveFinder, ignoreErrors));
    }

    @Test
    public void shouldStopSearchOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyFiveFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 2) {
                    throw testError;
                }

                return (integer == 5);
            }
        };

        try {
            underTest.findFirst(collection, pickyFiveFinder, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldFindFirstElement() {
        final int five = 5;
        final Integer expected = five;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        final Predicate<Integer> fiveMultiplesFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer%five == 0);
            }
        };

        Assert.assertEquals(expected, underTest.findFirst(collection, fiveMultiplesFinder, ignoreErrors));
    }

    @Test
    public void shouldFindUniqueElementDespiteError() {
        final Integer expected = 5;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyFiveFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 2) {
                    throw testError;
                }

                return (integer == expected);
            }
        };

        Assert.assertEquals(expected, underTest.findUnique(collection, pickyFiveFinder, ignoreErrors));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldErrorOnNonUniqueElement() {
        final int five = 5;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        final Predicate<Integer> fiveMultiplesFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer%five == 0);
            }
        };

        underTest.findUnique(collection, fiveMultiplesFinder, ignoreErrors);
    }

    @Test
    public void shouldStopUniqueSearchOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        final Predicate<Integer> pickyFiveMultiplesFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 2) {
                    throw testError;
                }
                return (integer%5 == 0);
            }
        };

        try {
            underTest.findUnique(collection, pickyFiveMultiplesFinder, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void shouldErrorOnNonUniqueElementDespiteOtherError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        final Predicate<Integer> pickyFiveMultiplesFinder = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 2) {
                    throw testError;
                }

                return (integer%5 == 0);
            }
        };

        underTest.findUnique(collection, pickyFiveMultiplesFinder, ignoreErrors);
    }

    @Test
    public void shouldFilterCollection() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> evenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer%2 == 0);
            }
        };

        final Collection<Integer> expected = Arrays.asList(new Integer[]{2, 4, 6, 8});

        final Collection<Integer> actual = new ArrayList<Integer>(4);
        underTest.filter(collection, evenSelector, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFilterCollectionWhenItHasNulls() {
        final Collection<Integer> collection = Arrays.asList(1, 2, null, 3, 4, 5, null, 6, 7, null, 8, 9);
        final Predicate<Integer> evenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer != null && integer%2 == 0);
            }
        };

        final Collection<Integer> expected = Arrays.asList(new Integer[]{2, 4, 6, 8});

        final Collection<Integer> actual = new ArrayList<Integer>(4);
        underTest.filter(collection, evenSelector, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldPartiallyFilterCollectionDespiteError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 4) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        final Collection<Integer> expected = Arrays.asList(2, 6, 8);

        final Collection<Integer> actual = new ArrayList<Integer>(4);
        underTest.filter(collection, pickyEvenSelector, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldStopFilteringOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 3) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        final Collection<Integer> actual = new ArrayList<Integer>(4);

        try {
            underTest.filter(collection, pickyEvenSelector, actual, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldOperateOnCollection() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Collection<Integer> expected = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90);

        final Collection<Integer> operationResult = new ArrayList<Integer>(expected.size());
        final Closure<Integer> multiplyByTen = new Closure<Integer>() {
            public void execute(Integer input) {
                operationResult.add(10*input);
            }
        };

        underTest.operate(collection, multiplyByTen, ignoreErrors);
        Assert.assertEquals(expected, operationResult);
    }

    @Test
    public void shouldPartiallyOperateOnCollectionDespiteError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Collection<Integer> expected = Arrays.asList(10, 20, 30, 40, 60, 70, 80, 90);

        final Collection<Integer> operationResult = new ArrayList<Integer>(expected.size());
        final Closure<Integer> pickyMultiplyByTen = new Closure<Integer>() {
            public void execute(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                operationResult.add(10*input);
            }
        };

        underTest.operate(collection, pickyMultiplyByTen, ignoreErrors);
        Assert.assertEquals(expected, operationResult);
    }

    @Test
    public void shouldStopOperationOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Collection<Integer> operationResult = new ArrayList<Integer>(collection.size());
        final Closure<Integer> pickyMultiplyByTen = new Closure<Integer>() {
            public void execute(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                operationResult.add(10*input);
            }
        };

        try {
            underTest.operate(collection, pickyMultiplyByTen, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldTransformCollection() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Collection<String> expected = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        final Transformer<Integer, String> intToString = new Transformer<Integer, String>() {
            public String transform(Integer input) {
                return Integer.toString(input);
            }
        };

        final Collection<String> actual = new ArrayList<String>(expected.size());
        underTest.transform(collection, intToString, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldTransformCollectionIncludingNulls() {
        final Collection<Integer> collection = Arrays.asList(1, 2, null, 3, 4, 5, null, 6, 7, null, 8, 9);
        final Collection<String> expected = Arrays.asList("1", "2", "null", "3", "4", "5", "null", "6", "7", "null", "8", "9");
        final Transformer<Integer, String> intToString = new Transformer<Integer, String>() {
            public String transform(Integer input) {
                return (input == null ? "null" : Integer.toString(input));
            }
        };

        final Collection<String> actual = new ArrayList<String>(expected.size());
        underTest.transform(collection, intToString, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldPartiallyTransformCollectionDespiteError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Collection<String> expected = Arrays.asList("1", "2", "3", "4", "6", "7", "8", "9");
        final Transformer<Integer, String> pickyIntToString = new Transformer<Integer, String>() {
            public String transform(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                return Integer.toString(input);
            }
        };

        final Collection<String> actual = new ArrayList<String>(expected.size());
        underTest.transform(collection, pickyIntToString, actual, ignoreErrors);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldStopTransformingOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Transformer<Integer, String> pickyIntToString = new Transformer<Integer, String>() {
            public String transform(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                return Integer.toString(input);
            }
        };

        final Collection<String> actual = new ArrayList<String>(collection.size());

        try {
            underTest.transform(collection, pickyIntToString, actual, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldCountElements() {
        final int expected = 4;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> evenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer%2 == 0);
            }
        };

        Assert.assertEquals(expected, underTest.countElements(collection, evenSelector, ignoreErrors));
    }

    @Test
    public void shouldPartiallyCountElementsDespiteError() {
        final int expected = 3;
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 4) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        Assert.assertEquals(expected, underTest.countElements(collection, pickyEvenSelector, ignoreErrors));
    }

    @Test
    public void shouldStopCountingOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 4) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        try {
            underTest.countElements(collection, pickyEvenSelector, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldSplitCollection() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> evenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                return (integer%2 == 0);
            }
        };

        final Collection<Integer> expectedTrue = Arrays.asList(2, 4, 6, 8);
        final Collection<Integer> expectedFalse = Arrays.asList(1, 3, 5, 7, 9);

        final Collection<Integer> actualTrue = new ArrayList<Integer>(4);
        final Collection<Integer> actualFalse = new ArrayList<Integer>(5);
        underTest.split(collection, evenSelector, actualTrue, actualFalse, ignoreErrors);
        Assert.assertEquals(expectedTrue, actualTrue);
        Assert.assertEquals(expectedFalse, actualFalse);
    }

    @Test
    public void shouldPartiallySplitCollectionDespiteError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 5) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        final Collection<Integer> expectedTrue = Arrays.asList(2, 4, 6, 8);
        final Collection<Integer> expectedFalse = Arrays.asList(1, 3, 7, 9);

        final Collection<Integer> actualTrue = new ArrayList<Integer>(4);
        final Collection<Integer> actualFalse = new ArrayList<Integer>(4);
        underTest.split(collection, pickyEvenSelector, actualTrue, actualFalse, ignoreErrors);
        Assert.assertEquals(expectedTrue, actualTrue);
        Assert.assertEquals(expectedFalse, actualFalse);
    }

    @Test
    public void shouldStopSplittingOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Predicate<Integer> pickyEvenSelector = new Predicate<Integer>() {
            public boolean test(Integer integer) {
                if (integer == 5) {
                    throw testError;
                }

                return (integer%2 == 0);
            }
        };

        final Collection<Integer> actualTrue = new ArrayList<Integer>(collection.size());
        final Collection<Integer> actualFalse = new ArrayList<Integer>(collection.size());

        try {
            underTest.split(collection, pickyEvenSelector, actualTrue, actualFalse, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldReduceCollection() {
        final String expectedSum = "45";
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Aggregate<Integer, String> sum = new Aggregate<Integer, String>() {
            private int sum = 0;

            public String getResult() {
                return Integer.toString(sum);
            }

            public void execute(Integer input) {
                sum += input;
            }
        };

        Assert.assertEquals(expectedSum, underTest.reduce(collection, sum, ignoreErrors));
    }

    @Test
    public void shouldPartiallyReduceCollectionDespiteError() {
        final String expectedSum = "40";
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Aggregate<Integer, String> pickySum = new Aggregate<Integer, String>() {
            private int sum = 0;

            public String getResult() {
                return Integer.toString(sum);
            }

            public void execute(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                sum += input;
            }
        };

        Assert.assertEquals(expectedSum, underTest.reduce(collection, pickySum, ignoreErrors));
    }

    @Test
    public void shouldNotCrashOnFinalReductionError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Aggregate<Integer, String> inaccessibleSum = new Aggregate<Integer, String>() {
            private int sum = 0;

            public String getResult() {
                if (true) {
                    throw testError;
                }

                return Integer.toString(sum);
            }

            public void execute(Integer input) {
                sum += input;
            }
        };

        Assert.assertNull(underTest.reduce(collection, inaccessibleSum, ignoreErrors));
    }

    @Test
    public void shouldStopReducingOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Aggregate<Integer, String> pickySum = new Aggregate<Integer, String>() {
            private int sum = 0;

            public String getResult() {
                return Integer.toString(sum);
            }

            public void execute(Integer input) {
                if (input == 5) {
                    throw testError;
                }

                sum += input;
            }
        };

        try {
            underTest.reduce(collection, pickySum, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldYieldNoReductionResultOnError() {
        final Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Aggregate<Integer, String> inaccessibleSum = new Aggregate<Integer, String>() {
            private int sum = 0;

            public String getResult() {
                if (true) {
                    throw testError;
                }
                return Integer.toString(sum);
            }

            public void execute(Integer input) {
                sum += input;
            }
        };

        try {
            underTest.reduce(collection, inaccessibleSum, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    @Test
    public void shouldTransformToMap() {
        final TestElement te1 = new TestElement(1, "one");
        final TestElement te2 = new TestElement(2, "two");
        final TestElement te3 = new TestElement(3, "three");
        final TestElement te4 = new TestElement(4, "four");

        final Collection<TestElement> elements = new ArrayList<TestElement>(4);
        elements.add(te1);
        elements.add(te2);
        elements.add(te3);
        elements.add(te4);

        final MapEntryBuilder<TestElement, Integer, String> mapEntryBuilder = new MapEntryBuilder<TestElement, Integer, String>() {
            public Map.Entry<Integer, String> buildMapEntry(final TestElement arg) {
                return new Map.Entry<Integer, String>() {
                    public Integer getKey() {
                        return arg.id;
                    }

                    public String getValue() {
                        return arg.name;
                    }

                    public String setValue(String value) {return null;} // Not relevant for this test.
                };
            }
        };

        final Map<Integer, String> map = new HashMap<Integer, String>(elements.size());
        underTest.transform(elements, mapEntryBuilder, map, ignoreErrors);

        assertMapElements(elements, map);
    }

    @Test
    public void shouldPartiallyTransformToMapDespiteError() {
        final TestElement te1 = new TestElement(1, "one");
        final TestElement te2 = new TestElement(2, "two");
        final TestElement te3 = new TestElement(3, "three");
        final TestElement te4 = new TestElement(4, "four");

        final Collection<TestElement> elements = new ArrayList<TestElement>(4);
        elements.add(te1);
        elements.add(te2);
        elements.add(te3);
        elements.add(te4);

        final Collection<TestElement> acceptedElements = new ArrayList<TestElement>(3);
        acceptedElements.add(te1);
        acceptedElements.add(te2);
        acceptedElements.add(te4);

        final MapEntryBuilder<TestElement, Integer, String> pickyMapEntryBuilder = new MapEntryBuilder<TestElement, Integer, String>() {
            public Map.Entry<Integer, String> buildMapEntry(final TestElement arg) {
                if (arg.id == 3) {
                    throw testError;
                }

                return new Map.Entry<Integer, String>() {
                    public Integer getKey() {
                        return arg.id;
                    }

                    public String getValue() {
                        return arg.name;
                    }

                    public String setValue(String value) {return null;} // Not relevant for this test.
                };
            }
        };

        final Map<Integer, String> map = new HashMap<Integer, String>(elements.size());
        underTest.transform(elements, pickyMapEntryBuilder, map, ignoreErrors);

        assertMapElements(acceptedElements, map);
    }

    @Test
    public void shouldStopTransformingToMapOnError() {
        final TestElement te1 = new TestElement(1, "one");
        final TestElement te2 = new TestElement(2, "two");
        final TestElement te3 = new TestElement(3, "three");
        final TestElement te4 = new TestElement(4, "four");

        final Collection<TestElement> elements = new ArrayList<TestElement>(4);
        elements.add(te1);
        elements.add(te2);
        elements.add(te3);
        elements.add(te4);

        final MapEntryBuilder<TestElement, Integer, String> pickyMapEntryBuilder = new MapEntryBuilder<TestElement, Integer, String>() {
            public Map.Entry<Integer, String> buildMapEntry(final TestElement arg) {
                if (arg.id == 3) {
                    throw testError;
                }

                return new Map.Entry<Integer, String>() {
                    public Integer getKey() {
                        return arg.id;
                    }

                    public String getValue() {
                        return arg.name;
                    }

                    public String setValue(String value) {return null;} // Not relevant for this test.
                };
            }
        };

        final Map<Integer, String> map = new HashMap<Integer, String>(elements.size());

        try {
            underTest.transform(elements, pickyMapEntryBuilder, map, abortOnError);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(testError, e.getCause());
        }
    }

    private void assertMapElements(Collection<TestElement> elements, Map<Integer, String> map) {
        Assert.assertEquals(elements.size(), map.size());
        for (TestElement te : elements) {
            Assert.assertTrue(map.containsKey(te.id));
            Assert.assertEquals(te.name, map.get(te.id));
        }
    }

    private static final class TestElement {
        public final int id;
        public final String name;

        private TestElement(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
