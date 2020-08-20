package com.whitehorse.dailydiary.callback;

import com.google.gson.annotations.SerializedName;

public class CallbackLogin {
    @SerializedName("email")
    String email;
    @SerializedName("first name")
    String fname;
    @SerializedName("last_name")
    String lname;

    public CallbackLogin(){}

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
