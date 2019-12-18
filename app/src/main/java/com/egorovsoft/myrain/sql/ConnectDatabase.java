package com.egorovsoft.myrain.sql;

import com.egorovsoft.myrain.sql.sqlite.SQLLiteDataReader;

public interface ConnectDatabase {
    String DATABASE = "myrain";
    String TABLE_CITY = "city";
    String TABLE_NODE_CITYNAME = "city_name";
    String TABLE_NODE_CITYTEMPERATURE = "city_temperature";
    int VERSION = 1;

    boolean addNewRecord(City city);
    boolean updateRecord(City city);

    boolean isOpening();

    SQLLiteDataReader getDataReader();

    void openConnection();

    void closeConnection();
}
