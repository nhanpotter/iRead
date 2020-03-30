package com.example.iread.data;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.iread.MyApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Preference {
    private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.applicationContext);

    private SharedPreferences.Editor editor = sharedPreferences.edit();

    @Inject
    public Preference() {}

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).apply();
    }
}