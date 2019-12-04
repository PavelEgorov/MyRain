package com.egorovsoft.myrain.sensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public interface WheatherSensorManager {
    boolean isActive();
    boolean phoneHaveSensor();

    SensorManager getSensorManager();
    Sensor getSensor();
    String getSensorData();

    void registerListener();
    void unregisterListener();
}
