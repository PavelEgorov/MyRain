package com.egorovsoft.myrain;

public class Weekend {
    private String dayName;
    private int picture;
    private String temperature;

    public Weekend(String dayName){
        this.dayName = dayName;
        this.picture = 0;
        this.temperature = "5"; // Пока оставляю пустым, это на будущее, когда будет температура получаться с сайта
    }

    public String getDayName(){
        return dayName;
    }

    public void setPicture(int newPicture){
        this.picture = newPicture;
    }

    public int getPicture(){
        return  picture;
    }

    public String getTemperature(){
        return temperature;
    }
}
