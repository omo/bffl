package es.flakiness.shuttle;

public interface Station {
    Shuttle main();
    Shuttle background();
    Shuttle sequential(Object key);
}
