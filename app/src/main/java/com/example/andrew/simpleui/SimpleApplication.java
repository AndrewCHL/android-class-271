package com.example.andrew.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Andrew on 8/2/16.
 */
public class SimpleApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(Order.class);
        ParseObject.registerSubclass(DrinkOrder.class);
        ParseObject.registerSubclass(Drink.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jAPYtIwXuBerHNMCTTEIOmudBhoI8g9UAdoybc1L")
                .server("https://parseapi.back4app.com/")
                .clientKey("sWABDbcifuWrYaCdRIuClGaD3nfZ0xFPWBBrnW73")
                .enableLocalDataStore()
                .build());
    }
}
