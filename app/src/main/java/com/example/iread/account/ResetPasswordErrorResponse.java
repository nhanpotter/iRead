package com.example.iread.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class ResetPasswordErrorResponse {
    public List<String> email;

}
