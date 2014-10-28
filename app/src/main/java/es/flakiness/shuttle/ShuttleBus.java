package es.flakiness.shuttle;

import com.squareup.otto.Bus;

public abstract class ShuttleBus implements Shuttle {

    private Bus mBus;

    public ShuttleBus(String name) {
        mBus = new Bus(name);
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
