package com.whitehorse.dailydiary.data;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("first name")
    String full_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("email")
    String email;
    String password;

    public UserData(){}


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
