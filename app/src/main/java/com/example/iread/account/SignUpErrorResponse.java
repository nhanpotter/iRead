package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpErrorResponse {
    public List<String> email;
    public List<String> username;
    public List<String> password;
    @JsonProperty("re_password")
    public List<String> rePassword;
}
