package com.weatherapplication.controller;

import android.content.Context;

import com.weatherapplication.utils.AssetsPropertyReader;

import java.util.Properties;

/**This class provides the environment set for the application written in property file
 * Created by Saloni on 17/1/17.
 */

public class ApplicationEnvironment {
    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;

    public String getApplicationEnvironment() {
        context = MyApplication.getAppContext();
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("application.properties");
        return p.getProperty("environment");
    }
}
