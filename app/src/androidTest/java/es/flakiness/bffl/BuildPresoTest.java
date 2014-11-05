package es.flakiness.bffl;

import junit.framework.TestCase;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import nl.qbusict.cupboard.DatabaseCompartment;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class BuildPresoTest extends TestCase {

    private FakePictureStore mPictureStore;

    static public class TestingBuildPreso extends BuildPreso {

        public TestingBuildPreso(Build model) {
            super(model, null);
        }

        @Override
        public Date now() {
            return BuildPreso.parseStubDate("2014/10/31-11:30:00");
        }
    };

    static public class FakePictureStore extends PictureStore {
        PublishSubject<Picture> mFound;

        public FakePictureStore(DatabaseCompartment database) {
            super(database);
        }

        @Override
        public Observable<Picture> find(Long status, long seed) {
            mFound = PublishSubject.create();
            return mFound;
        }

        private void publishOnce(Picture toReturn) {
            mFound.onNext(toReturn);
            mFound.onCompleted();
        }
    }

    public void testHello() {
        BuildPreso target = new TestingBuildPreso(makeBuildForTest());
        assertEquals("30 minutes ago, took 2 hours", target.getTimestampText());
        assertEquals("Passed!", target.getStatusText());
    }

    private Build makeBuildForTest() {
        return Build.create(
                Long.valueOf(BuildStatus.PASSED.sequence()),
                "make",
                BuildPreso.parseStubDate("2014/10/31-09:00:00"),
                BuildPreso.parseStubDate("2014/10/31-11:00:00"));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mPictureStore = new FakePictureStore(null);
    }

    public void testGetImagePath() {
        BuildPreso target = new BuildPreso(makeBuildForTest(), mPictureStore);
        final AtomicReference<String> called = new AtomicReference();
        target.getImageURL().subscribe(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        called.set(s);
                    }
                }
        );

        assertNull(called.get());

        Picture toReturn = Picture.createBuiltinList().get(0);
        mPictureStore.publishOnce(toReturn);
        assertEquals(called.get(), toReturn.mediumImage);
    }

    public void testGetImagePathAfterDetach() {
        BuildPreso target = new BuildPreso(makeBuildForTest(), mPictureStore);
        final AtomicReference<String> called = new AtomicReference();
        target.getImageURL().subscribe(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        called.set(s);
                    }
                }
        );

        target.onViewDetachedFromWindow(null);

        Picture toReturn = Picture.createBuiltinList().get(0);
        mPictureStore.publishOnce(toReturn);
        assertNull(called.get());
    }
}
