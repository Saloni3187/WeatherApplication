package com.weatherapplication.interfaces;

import com.weatherapplication.module.ApplicationEnvironmentModule;
import com.weatherapplication.views.WeatherApplicationActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Saloni on 17/1/17.
 */

@Singleton
@Component(modules = {ApplicationEnvironmentModule.class})
public interface DiComponent {
    // to update the fields in your activities
    void inject(WeatherApplicationActivity activity);
}
