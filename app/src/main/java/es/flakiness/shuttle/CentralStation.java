package es.flakiness.shuttle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
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

    private String mName;
    private Executor mExecutor;
    private HandlerShuttle mMain;
    private ExecutorShuttle mBackground;
    private ConcurrentHashMap<Object, SequentialShuttle> mBoundSequences = new ConcurrentHashMap<Object, SequentialShuttle>();

    public CentralStation(String name, Executor executor) {
        mName = name;
        mExecutor = executor;
        mMain = new HandlerShuttle(name + "Main");
        mBackground = new ExecutorShuttle(name + "Background", executor);
    }

    public CentralStation(String name) {
        this(name, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public CentralStation() {
        this("");
    }

    @Override
    public SequentialShuttle sequential(Object key) {
        SequentialShuttle found = mBoundSequences.get(key);
        if (found != null)
            return found;
        SequentialShuttle shuttle = new SequentialShuttle(mName + key.toString(), mExecutor);
        SequentialShuttle previous = mBoundSequences.putIfAbsent(key, shuttle);
        return null != previous ? previous : shuttle;
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
