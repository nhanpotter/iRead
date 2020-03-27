package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public int id;
    public String email;
    public String username;
    public boolean is_active;
}
