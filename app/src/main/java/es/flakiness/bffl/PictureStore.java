package es.flakiness.bffl;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import nl.qbusict.cupboard.DatabaseCompartment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

@Singleton
public class PictureStore {

    private DatabaseCompartment mDatabase;
    private ConnectableObservable<TrivialResults> mInitialDefaultIsFilled;

    @Inject
    public PictureStore(DatabaseCompartment database) {
        mDatabase = database;
        mInitialDefaultIsFilled = fillDefaultIfNeeded();
        mInitialDefaultIsFilled.connect();
    }

    public Observable<TrivialResults> clear() {
        return ensureDefault().observeOn(Schedulers.io()).map(new Func1<TrivialResults, TrivialResults>() {
            @Override
            public TrivialResults call(TrivialResults trivialResults) {
                Log.d(getClass().getSimpleName(), "clear!");
                mDatabase.delete(Picture.class, null);
                return TrivialResults.DONE;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TrivialResults> ensureDefault() {
        return mInitialDefaultIsFilled;
    }

    public Observable<Integer> count() {
        return Async.start(new Func0<Integer>() {
            @Override
            public Integer call() {
                return mDatabase.query(Picture.class).getCursor().getCount();
            }
        }, Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Picture> find(final Long status, long seed) {
        // TODO: Cache things
        return Async.start(new Func0<Picture>() {
            @Override
            public Picture call() {
                List<Picture> found = mDatabase.query(Picture.class).withSelection("status = ? ", status.toString()).list();
                return found.get(status.intValue() % found.size());
            }
        }, Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
    }

    private ConnectableObservable<TrivialResults> fillDefaultIfNeeded() {
        return Async.start(new Func0<TrivialResults>() {
            @Override
            public TrivialResults call() {
                if (0 < mDatabase.query(Picture.class).getCursor().getCount())
                    return TrivialResults.DONE;
                mDatabase.put(Picture.createBuiltinList());
                return TrivialResults.DONE;
            }
        }, Schedulers.io()).replay(AndroidSchedulers.mainThread());
    }
}
