package com.egorovsoft.myrain;

public final class MainPresenter {
    private static MainPresenter instance = null;
    private static Object syncObj = new Object();

    public static final String CITY_NAME = "city_name";
    public static final String SPEED_VISIBLE = "speed_visible";
    public static final String PRESSURE_VISIBLE = "pressure_visible";
    public static final String THEME_INSTALLED = "theme_installed";


    private String cityName;
    private boolean needPressure;
    private boolean needSpeed;
    private int theme;
    private int language;

    public final int THEME_LIGHT = 1001;
    public final int THEME_DARK = 1002;

    public final int LANGUAGE_EN = 1101;
    public final int LANGUAGE_RU = 1102;

    public MainPresenter(){
        needPressure = false;
        needSpeed = false;
        cityName = "Krasnodar";
        theme = THEME_LIGHT;
        language = LANGUAGE_EN;
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
}
