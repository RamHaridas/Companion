package com.whitehorse.dailydiary.data;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("first name")
    String first_name;

    @SerializedName("last name")
    String last_name;

    public RegisterData(String email, String password, String first_name, String last_name) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
