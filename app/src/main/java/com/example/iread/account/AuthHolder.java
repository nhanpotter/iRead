package com.example.iread.account;

import javax.inject.Inject;

class AuthHolder {
    private String token = null;

    @Inject
    public AuthHolder() {}

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getAuthorization() {
        if (!isLoggedIn())
            return null;
        return "Token: " + this.token;
    }

    public boolean isLoggedIn() {
        if (this.token == null) {
            return false;
        }
        return true;
    }
}
