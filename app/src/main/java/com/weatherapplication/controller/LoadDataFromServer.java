package com.weatherapplication.controller;

import com.weatherapplication.model.WeatherData;

/**
 * Created by Saloni on 17/1/17.
 */

public class LoadDataFromServer {
    private WeatherData weatherDetailsData;
    /**
     * Method is used to retrieve data from server and parse data by calling parseServerData(weather data)
     * weatherData list having all the data from server
     */
    public void getDataFromServer() {

    }
    /*{

        WeatherDataServiceInterface.Factory.getInstance().getWeatherData().enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i("URL", call.request().url().toString());
                try {
                    weatherDetailsData = response.body();
                }catch (Exception exc){
                    exc.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
            }
        });
        return weatherDetailsData;
    }*/
}
