package com.weatherapplication.controller;

/** This class sets the context for the application
 * Also create Dagger dependency component
 * Created by Saloni on 17/1/17.
 */

import android.app.Application;
import android.content.Context;

import com.weatherapplication.interfaces.DaggerDiComponent;
import com.weatherapplication.interfaces.DiComponent;

public class MyApplication extends Application {
    DiComponent component;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        component = DaggerDiComponent.builder().build();
    }

    public DiComponent getComponent() {
        return component;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
