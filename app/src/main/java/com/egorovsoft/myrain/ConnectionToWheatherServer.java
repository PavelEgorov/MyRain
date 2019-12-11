package com.egorovsoft.myrain;

import com.egorovsoft.myrain.api.openweathermap.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionToWheatherServer {
    private static final String API_KEY = "37a0b47b7c853559bc683f29de620736";
    private static final String SERVER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private String city_name;
    private float temperature;
    private int pressure;
    private int humidity;
    private float windSpeed;
    private HttpsURLConnection urlConnection;
    private int error_massage;


//    q={название города}, {код страны}
//    Пример итогового запроса: api.openweathermap.org/data/2.5/weather?q=London,uk

    public ConnectionToWheatherServer(String city){
        city_name = city;
        temperature = 0;
        pressure = 0;
        humidity = 0;
        windSpeed = 0;
    }

    public void refreshData() throws MalformedURLException {
        final URL uri = new URL(SERVER_URL + "q=" + city_name + ",RU&units=metric&appid=" + API_KEY);

        urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
            urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд

            error_massage   =   urlConnection.getResponseCode();
            if (error_massage>=200&&error_massage<=299) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                String result = getLinesWheather(in);
                Gson gson = new Gson();
                WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                displayWeather(weatherRequest);
            }else{
                ///{{ Произошла ошибка
                SetError();
                ///}}
            }
            // преобразование данных запроса в модель

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }

    private void SetError() {
        temperature = 0;
        pressure = 0;
        humidity = 0;
        windSpeed = 0;

        updateActivity();
    }

    private void displayWeather(WeatherRequest weatherRequest) {
        city_name = weatherRequest.getName();
        temperature = weatherRequest.getMain().getTemp();
        pressure = weatherRequest.getMain().getPressure();
        humidity = weatherRequest.getMain().getHumidity();
        windSpeed = weatherRequest.getWind().getSpeed();

        updateActivity();
    }

    private void updateActivity(){
        MainPresenter.getInstance().setTemperature(temperature);
        MainPresenter.getInstance().setError(error_massage);
        MainPresenter.getInstance().setWindSpeed(windSpeed);
        MainPresenter.getInstance().setPressure(pressure);
    }


//    private String getLinesError(InputStream in) {
//        return in.lines().collect(Collectors.joining("\n"));
//    }

    private String getLinesWheather(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    public String getCity_name() {
        return city_name;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void close() {
        if (urlConnection != null){
            urlConnection.disconnect();
        }
    }
}
