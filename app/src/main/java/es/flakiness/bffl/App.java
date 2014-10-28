package es.flakiness.bffl;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

public class App extends Application {

    @Module(injects = {
            BuildCardView.class,
            BuildListView.class,
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

        @Provides
        public BuildListAdapter provideListAdapter() {
            return new BuildListAdapter();
        }
    }

    private ObjectGraph mGraph;

    @Override
    public void onCreate() {
        mGraph = ObjectGraph.create(new Mod(this));
    }

    static public App get(Context context) {
        return (App)context.getApplicationContext();
    }

    public <T> void inject(T target) {
        mGraph.inject(target);
    }
}
