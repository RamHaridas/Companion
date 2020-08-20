package com.whitehorse.dailydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.whitehorse.dailydiary.callback.CallbackLogin;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.LoginData;
import com.whitehorse.dailydiary.popups.SuccessPopup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button mLoginButton;
    androidx.appcompat.widget.AppCompatButton btn,register;
    TextInputEditText username, password;
    private static String user, pass;
    TextView textView;
    SharedPreferences data;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        data = getSharedPreferences("data",MODE_PRIVATE);
        editor = data.edit();

        /*if(isLogin.getBoolean("login",false)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }*/
        register = findViewById(R.id.register_btn);
        textView = findViewById(R.id.notice);
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        user = "";
        pass = "";


        mLoginButton = findViewById(R.id.signin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                try {
                    signIn(v);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });
        //generateDeviceId();
    }

    public void signIn(View view) throws Exception{
        textView.setText("Please Wait.....");
        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        if(user.isEmpty()){
            username.setError("Cannot be empty");
            return;
        }else if(pass.isEmpty()){
            password.setError("Cannot be empty");
            return;
        }

        // login logic
        API api = RestAdapter.createAPI();
        LoginData loginData = new LoginData(user,pass);
        Call<CallbackLogin> callbackLoginCall = api.loginUser(loginData);
        callbackLoginCall.enqueue(new Callback<CallbackLogin>() {
            @Override
            public void onResponse(Call<CallbackLogin> call, Response<CallbackLogin> response) {
                if(response.isSuccessful()){
                    CallbackLogin callbackLogin = response.body();
                    if (callbackLogin.getEmail() != null){
                        editor.putBoolean("login",true);
                        editor.apply();
                        editor.putString("email", callbackLogin.getEmail());
                        editor.apply();
                        SuccessPopup successPopup = new SuccessPopup();
                        successPopup.show(getSupportFragmentManager(),"success");
                    }else{
                        textView.setText("Invalid Username or password");
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    textView.setText("Invalid Username or password");
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackLogin> call, Throwable t) {
                textView.setText("Invalid Username or password");
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void listeners(){
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}