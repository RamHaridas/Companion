package com.whitehorse.dailydiary.data;

import com.whitehorse.dailydiary.callback.CallbackMoodPost;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayWiseMood {
    @SerializedName("moods")
    List<CallbackMoodPost> callbackMoodPostList;

    public DayWiseMood(){}

    public List<CallbackMoodPost> getCallbackMoodPostList() {
        return callbackMoodPostList;
    }
}
