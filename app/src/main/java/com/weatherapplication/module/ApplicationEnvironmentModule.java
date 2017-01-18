package com.weatherapplication.module;

import com.weatherapplication.controller.ApplicationEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Saloni on 17/1/17.
 */
@Module
public class ApplicationEnvironmentModule {
    @Provides
    @Singleton
    public ApplicationEnvironment getApplicationEnvironment(){
        return new ApplicationEnvironment();
    }
}
