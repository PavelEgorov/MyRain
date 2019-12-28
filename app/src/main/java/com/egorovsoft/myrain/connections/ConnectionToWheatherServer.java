package com.egorovsoft.myrain.connections;

import android.util.Log;

import com.egorovsoft.myrain.MainPresenter;
import com.egorovsoft.myrain.api.openweathermap.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionToWheatherServer {
    private static final String TAG = "ConnectionToWheatherServer";

    private static final String API_KEY = "37a0b47b7c853559bc683f29de620736";
    private static final String SERVER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String SERVER_URI = "https://api.openweathermap.org/";

    private String city_name;
    private float temperature;
    private int pressure;
    private int humidity;
    private float windSpeed;
    private HttpsURLConnection urlConnection;
    private int error_massage;

    private Retrofit retrofit;
    private OpenWheatherMapFactory weatherRequestCall;

    public ConnectionToWheatherServer(String city){
        Log.d(TAG, "ConnectionToWheatherServer: ");
        
        city_name = city;
        temperature = 0;
        pressure = 0;
        humidity = 0;
        windSpeed = 0;
        error_massage = 200;

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherRequestCall = retrofit.create(OpenWheatherMapFactory.class);
    }

    public void refreshDataRetrofit(){
        Log.d(TAG, "refreshDataRetrofit: ");
        /// refreshDataRetrofit вызывается в отдельном потоке, по этому я не делаю асинхронный вызов.
        Call<WeatherRequest> call = weatherRequestCall.refreshDataRetrofit(city_name + ",ru", "metric", API_KEY);
        try {
            Response<WeatherRequest> weatherRequest = call.execute();
            error_massage = weatherRequest.code();

            if (weatherRequest.isSuccessful()) {
                displayWeather(weatherRequest.body());
            }
            else{
                SetError();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void refreshDataRetrofitLocation(double latitude, double longitude){

        //api.openweathermap.org/data/2.5/weather?lat=35&lon=139
        Log.d(TAG, "refreshDataRetrofitLocation: ");
        /// refreshDataRetrofit вызывается в отдельном потоке, по этому я не делаю асинхронный вызов.
        Call<WeatherRequest> call = weatherRequestCall.refreshDataRetrofitLocation(Double.toString(latitude), Double.toString(longitude), API_KEY);
        try {
            Response<WeatherRequest> weatherRequest = call.execute();
            error_massage = weatherRequest.code();

            if (weatherRequest.isSuccessful()) {
                displayWeather(weatherRequest.body());
            }
            else{
                SetError();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
