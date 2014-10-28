package es.flakiness.bffl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.qbusict.cupboard.CupboardFactory;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "bffl";
    public static int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CupboardFactory.cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        CupboardFactory.cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }
}
