package es.flakiness.shuttle;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CentralStation implements Station {

    static private volatile CentralStation sInstance;

    static public CentralStation getsInstance() {
        CentralStation instance = sInstance;
        if (instance == null) {
            synchronized (CentralStation.class) {
                instance = sInstance;
                if (instance == null) {
                    sInstance = instance = new CentralStation("Singleton");
                }
            }
        }

        return instance;
    }

    private HandlerShuttle mMain;
    private ExecutorShuttle mBackground;

    public CentralStation(HandlerShuttle main, ExecutorShuttle background) {
        mMain = main;
        mBackground = background;
    }

    public CentralStation(String name) {
        mMain = new HandlerShuttle(name + "Main");
        mBackground = new ExecutorShuttle(name + "Background", Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public CentralStation() {
        this("");
    }

    @Override
    public HandlerShuttle main() {
        return mMain;
    }

    @Override
    public ExecutorShuttle background() {
        return mBackground;
    }
}
