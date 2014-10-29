package es.flakiness.shuttle;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public abstract class ShuttleBus implements Shuttle {

    private Bus mBus;

    public ShuttleBus(ThreadEnforcer enforcer, String name) {
        mBus = new Bus(enforcer, name);
    }

    @Override
    public void register(Object object) {
        mBus.register(object);
    }

    @Override
    public void unregister(Object object) {
        mBus.unregister(object);
    }

    protected void postToBus(Object event) {
        mBus.post(event);
    }
}
