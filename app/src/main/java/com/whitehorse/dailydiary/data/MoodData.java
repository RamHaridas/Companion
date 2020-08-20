package com.whitehorse.dailydiary.data;

import com.google.gson.annotations.SerializedName;

public class MoodData {

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
    @SerializedName("activities")
    String activity;

    public MoodData(String email, String day, String sleep, String time, String eat, String activity) {
        this.email = email;
        this.day = day;
        this.sleep = sleep;
        this.time = time;
        this.eat = eat;
        this.activity = activity;
    }

}
