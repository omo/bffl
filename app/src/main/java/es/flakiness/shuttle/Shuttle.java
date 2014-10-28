package es.flakiness.shuttle;

public interface Shuttle {
    void post(Object event);
    void register(Object subject);
    void unregister(Object object);
}
