package com.example.andrew.simpleui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Andrew on 8/2/16.
 */
public class SimpleApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jAPYtIwXuBerHNMCTTEIOmudBhoI8g9UAdoybc1L")
                .server("https://parseapi.back4app.com/")
                .clientKey("sWABDbcifuWrYaCdRIuClGaD3nfZ0xFPWBBrnW73")
                .build());
    }
}
