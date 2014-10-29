package es.flakiness.shuttle;

import com.squareup.otto.ThreadEnforcer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class SequentialShuttle extends ShuttleBus {

    private AtomicBoolean mRunning = new AtomicBoolean(false);
    private ConcurrentLinkedQueue<Object> mQueue = new ConcurrentLinkedQueue();
    private Executor mExecutor;

    @Override
    public void post(Object event) {
        mQueue.offer(event);
        activateIfNeeded();
    }

    private void activateIfNeeded() {
        if (mQueue.isEmpty())
            return;
        if (!mRunning.compareAndSet(false, true))
            return;
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Object taken = mQueue.poll();
                if (taken != null)
                    postToBus(taken);
                //
                // Here is a tricky part. My reasoning:
                //
                // * It can become from empty to non-empty. In that case, the thread who
                //   offer()-ed the item must have schedule the task.
                //
                // * It can become no-empty to empty. In that case, the activation is a waste
                //   but it is OK as it doesn't hurt.
                //
                // * Is it possible for offere()-ed thread to miss its own activation?
                //   I don't think so. mRunning is set to false before examining isEmpty()
                //   So either this thread or offer()-ed thread should be able to activate
                //   the shuttle.
                //
                // However TODO(morrita): Probably I should write a test for this somehow.
                mRunning.set(false);
                activateIfNeeded();
            }
        });
    }

    public SequentialShuttle(String name, Executor executor) {
        super(ThreadEnforcer.ANY, name);
        mExecutor = executor;
    }
}
