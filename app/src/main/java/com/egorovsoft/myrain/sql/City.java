package com.egorovsoft.myrain.sql;

public class City {

    /// Предполагаю, что город уникальный, хотя в реальности нужно добавлять связку со страной и краем(областью)
    /// Лучше делать по почтовому индексу.

    /// Вообще идея плохая, т.к. я не смогу отслеживать температуру по дням. По идеи нужно несколько таблиц.
    /// но для примера думаю пойдет.
    private String cityName;
    private float temperature;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
