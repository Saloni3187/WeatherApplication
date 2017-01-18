package com.weatherapplication.controller;

import android.content.Context;

import com.weatherapplication.model.WeatherDetails;
import com.weatherapplication.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**This class parse the local json data and set the data in weather details object for further requirments
 * Created by Saloni on 18/1/17.
 */

public class ParseDataFromLocal {
    private ArrayList<String> day = new ArrayList<>();
    private ArrayList<String> maxTemp = new ArrayList<>();
    private ArrayList<String> minTemp = new ArrayList<>();
    private ArrayList<String> weatherType = new ArrayList<>();
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Context context;
    private DateUtils date = new DateUtils();
    public ParseDataFromLocal(Context ctx){
        this.context = ctx;
    }


    /*
    Method will read the static json from assets folder and pass json object to next method
     */
    public WeatherDetails getDataFromJson() {
        LoadJSONFromAssets jsonData = new LoadJSONFromAssets(context);
        jsonObject = jsonData.getJsonData();
        return parseStaticJsonData(jsonObject);
    }

    /**
     * Method will parse the json object and set the value in weather details obkect
     * @param jsonObject
     * @return
     */
    private WeatherDetails parseStaticJsonData(JSONObject jsonObject) {
        String city=null;
        try {
            if(!jsonObject.equals(null)) {
                jsonArray = jsonObject.getJSONArray("list");
                city = jsonObject.getJSONObject("city").get("name") + "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonArray = jsonObject.getJSONArray("list");
                    day.add(date.getTimeStamp(jsonArray.getJSONObject(i).get("dt") + ""));
                    maxTemp.add(jsonArray.getJSONObject(i).getJSONObject("temp").get("max") + "");
                    minTemp.add(jsonArray.getJSONObject(i).getJSONObject("temp").get("min") + "");
                    weatherType.add(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("main") + "");
                }
            }
        }catch (Exception exc) {
                exc.printStackTrace();
            }

        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setMaxTemp(maxTemp);
        weatherDetails.setMinTemp(minTemp);
        weatherDetails.setType_of_weather(weatherType);
        weatherDetails.setDateValue(day);
        if(!city.equals(null))
            weatherDetails.setCity(city);

        return weatherDetails;

    }
}
