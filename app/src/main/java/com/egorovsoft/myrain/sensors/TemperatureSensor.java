package com.egorovsoft.myrain.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.egorovsoft.myrain.R;

import static android.content.Context.SENSOR_SERVICE;

public final class TemperatureSensor implements WheatherSensorManager {
    private final static String TAG = "TEMPERATURE_SENSOR";

    private static TemperatureSensor instance = null;
    private static Object syncObj = new Object();

    private boolean isActive;
    private boolean phoneHaveSensor;
    private SensorManager sensorManager;
    private Sensor sensor;
    private String sensorData;
    private Context context;
    private SensorEventListener sensorEventListener;


    public TemperatureSensor(Context context){
        isActive = false;
        this.context = context;

        sensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        phoneHaveSensor = (sensor == null)?false:true;
        sensorData = String.valueOf(R.string.disconnect);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(event.values[0]);

                sensorData =  stringBuilder.toString();

                Log.d(TAG, "onSensorChanged: " + sensorData);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public static TemperatureSensor getInstance(Context context) {
        synchronized (syncObj) {
            if (instance == null) {
                instance = new TemperatureSensor(context);
            }

            return instance;
        }
    }

    @Override
    public boolean isActive() {
        synchronized (syncObj) {
            return isActive;
        }
    }

    @Override
    public boolean phoneHaveSensor() {
        synchronized (syncObj) {
            return phoneHaveSensor;
        }
    }

    @Override
    public SensorManager getSensorManager() {
        return sensorManager;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public String getSensorData() {
        synchronized (syncObj) {
            return sensorData;
        }
    }

    @Override
    public void registerListener() {

        sensorManager.registerListener(sensorEventListener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        isActive = true;
        Log.d(TAG, "registerListener: isActive = true");
    }

    @Override
    public void unregisterListener() {
        sensorManager.unregisterListener(sensorEventListener, sensor);

        isActive = false;
        Log.d(TAG, "unregisterListener: isActive = false");
    }
}
