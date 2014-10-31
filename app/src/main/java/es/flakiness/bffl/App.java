package es.flakiness.bffl;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.DatabaseCompartment;

public class App extends Application {

    private ObjectGraph mGraph;

    @Inject PictureStore mPictureStore;

    @Override
    public void onCreate() {
        mGraph = ObjectGraph.create(new AppModule(this));
        mGraph.inject(this);
    }

    static public App get(Context context) {
        return (App)context.getApplicationContext();
    }
    public <T> void inject(T target) {
        mGraph.inject(target);
    }
    public <T> T make(Class<T> clazz) { return mGraph.get(clazz); }
}
