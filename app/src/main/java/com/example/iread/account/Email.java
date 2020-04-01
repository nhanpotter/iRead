package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {
    public String email;

    public Email(String email) {
        this.email = email;
    }
}
