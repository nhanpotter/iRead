package com.example.iread.account;

import com.example.iread.data.Preference;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthHolder {
    private final String TOKEN_KEY = "TOKEN";
    private Preference preference;

    @Inject
    public AuthHolder(Preference preference) {
        this.preference = preference;
    }

    public String getToken() {
        return preference.getString(TOKEN_KEY);
    }

    public void setToken(String value) {
        preference.putString(TOKEN_KEY, value);
    }

    public String getAuthorization() {
        if (!isLoggedIn())
            return null;
        return "Token " + this.getToken();
    }

    public boolean isLoggedIn() {
        if (this.getToken() == null) {
            return false;
        }
        return true;
    }
}