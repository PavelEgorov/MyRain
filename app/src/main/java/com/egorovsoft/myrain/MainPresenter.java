package com.egorovsoft.myrain;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.egorovsoft.myrain.sensors.HumiditySensor;
import com.egorovsoft.myrain.sensors.TemperatureSensor;
import com.egorovsoft.myrain.services.UpdateWheatherService;
import com.egorovsoft.myrain.sql.City;
import com.egorovsoft.myrain.sql.ConnectDatabase;
import com.egorovsoft.myrain.sql.sqlite.SQLLiteConnector;
import com.egorovsoft.myrain.sql.sqlite.SQLLiteDataReader;

public final class MainPresenter {
    private static MainPresenter instance = null;
    private static Object syncObj = new Object();

    private static final String TAG = "MainPresenter";

    public static final String CITY_NAME = "city_name";
    public static final String SPEED_VISIBLE = "speed_visible";
    public static final String PRESSURE_VISIBLE = "pressure_visible";
    public static final String THEME_INSTALLED = "theme_installed";

    private UpdateWheatherService updateWheatherService;

    private String cityName;
    private boolean needPressure;
    private boolean needSpeed;
    private int theme;
    private int language;
    private int error;

    private Handler handler;

    public final int THEME_LIGHT = 1001;
    public final int THEME_DARK = 1002;

    public final int LANGUAGE_EN = 1101;
    public final int LANGUAGE_RU = 1102;
    private float temperature;
    private int pressure;
    private float windSpeed;
    private Thread threadTemperature;
    private Thread threadHumidity;
    private Intent intentService;

    private SPreference sPreference;

    private boolean temperatureSensorIsActive;
    private boolean humiditySensorIsActive;

    private ConnectDatabase notesDataSource;      // Источник данных
    private SQLLiteDataReader noteDataReader;      // Читатель данных


    public MainPresenter(){
        needPressure = false;
        needSpeed = false;
        cityName = "Krasnodar";
        theme = THEME_LIGHT;
        language = LANGUAGE_EN;
        error = 200;
        handler =   new Handler();

        windSpeed = 0;
        pressure = 0;
        temperature = 0;

        updateWheatherService = new UpdateWheatherService();

        temperatureSensorIsActive = false;

    }

    public static MainPresenter getInstance() {
        synchronized (syncObj) {
            if (instance == null) {
                instance = new MainPresenter();
            }

            return instance;
        }
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isNeedPressure() {
        return needPressure;
    }

    public boolean isNeedSpeed() {
        return needSpeed;
    }

    public int getTheme() {
        return theme;
    }

    public int getLanguage() {
        return language;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setNeedPressure(boolean needPressure) {
        this.needPressure = needPressure;
    }

    public void setNeedSpeed(boolean needSpeed) {
        this.needSpeed = needSpeed;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void updateActivity(){
        if (error >= 200 && error<=299){
            Publisher.getInstance().notifyTemp(String.format("%f2",temperature) + "°C");
            Publisher.getInstance().notifyErr(getError());

            /// В идеале нужно повесить на observer
            updateCity(cityName, temperature);
        }else{
            Publisher.getInstance().notifyTemp("0°C");
            Publisher.getInstance().notifyErr(getError());
        }
        Publisher.getInstance().notifyPressure(String.format("%d",pressure) + " mm atmospheric pressure");
        Publisher.getInstance().notifyWind(String.format("%f2",windSpeed) + " m/s wind speed");

        this.error = error;
    }

//    public void updateData(){
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ConnectionToWheatherServer conn = new ConnectionToWheatherServer(cityName);
//                    conn.refreshData();
//                    conn.close();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//
//                ///{{ разобрался с использованием handler.
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        updateActivity();
//                    }
//                });
//                ///}}
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//    }

    public boolean isError() {
        if (error >= 200 && error<=299){
            return false;
        }else{
            return true;
        }
    }

    public String getError() {
        if (error >= 200 && error<=299){
            return "Success";
        }else{
            return "Error: " + String.format("%d",error);
        }
    }

    public void registerTemperatureListener(final Context context){
        if (!TemperatureSensor.getInstance(context).phoneHaveSensor()) return; /// если у телефона нет сенсора, нет смысла его запускать.
        if (!temperatureSensorIsActive) return; /// выключенный сенсор тоже нет смысла запускать
        if (TemperatureSensor.getInstance(context).isActive()) return; /// Нет смысла регистрировать сенсор, т.к. он уже зарегистрирован

        Runnable runnuble = new Runnable() {
            @Override
            public void run() {
                TemperatureSensor.getInstance(context).registerListener();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Publisher.getInstance().notifyTemp(TemperatureSensor.getInstance(context).getSensorData() + "°C");
                    }
                });
            }
        };

        threadTemperature = new Thread(runnuble);
        threadTemperature.setDaemon(true);
        threadTemperature.start();
    }

