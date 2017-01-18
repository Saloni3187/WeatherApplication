package com.weatherapplication.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**This class loads static json from asset folder and retrieve the data as required.
 * Created by Saloni on 17/1/17.
 */

public class LoadJSONFromAssets {
    private Context ctx;

    public LoadJSONFromAssets(Context context) {
        this.ctx =context;
    }

    public JSONObject getJsonData() {
        JSONObject obj = null;
        try {
            obj = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = ctx.getAssets().open("weather_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
