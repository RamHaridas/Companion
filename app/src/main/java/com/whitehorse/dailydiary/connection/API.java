package com.whitehorse.dailydiary.connection;


import com.whitehorse.dailydiary.callback.CallbackAdvice;
import com.whitehorse.dailydiary.callback.CallbackLogin;
import com.whitehorse.dailydiary.callback.CallbackMoodPost;
import com.whitehorse.dailydiary.callback.CallbackRegister;
import com.whitehorse.dailydiary.data.DayWiseMood;
import com.whitehorse.dailydiary.data.LoginData;
import com.whitehorse.dailydiary.data.MoodData;
import com.whitehorse.dailydiary.data.RegisterData;
import com.whitehorse.dailydiary.data.UserData;
import com.whitehorse.dailydiary.data.WeeklyMoodStatus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("register")
    Call<CallbackRegister> registerUser(@Body RegisterData registerData);


    @POST("login")
    Call<CallbackLogin> loginUser(@Body LoginData loginData);

    @POST("mood")
    Call<CallbackMoodPost> storeMood(@Body MoodData moodData);

    @GET("usermood")
    Call<DayWiseMood> getDayWiseMood(@Query("email")String email);

    @GET("mood")
    Call<WeeklyMoodStatus> getWeeklyMoodData(@Query("email")String email);

    @GET("wake")
    Call<CallbackRegister> wakeUp();

    @GET("login")
    Call<UserData> getUserData(@Query("email")String email);

    @GET("bad")
    Call<CallbackAdvice> getAdviceData(@Query("email")String email);
}
