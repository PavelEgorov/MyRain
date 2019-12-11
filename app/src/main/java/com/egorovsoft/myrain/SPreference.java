package com.egorovsoft.myrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SPreference {

    private static final String TAG = "SPreference";

    // Использую Apply т.к. он выполняется aсинхронно.
    // Понимаю, что если будет запуск приложения сразу после закрытия, я могу не получить сохраненных значений при использовании apply.
    // Но при commit нам придется ждать завершения сохранения данных.
    // Настройки будут сохраняться при изменении данных. По этому до перезапуска приложения скорее всего успеют записаться.
    // А пока приложение работает используется MainPresenter.

    private static final String BASE_NAME = "Settings";
    private static final String CITY = "city";
    private static final String NEED_PRESSURE = "need_pressure";
    private static final String NEED_SPEED = "need_speed";
    private static final String THEME = "theme";
    private static final String LANGUAGE = "language";

    private String name;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SPreference(String name, Context c){
        this.context = c;
        this.name = name;

        setSharedPreference();
    }
    public SPreference(Context c){
        this.context = c;
        this.name = BASE_NAME;

        setSharedPreference();
    }

    private void setSharedPreference(){
        sharedPreferences = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        Log.d(TAG, "setSharedPreference: ");
    }

    public String readCity(){
        return sharedPreferences.getString(CITY, context.getResources().getString(R.string.moscow));
    }

    public void setCity(String city){
        sharedPreferences.edit().putString(CITY, city).apply();
    }

    public boolean readNeedPressure(){
        return sharedPreferences.getBoolean(NEED_PRESSURE, false);
    }

    public void setNeedPressure(boolean b){
        sharedPreferences.edit().putBoolean(NEED_PRESSURE, b).apply();
    }

    public boolean readNeedSpeed(){
        return sharedPreferences.getBoolean(NEED_SPEED, false);
    }

    public void setNeedSpeed(boolean b){
        sharedPreferences.edit().putBoolean(NEED_SPEED, b).apply();
    }

    public int readTheme(){
        return sharedPreferences.getInt(THEME, MainPresenter.getInstance().THEME_LIGHT);
    }

    public void setTheme(int i){
        sharedPreferences.edit().putInt(THEME, i).commit();
    }

    public int readLanguage(){
        return sharedPreferences.getInt(LANGUAGE, MainPresenter.getInstance().LANGUAGE_EN);
    }

    public void setLanguage(int i){
        sharedPreferences.edit().putInt(LANGUAGE, i).apply();
    }
}
