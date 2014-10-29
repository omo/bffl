package es.flakiness.shuttle;

import android.os.Handler;
import android.os.Looper;

import com.google.common.eventbus.EventBus;
import com.squareup.otto.ThreadEnforcer;

public class HandlerShuttle extends ShuttleBus {

    private Handler mHandler;

    public HandlerShuttle(ThreadEnforcer enforcer, String name, Handler handler) {
        super(enforcer, name);
        mHandler = handler;
    }

    public HandlerShuttle(String name) {
        this(ThreadEnforcer.MAIN, name, new Handler(Looper.getMainLooper()));
    }

    public HandlerShuttle() {
        this("Shuttle");
    }

    @Override
    public void post(final Object event) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                postToBus(event);
            }
        });
    }

}
