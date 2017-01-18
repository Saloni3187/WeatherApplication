package com.weatherapplication.views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherapplication.R;
import com.weatherapplication.constants.AppConstant;
import com.weatherapplication.constants.URLConstants;
import com.weatherapplication.controller.ApplicationEnvironment;
import com.weatherapplication.controller.MyApplication;
import com.weatherapplication.controller.ParseDataFromLocal;
import com.weatherapplication.interfaces.WeatherDataServiceInterface;
import com.weatherapplication.model.WeatherData;
import com.weatherapplication.model.WeatherDetails;
import com.weatherapplication.utils.DateUtils;
import com.weatherapplication.utils.GPSTracker;
import com.weatherapplication.utils.TextToImageUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherApplicationActivity extends AppCompatActivity {
    private JSONArray jsonArray;
    private TextView max_temp;
    private TextView min_temp;
    private TextView type_of_weather;
    private ImageView img_view;
    private DateUtils date = new DateUtils();
    private TextView name_of_the_day;
    private WeatherData weatherData;
    private Boolean isGPSOn = false;
    private String injected;
    private ArrayList<String> day = new ArrayList<>();
    private ArrayList<String> maxTemp = new ArrayList<>();
    private ArrayList<String> minTemp = new ArrayList<>();
    private ArrayList<String> weatherType = new ArrayList<>();
    private ProgressDialog progress;
    private int daysToForecast;
    private TextView location_name;
    private String cityName;
    private Boolean isAvailable = false;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Inject
    ApplicationEnvironment applicationEnvironment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = new ProgressDialog(this);
        //get the application environment through DI
        ((MyApplication) getApplication()).getComponent().inject(this);
        injected = applicationEnvironment.getApplicationEnvironment();
        //show data as per set environment
        showDataAsApplicationEnvironment();
    }

    /**
     * Method will get the injected module as per application environment
     */
    private void showDataAsApplicationEnvironment() {
        if (injected.equals(AppConstant.DEV_ENV)) {
            daysToForecast = 5; //5 days forecast available in json file
            getDataFromJson();
            dataInViews(0);
        } else {
            daysToForecast = AppConstant.NUMBER_OF_DAYS_FORECAST;
            if (checkNetworkConnectivity())
                getIfGPSOn();
            else {
                getDataFromJson();
                dataInViews(0);
                Toast.makeText(WeatherApplicationActivity.this, getString(R.string._msg_no_network_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //This method will get the data from the parsed json
    private void getDataFromJson() {
        daysToForecast = 5;
        ParseDataFromLocal parseDataFromLocal = new ParseDataFromLocal(this);
        WeatherDetails weatherDetails = parseDataFromLocal.getDataFromJson();
        maxTemp = weatherDetails.getMaxTemp();
        minTemp = weatherDetails.getMaxTemp();
        day = weatherDetails.getDateValue();
        cityName = weatherDetails.getCity();
        weatherType = weatherDetails.getType_of_weather();
        setDataInViewsFromJson();
    }

    //This method will inflate the view based on number of forecast
    private void setDataInViewsFromJson() {
        for (int i = 0; i < daysToForecast; i++) {
            setDataInView(i);
        }
    }

    /**
     * Method will inflate the view based on the forecast data.
     * and set data into the respective view
     *
     * @param i
     */
    private void setDataInView(int i) {
        LinearLayout parentPanel = (LinearLayout) findViewById(R.id.ll_bottom);
        View v = getLayoutInflater().inflate(R.layout.content_bottom_layout, null);
        TextView dt = (TextView) v.findViewById(R.id.txt_day);
        max_temp = (TextView) v.findViewById(R.id.txt_max);
        min_temp = (TextView) v.findViewById(R.id.txt_min);
        img_view = (ImageView) v.findViewById(R.id.img_weather);
        int resourceId = TextToImageUtil.getImageDrawable(weatherType.get(i).toLowerCase());
        img_view.setBackgroundResource(resourceId);
        try {
            dt.setText(day.get(i));
            max_temp.setText(maxTemp.get(i));
            min_temp.setText(minTemp.get(i));
            v.setId(R.id.txt_day + i);

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        parentPanel.addView(v);
    }

    public void getClickedDayDetails(View v) {
        for (int i = 0; i < daysToForecast; i++) {
            if (v.getId() == R.id.txt_day + i) {
                dataInViews(i);
            }
        }
    }

    /**
     * method will set parsed data in views based on the selected tile
     *
     * @param i
     */
    private void dataInViews(int i) {
        location_name = (TextView) findViewById(R.id.txt_title_location);
        max_temp = (TextView) findViewById(R.id.txt_max_temp);
        min_temp = (TextView) findViewById(R.id.txt_min_temp);
        type_of_weather = (TextView) findViewById(R.id.txt_temp_type);
        name_of_the_day = (TextView) findViewById(R.id.txt_title_day);
        img_view = (ImageView) findViewById(R.id.img_weather_type);
        try {
            name_of_the_day.setText(day.get(i));
            max_temp.setText(maxTemp.get(i));
            min_temp.setText(minTemp.get(i));
            location_name.setText(cityName);
            type_of_weather.setText(weatherType.get(i));
            int resourceId = TextToImageUtil.getImageDrawable(weatherType.get(i).toLowerCase());
            img_view.setBackgroundResource(resourceId);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Method is used to retrieve data from server and parse data by calling parseServerData(weather data)
     * weatherData list having all the data from server
     */
    public void getDataFromServer(HashMap<String, String> details) {

        //check whether network is available then only download data from services

        progress.setMessage(getResources().getString(R.string._lbl_download));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        WeatherDataServiceInterface.Factory.getInstance().getWeatherData(details).enqueue(new Callback<WeatherData>() {
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
                getDataFromJson();
                setDataInView(0);
                Toast.makeText(WeatherApplicationActivity.this, getString(R.string._msg_service_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    Method will check the status of GPS
    if enables get the locaion
    else ask user to enable the gps, if user press on cancel, it will show the static data
     */
    private void getIfGPSOn() {
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            double lat = gpsTracker.getLatitude();
            double lng = gpsTracker.getLongitude();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
                cityName = addresses.get(0).getLocality();
                HashMap<String, String> options = new HashMap<>();
                options.put("q", cityName);
                options.put("cnt", AppConstant.NUMBER_OF_DAYS_FORECAST + "");
                options.put("APPID", URLConstants.APPID);
                getDataFromServer(options);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getResources().getString(R.string._lbl_GPS_Message))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string._lbl_enable_GPS),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                    finish();
                                    isGPSOn = true;
                                }
                            });
            alertDialogBuilder.setNegativeButton(getString(R.string._lbl_cancel_GPS),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            getDataFromJson();
                            dataInViews(0);
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

    /**
     * Method will parse server data and save
     * for different views
     *
     * @param weatherData
     */
    private void parseServerData(WeatherData weatherData) {
        progress.dismiss();
        for (int i = 0; i < weatherData.getList().size(); i++) {
            try {
                day.add(date.getTimeStamp(weatherData.getList().get(i).getDt() + ""));
                maxTemp.add(weatherData.getList().get(i).getTemp().getMax() + "");
                minTemp.add(weatherData.getList().get(i).getTemp().getMin() + "");
                weatherType.add(weatherData.getList().get(i).getWeather().get(0).getMain() + "");
                setDataInView(i);
                dataInViews(0);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }

    private Boolean checkNetworkConnectivity() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (wifi.isWifiEnabled() || (activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            isAvailable = true;
        }
        return isAvailable;
    }
}
