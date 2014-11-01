package es.flakiness.bffl;

import android.test.ApplicationTestCase;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import nl.qbusict.cupboard.DatabaseCompartment;
import rx.Observable;
import rx.observables.BlockingObservable;

public class PictureStoreTest extends ApplicationTestCase<App> {

    @Module(injects = { PictureStoreTest.class}, addsTo = AppModule.class)
    static public class TestModule {}

    private ObjectGraph mGraph;

    @Inject DatabaseCompartment mDatabase;

    public PictureStoreTest() {
        super(App.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        mGraph = getApplication().plus(new TestModule());
        mGraph.inject(this);
    }

    @Override
    public void tearDown() throws Exception {
        terminateApplication();
        super.tearDown();
    }

    public void testEnsureDefault() {
        PictureStore target = createPictureStore();
        block(target.ensureDefault());
        assertTrue(0 < BlockingObservable.from(target.count()).last());
    }

    private <T> T block(Observable<T> o) {
        return BlockingObservable.from(o).last();
    }

    private PictureStore createPictureStore() {
        return new PictureStore(mDatabase);
    }

    public void testClear() {
        PictureStore target = createPictureStore();
        assertTrue(0 < block(target.count()));
        block(target.clear());
        assertEquals(0, block(target.count()).intValue());

    }
}
