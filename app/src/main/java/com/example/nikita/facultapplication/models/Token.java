package com.example.nikita.facultapplication.models;

import com.google.gson.annotations.SerializedName;

public class Token {

   @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getToken() {
        return accessToken;
    }
}
