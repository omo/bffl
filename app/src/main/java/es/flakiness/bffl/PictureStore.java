package es.flakiness.bffl;

import javax.inject.Inject;
import javax.inject.Singleton;

import nl.qbusict.cupboard.DatabaseCompartment;

@Singleton
public class PictureStore {

    @Inject
    DatabaseCompartment mDatabase;

    public void insertDefaultIfNeeded() {

    }
}
