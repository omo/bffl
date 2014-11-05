package es.flakiness.bffl;

import java.util.concurrent.atomic.AtomicBoolean;

import rx.functions.Func1;

public class AtomicPredicate<T> implements Func1<T, Boolean> {
    private AtomicBoolean mPredicate = new AtomicBoolean();

    public AtomicPredicate(boolean pred) {
        mPredicate = new AtomicBoolean(pred);
    }

    public void set(boolean pred) {
        mPredicate.set(pred);
    }

    @Override
    public Boolean call(T o) {
        return mPredicate.get();
    }
}
