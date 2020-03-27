package com.example.iread;

import android.app.Application;
import android.content.Context;

import com.example.iread.di.ApplicationComponent;
import com.example.iread.di.DaggerApplicationComponent;

public class MyApplication extends Application {
    public static Context applicationContext;
    public ApplicationComponent appComponent = DaggerApplicationComponent.create();

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}
