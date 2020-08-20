package com.whitehorse.dailydiary.callback;

import com.google.gson.annotations.SerializedName;

public class CallbackMoodPost {

    @SerializedName("email")
    String email;
    @SerializedName("day_status")
    String day;
    @SerializedName("sleep_status")
    String sleep;
    @SerializedName("time_spending")
    String time;
    @SerializedName("eat_status")
    String eat;
    @SerializedName("activites")
    String activity;
    @SerializedName("date")
    String date;


    public CallbackMoodPost(){}

    public String getEmail() {
        return email;
    }

    public String getDay() {
        return day;
    }

    public String getSleep() {
        return sleep;
    }

    public String getTime() {
        return time;
    }

    public String getEat() {
        return eat;
    }

    public String getActivity() {
        return activity;
    }

    public String getDate() {
        return date;
    }


}
