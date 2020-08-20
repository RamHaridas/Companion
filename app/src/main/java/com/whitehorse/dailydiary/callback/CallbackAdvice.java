package com.whitehorse.dailydiary.callback;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallbackAdvice {
    @SerializedName("min")
    List<String> min;

    public List<String> getMin() {
        return min;
    }
}
