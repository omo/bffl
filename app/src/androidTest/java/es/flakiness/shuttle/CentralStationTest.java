package es.flakiness.shuttle;

import com.squareup.otto.Subscribe;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CentralStationTest extends TestCase {

    private Sequence mPrimaryName = new Sequence("Primary");
    private CentralStation mTarget;
    private int nThreads = 10;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Forces plenty of concurrency instead of asking the core count.
        mTarget = new CentralStation("test", Executors.newFixedThreadPool(nThreads));
    }

    static public class WaitAndRunEvent {
        public String mName;
        public int mSleepLength;

        public WaitAndRunEvent(String mName, int mSleepLength) {
            this.mName = mName;
            this.mSleepLength = mSleepLength;
        }
    }

    static public class NotifyEvent {
        public void notifyDone() {
            synchronized (this) {
                this.notifyAll();
            }
        }

        public void waitUntilDone() throws InterruptedException {
            synchronized (this) {
                this.wait();
            }
        }
    }

    static public class QueueObject {

        ConcurrentLinkedQueue<String> mQueue = new ConcurrentLinkedQueue();

        @Subscribe
        public void run(WaitAndRunEvent event) throws InterruptedException {
            mQueue.add("Pre" + event.mName);
            Thread.sleep(event.mSleepLength);
            mQueue.add("Post" + event.mName);
        }

        @Subscribe
        public void notifyThis(NotifyEvent event) {
            event.notifyDone();
        }

        List<String> getQueued() {
            return Arrays.asList(mQueue.toArray(new String[]{}));
        }
    }

    public void testSequentialShouldBeExecutedOneByOne() throws InterruptedException {
        QueueObject qo = new QueueObject();
        Shuttle seq = mTarget.sequential(mPrimaryName);
        seq.register(qo);

        NotifyEvent notification = new NotifyEvent();
        seq.post(new WaitAndRunEvent("1", 300));
        seq.post(new WaitAndRunEvent("2", 200));
        seq.post(new WaitAndRunEvent("3", 100));
        seq.post(notification);

        notification.waitUntilDone();

        List result = qo.getQueued();
        assertEquals(result.size(), 6);
        assertEquals(result.get(0), "Pre1");
        assertEquals(result.get(1), "Post1");
        assertEquals(result.get(2), "Pre2");
        assertEquals(result.get(3), "Post2");
        assertEquals(result.get(4), "Pre3");
        assertEquals(result.get(5), "Post3");
    }

    public void testSequentialShouldGiveSameInstanceForAKey() {
        final ConcurrentLinkedQueue<Shuttle> shuttles = new ConcurrentLinkedQueue();
        final CentralStation target = mTarget;
        final Sequence key = new Sequence();
        final NotifyEvent notification = new NotifyEvent();

        Runnable getShuttle = new Runnable() {
            @Override
            public void run() {
                try {
                    notification.waitUntilDone();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                shuttles.add(target.sequential(key));
            }
        };

        int nTasks = 4;
        assertTrue(nTasks < nThreads);
        for (int i = 0; i < nTasks; i++)
            target.getmExecutor().execute(getShuttle);
        notification.notifyDone();
        for (Shuttle s : shuttles)
            assertEquals(shuttles.peek(), s);
    }

    public void testSequentialShouldGiveDifferentInstancesForDifferentKeys() {
        Shuttle s1 = mTarget.sequential(new Sequence());
        Shuttle s2 = mTarget.sequential(new Sequence());
        assertNotSame(s1, s2);
    }
}