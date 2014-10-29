package es.flakiness.shuttle;

import com.squareup.otto.ThreadEnforcer;

import java.util.concurrent.Executor;

class ExecutorShuttle extends ShuttleBus {

    private Executor mExecutor;

    public ExecutorShuttle(String name, Executor executor) {
        super(ThreadEnforcer.ANY, name);
        mExecutor = executor;
    }

    @Override
    public void post(final Object event) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                postToBus(event);
            }
        });
    }
}