package com.whitehorse.dailydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.whitehorse.dailydiary.callback.CallbackRegister;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000; ///set to 3000

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;
    SharedPreferences isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        isLogin = getSharedPreferences("data",MODE_PRIVATE);


        /*topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogin.getBoolean("login",false)){
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent1 = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
            }
        },SPLASH_SCREEN);
        wakeDyno();
    }

    public void wakeDyno(){
        API api = RestAdapter.createAPI();

        Call<CallbackRegister> call = api.wakeUp();
        call.enqueue(new Callback<CallbackRegister>() {
            @Override
            public void onResponse(Call<CallbackRegister> call, Response<CallbackRegister> response) {

            }

            @Override
            public void onFailure(Call<CallbackRegister> call, Throwable t) {

            }
        });
    }
}