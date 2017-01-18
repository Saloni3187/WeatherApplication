package com.weatherapplication.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.weatherapplication.model.WeatherData;
import com.weatherapplication.model.WeatherDetails;
import com.weatherapplication.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by Saloni on 18/1/17.
 */

public class ParseServerData {
    private WeatherData weatherData;
    private ProgressDialog progress;
    private Context ctx;
    private ArrayList<String> day = new ArrayList<>();
    private ArrayList<String> maxTemp = new ArrayList<>();
    private ArrayList<String> minTemp = new ArrayList<>();
    private ArrayList<String> weatherType = new ArrayList<>();
    private DateUtils date = new DateUtils();
    private WeatherDetails weatherDetails = new WeatherDetails();

    public ParseServerData(Context context){
        this.ctx = context;
    }
    /**
     * Method is used to retrieve data from server and parse data by calling parseServerData(weather data)
     * weatherData list having all the data from server
     */
    /*public WeatherDetails getDataFromServer() {

        progress.setMessage(ctx.getString(R.string._lbl_download));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        WeatherDataServiceInterface.Factory.getInstance().getWeatherData("london").enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i("URL", call.request().url().toString());
                try {
                    weatherData = response.body();
                    parseServerData(weatherData);

                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
            }
        });
            return weatherDetails;
    }*/
    private WeatherDetails parseServerData(WeatherData weatherData) {
        progress.dismiss();

        for (int i = 0; i < weatherData.getList().size(); i++) {
            try {
                day.add(date.getTimeStamp(weatherData.getList().get(i).getDt() + ""));
                maxTemp.add(weatherData.getList().get(i).getTemp().getMax() + "");
                minTemp.add(weatherData.getList().get(i).getTemp().getMin() + "");
                weatherType.add(weatherData.getList().get(i).getWeather().get(0).getMain() + "");
                weatherDetails.setMaxTemp(maxTemp);
                weatherDetails.setMinTemp(minTemp);
                weatherDetails.setType_of_weather(weatherType);
                weatherDetails.setDateValue(day);

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }return weatherDetails;
    }
}
