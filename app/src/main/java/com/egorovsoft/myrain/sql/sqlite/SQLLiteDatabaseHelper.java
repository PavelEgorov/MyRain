package com.egorovsoft.myrain.sql.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.egorovsoft.myrain.sql.ConnectDatabase;

import androidx.annotation.Nullable;

public class SQLLiteDatabaseHelper extends SQLiteOpenHelper {
    public SQLLiteDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ConnectDatabase.TABLE_CITY + " (" + ConnectDatabase.TABLE_NODE_CITYNAME
                + " TEXT PRIMARY KEY," + ConnectDatabase.TABLE_NODE_CITYTEMPERATURE + " REAL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /// В данный момент это первая версия
    }
}
