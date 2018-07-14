package uk.co.akm.util.collection.streams.impl.factory;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.akm.util.collection.log.Logger;
import uk.co.akm.util.collection.log.impl.LoggerFactory;
import uk.co.akm.util.collection.streams.impl.factory.impl.CollectionFactorySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanos Mavroidis on 27/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionFactoryTest {

    @Mock
    private Logger testLogger;

    private CollectionFactory underTest;

    @Before
    public void setUp() {
        LoggerFactory.setLoggerInstance(testLogger);
        underTest = CollectionFactorySource.instance();
    }

    @Test
    public void shouldProduceCollectionInstance() {
        final List list = underTest.newInstance(ArrayList.class, 3);
        Assert.assertNotNull(list);
        Mockito.verify(testLogger, Mockito.never()).log(Mockito.anyString(), Mockito.anyString()); // No errors logged means we found the constructor with the int argument.
    }

    @Test
    public void shouldProduceNonPreSizedCollectionInstance() {
        final List list = underTest.newInstance(NonPreSizedList.class, 3);
        Assert.assertNotNull(list);
    }

    @Test
    public void shouldNotProduceCollectionInstanceWhenNoSuitableConstructorIsAvailable() {
        try {
            underTest.newInstance(UnsuitableList.class, 3);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof NoSuchMethodException);
        }
    }

    public static final class NonPreSizedList extends ArrayList {
        public NonPreSizedList() {}

        private NonPreSizedList(int size) {
            super(size);
        }
    }

    public static final class UnsuitableList extends ArrayList {
        private UnsuitableList() {}

        private UnsuitableList(int size) {
            super(size);
        }
    }
}
