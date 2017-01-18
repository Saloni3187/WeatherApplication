package com.weatherapplication.interfaces;

import com.weatherapplication.constants.URLConstants;
import com.weatherapplication.model.WeatherData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**This class calls retrofit library to get data from given URL
 * Created by Saloni on 21/10/16.
 */
public interface WeatherDataServiceInterface {
    @GET("daily")
    Call<WeatherData> getWeatherData(@QueryMap(encoded=true) Map<String, String> options);
    //inner class used to create singleton instance
    class Factory{
        private static WeatherDataServiceInterface service;
        public static WeatherDataServiceInterface getInstance(){
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(URLConstants.BASE_URL).
                                        addConverterFactory(GsonConverterFactory.create()).build();

                service = retrofit.create(WeatherDataServiceInterface.class);

                return service;
            }else
                return service;
            }
        }
    }
