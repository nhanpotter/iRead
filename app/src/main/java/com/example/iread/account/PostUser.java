package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonProperty;

class PostUser {
    public String username;
    public String email;
    public String password;
    @JsonProperty("re_password")
    public String rePassword;

    public PostUser(String username, String email, String password, String rePassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }
}
