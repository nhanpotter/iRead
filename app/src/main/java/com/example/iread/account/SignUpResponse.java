package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class SignUpResponse {
    // Successful (username, email, id)
    public int id;
    public String username;
    public String email;
    // Fail (username, email, password, re_password)
    public String password;
    @JsonProperty("re_password")
    public String rePassword;
}
