package es.flakiness.bffl;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.flakiness.shuttle.CentralStation;
import es.flakiness.shuttle.Station;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.DatabaseCompartment;

@Module(injects = {
        App.class,
        BuildView.class,
        BuildListView.class,
        BuildListPreso.class,
        PictureStore.class
})
public class AppModule {
    private Context mApp;
    private DatabaseOpenHelper mDatabaseHelper;

    public AppModule(App app) {
        mApp = app;
        mDatabaseHelper = new DatabaseOpenHelper(mApp);
    }

    @Provides
    @Singleton
    public Picasso providePicasso() {
        return Picasso.with(mApp);
    }

    @Provides @Singleton
    public Station provideStation() { return CentralStation.getsInstance(); }

    @Provides @Singleton
    public DatabaseCompartment provideDatabaseCompartment() {
        return CupboardFactory.cupboard().withDatabase(mDatabaseHelper.getWritableDatabase());
    }
}