    public void setTemperatureSensorIsActive(boolean temperatureSensorIsActive) {
        this.temperatureSensorIsActive = temperatureSensorIsActive;
    }

    public void unRegisterTemperatureListener(final Context context){
        if (!TemperatureSensor.getInstance(context).phoneHaveSensor()) return; /// если у телефона нет сенсора, нет смысла его запускать.
        if (!TemperatureSensor.getInstance(context).isActive()) return; /// Нет смысла регистрировать сенсор, т.к. он уже выключен
        if(threadTemperature == null) return; /// если поток не инициализарован нет смысла его останавливать

        Thread dummy = threadTemperature;
        threadTemperature = null;
        dummy.interrupt();

        TemperatureSensor.getInstance(context).unregisterListener();
    }

    public boolean getTemperatureSensorIsActive() {
        return temperatureSensorIsActive;
    }

    public void registerHumidityListener(final Context context){
        if (!HumiditySensor.getInstance(context).phoneHaveSensor()) return; /// если у телефона нет сенсора, нет смысла его запускать.
        if (!humiditySensorIsActive) return; /// выключенный сенсор тоже нет смысла запускать
        if (HumiditySensor.getInstance(context).isActive()) return; /// Нет смысла регистрировать сенсор, т.к. он уже зарегистрирован

        Runnable runnuble = new Runnable() {
            @Override
            public void run() {
                HumiditySensor.getInstance(context).registerListener();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Publisher.getInstance().notifyPressure(HumiditySensor.getInstance(context).getSensorData() + " mm atmospheric pressure");
                    }
                });
            }
        };

        threadHumidity = new Thread(runnuble);
        threadHumidity.setDaemon(true);
        threadHumidity.start();
    }

    public void unRegisterHumidityListener(final Context context){
        if (!HumiditySensor.getInstance(context).phoneHaveSensor()) return; /// если у телефона нет сенсора, нет смысла его запускать.
        if (!HumiditySensor.getInstance(context).isActive()) return; /// Нет смысла регистрировать сенсор, т.к. он уже выключен
        if(threadHumidity == null) return; /// если поток не инициализарован нет смысла его останавливать

        Thread dummy = threadHumidity;
        threadHumidity = null;
        dummy.interrupt();

        HumiditySensor.getInstance(context).unregisterListener();
    }

    public void setHumiditySensorIsActive(boolean humiditySensorIsActive) {
        this.humiditySensorIsActive = humiditySensorIsActive;
    }

    public boolean getHumiditySensorIsActive() {
        return humiditySensorIsActive;
    }

    public void loadPreference(Context context){
        if (sPreference == null) sPreference = new SPreference(context);

        needPressure = sPreference.readNeedPressure();
        needSpeed = sPreference.readNeedSpeed();
        cityName = sPreference.readCity();
        theme = sPreference.readTheme();
        language = sPreference.readLanguage();

        Log.d(TAG, "loadPreference: ");
    }

    public void savePreference(Context context){
        if (sPreference == null) sPreference = new SPreference(context);

        sPreference.setCity(cityName);
        sPreference.setNeedPressure(needPressure);
        sPreference.setNeedSpeed(needSpeed);
        sPreference.setTheme(theme);
        sPreference.setLanguage(language);

        Log.d(TAG, "savePreference: ");
    }

    public void startServiceWheather(Context context){
        Log.d(TAG, "startServiceWheather: ");
        intentService = new Intent(context, UpdateWheatherService.class);
        intentService.putExtra(CITY_NAME, cityName);
        context.startService(intentService);

    }

    public void stopServiceWheather(Context context){
        Log.d(TAG, "stopServiceWheather: ");
        if (intentService == null) return;

        context.stopService(intentService);
    }

    public Handler getHandler(){
        return this.handler;
    }

    public void createConnection(Context context){
        notesDataSource = new SQLLiteConnector(context);
    }

    public void openSQLConnection(){
        if (notesDataSource.isOpening()) return;

        notesDataSource.openConnection();
        noteDataReader = notesDataSource.getDataReader();
    }

    public void closeSQLConnection(){
        if (!notesDataSource.isOpening()) return;

        notesDataSource.closeConnection();
    }

    public SQLLiteDataReader getDataReader(){
        return noteDataReader;
    }

    public void updateCity(String cityName_, float temperature_) {
        openSQLConnection();

        City city = new City();
        city.setCityName(cityName_);
        city.setTemperature(temperature_);

        notesDataSource.addNewRecord(city);

        closeSQLConnection();
    }
}
