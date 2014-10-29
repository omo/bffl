package es.flakiness.bffl;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.flakiness.shuttle.CentralStation;
import es.flakiness.shuttle.Station;

public class App extends Application {

    @Module(injects = {
            BuildView.class,
            BuildListView.class,
            BuildListPreso.class
    })
    public static class Mod {
        private App mApp;

        public Mod(App app) {
            mApp = app;
        }

        @Provides @Singleton
        public Picasso providePicasso() {
            return Picasso.with(mApp);
        }

        @Provides @Singleton
        public Station provideStation() { return CentralStation.getsInstance(); }
    }

    private ObjectGraph mGraph;
    private DatabaseOpenHelper mDatabaseHelper;

    @Override
    public void onCreate() {
        mDatabaseHelper = new DatabaseOpenHelper(this);
        mGraph = ObjectGraph.create(new Mod(this));
    }

    static public App get(Context context) {
        return (App)context.getApplicationContext();
    }

    public <T> void inject(T target) {
        mGraph.inject(target);
    }
}
