package com.egorovsoft.myrain.sql.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.egorovsoft.myrain.sql.City;
import com.egorovsoft.myrain.sql.ConnectDatabase;

import java.io.Closeable;

public class SQLLiteDataReader implements Closeable {

    private Cursor cursor;
    private SQLiteDatabase database;

    private String[] notesAllColumn = {
            ConnectDatabase.TABLE_NODE_CITYNAME,
            ConnectDatabase.TABLE_NODE_CITYTEMPERATURE
    };

    public SQLLiteDataReader(SQLiteDatabase database){
        this.database = database;
    }

    public void open() {
        query();
        cursor.moveToFirst();
    }

    public void close() {
        cursor.close();
    }

    private void query() {
        cursor = database.query(ConnectDatabase.TABLE_CITY,
                notesAllColumn, null, null, null, null, null);
    }

    public void Refresh(){
        int position = cursor.getPosition();
        query();
        cursor.moveToPosition(position);
    }

    public City getPosition(int position){
        cursor.moveToPosition(position);
        return cursorToNote();
    }

    private City cursorToNote() {
        City note = new City();
        note.setCityName(cursor.getString(0));
        note.setTemperature(cursor.getFloat(1));

        return note;
    }

    public int getCount(){
        return cursor.getCount();
    }
}
