package com.example.iread;

import android.app.Application;

import com.example.iread.di.ApplicationComponent;
import com.example.iread.di.DaggerApplicationComponent;

public class MyApplication extends Application {
    public ApplicationComponent appComponent = DaggerApplicationComponent.create();
}
