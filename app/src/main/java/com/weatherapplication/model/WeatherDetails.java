package com.weatherapplication.model;

import java.util.ArrayList;

/**
 * Created by Saloni on 18/1/17.
 */

public class WeatherDetails {
    String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    ArrayList<String> minTemp;
    ArrayList<String> maxTemp;
    ArrayList<String> dateValue;
    ArrayList<String> type_of_weather;

    public ArrayList<String> getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(ArrayList<String> minTemp) {
        this.minTemp = minTemp;
    }

    public ArrayList<String> getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(ArrayList<String> maxTemp) {
        this.maxTemp = maxTemp;
    }

    public ArrayList<String> getDateValue() {
        return dateValue;
    }

    public void setDateValue(ArrayList<String> dateValue) {
        this.dateValue = dateValue;
    }

    public ArrayList<String> getType_of_weather() {
        return type_of_weather;
    }

    public void setType_of_weather(ArrayList<String> type_of_weather) {
        this.type_of_weather = type_of_weather;
    }
}
