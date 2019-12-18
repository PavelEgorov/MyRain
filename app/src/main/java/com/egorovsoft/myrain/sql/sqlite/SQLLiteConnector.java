package com.egorovsoft.myrain.sql.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.egorovsoft.myrain.sql.City;
import com.egorovsoft.myrain.sql.ConnectDatabase;

import java.io.Closeable;
import java.io.IOException;

/// Вообще напрашивается промежуточный класс для интерфейса ConnectDatabase (В частности для методов open и close).
/// Но я не буду его в данный момент реализовывать, т.к. считаю, что нужно лучше продумать структуру.

public class SQLLiteConnector implements ConnectDatabase, Closeable {
    private SQLLiteDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private SQLLiteDataReader dataReader;
    private boolean isOpening;

    public SQLLiteConnector(Context context){
        dbHelper = new SQLLiteDatabaseHelper(context, ConnectDatabase.DATABASE, null , ConnectDatabase.VERSION);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
            // Создать читателя и открыть его
        dataReader = new SQLLiteDataReader(database);
        dataReader.open();

        isOpening = true;
    }

    @Override
    public boolean addNewRecord(City city) {
        ContentValues values = new ContentValues();
        values.put(ConnectDatabase.TABLE_NODE_CITYNAME, city.getCityName());
        values.put(ConnectDatabase.TABLE_NODE_CITYTEMPERATURE, city.getTemperature());
        // Добавление записи
        long insertId = database.insert(ConnectDatabase.TABLE_CITY, null,
                values);

        return (insertId == -1)? false:true; /// -1 если была ошибка
    }

    @Override
    public boolean updateRecord(City city) {
        ContentValues editedNote = new ContentValues();
        editedNote.put(ConnectDatabase.TABLE_NODE_CITYNAME, city.getCityName());
        editedNote.put(ConnectDatabase.TABLE_NODE_CITYTEMPERATURE, city.getTemperature());
        // Изменение записи

        long updateId = -1;
        updateId = database.update(ConnectDatabase.TABLE_CITY,
                editedNote,
                ConnectDatabase.TABLE_NODE_CITYNAME + "=" + city.getCityName(),
                null);

        return (updateId == -1)? false:true;
    }

    @Override
    public void close() throws IOException {
        dataReader.close();
        dbHelper.close();

        isOpening = false;
    }

    public SQLLiteDataReader getDataReader(){
        return dataReader;
    }

    @Override
    public void openConnection() {
        this.open();
    }

    @Override
    public void closeConnection() {
        try {
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpening(){
        return this.isOpening;
    }
}
