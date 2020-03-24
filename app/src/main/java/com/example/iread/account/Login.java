package com.example.iread.account;

import com.google.gson.annotations.SerializedName;

class Login {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
