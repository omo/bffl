package es.flakiness.shuttle;

import android.os.Handler;
import android.os.Looper;

import com.google.common.eventbus.EventBus;

public class HandlerShuttle extends ShuttleBus {

    private Handler mHandler;

    public HandlerShuttle(String name, Handler handler) {
        super(name);
        mHandler = handler;
    }

    public HandlerShuttle(String name) {
        this(name, new Handler(Looper.getMainLooper()));
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
