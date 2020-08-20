package com.whitehorse.dailydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.whitehorse.dailydiary.callback.CallbackRegister;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.RegisterData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    androidx.appcompat.widget.AppCompatButton register;
    TextInputEditText fname,lname,email,password,confirm;
    String f_name,l_name,mail,pass,conf;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        register = findViewById(R.id.register);
        fname = findViewById(R.id.fname_et);
        lname = findViewById(R.id.lname_et);
        email = findViewById(R.id.emai_et);
        password = findViewById(R.id.password_et);
        confirm = findViewById(R.id.confirm_password_et);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                try {
                    registration(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void registration(View view) throws Exception{
        f_name = fname.getText().toString().trim();
        l_name = lname.getText().toString().trim();
        mail = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        conf = confirm.getText().toString().trim();
        if(f_name.isEmpty()){
            fname.setError("Cannot be empty");
            return;
        }else if(l_name.isEmpty()){
            fname.setError("Cannot be empty");
            return;
        }else if(!isEmailValid(mail)){
            email.setError("Invalid Format");
            return;
        }else if(pass.isEmpty()){
            password.setError("Cannot be Empty");
            return;
        }else if (!pass.equals(conf)){
            confirm.setError("Does not match");
            return;
        }
       //registration logic
        RegisterData registerData = new RegisterData(mail,pass,f_name,l_name);
        API api = RestAdapter.createAPI();
        Call<CallbackRegister> callbackRegisterCall = api.registerUser(registerData);
        callbackRegisterCall.enqueue(new Callback<CallbackRegister>() {
            @Override
            public void onResponse(Call<CallbackRegister> call, Response<CallbackRegister> response) {
                if(response.isSuccessful()){
                    Log.i("response", "onResponse: Successfull");
                    CallbackRegister callbackRegister = response.body();
                    Toast.makeText(RegisterActivity.this,callbackRegister.getMessage()+"", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i("response", "onResponse: failed");
                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackRegister> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.i("response", "onResponse: failed");
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}