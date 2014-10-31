package es.flakiness.bffl;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.DatabaseCompartment;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module(injects = {
        App.class,
        BuildView.class,
        BuildListView.class,
        BuildListPreso.class,
        PictureStore.class,
        MainActivity.class,
})
public class AppModule {
    private Context mApp;
    private DatabaseOpenHelper mDatabaseHelper;

    public AppModule(App app) {
        mApp = app;
        mDatabaseHelper = new DatabaseOpenHelper(mApp);
    }

    @Provides @Singleton
    public Picasso providePicasso() {
        return Picasso.with(mApp);
    }

    @Provides @Singleton
    public DatabaseCompartment provideDatabaseCompartment() {
        return CupboardFactory.cupboard().withDatabase(mDatabaseHelper.getWritableDatabase());
    }
}
