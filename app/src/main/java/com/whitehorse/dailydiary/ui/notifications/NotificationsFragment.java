package com.whitehorse.dailydiary.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {


    Button button;
    View view;
    LottieAnimationView lottieAnimationView;
    TextView fname,email,lastname,about;
    SharedPreferences sharedPreferences;
    String _email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        _email = sharedPreferences.getString("email","");
        lottieAnimationView = view.findViewById(R.id.progress);
        lottieAnimationView.setVisibility(View.VISIBLE);
        button = view.findViewById(R.id.logout_button);
        fname = view.findViewById(R.id.first_name);
        lastname = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.email_et);
        about = view.findViewById(R.id.about_us);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutPopup logoutPopup = new LogoutPopup();
                logoutPopup.show(getActivity().getSupportFragmentManager(),"logout");
            }
        });
        try {
            getData(_email);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NotificationsFragment.this).navigate(R.id.action_navigation_notifications_to_nav_about);
            }
        });
        return view;
    }

    public void getData(String _email) throws Exception{
        if(_email.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        API api = RestAdapter.createAPI();
        Call<UserData> call = api.getUserData(_email);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    UserData userData = response.body();
                    email.setText(userData.getEmail());
                    fname.setText(userData.getFull_name());
                    lastname.setText(userData.getLast_name());
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                lottieAnimationView.setVisibility(View.INVISIBLE);
                t.printStackTrace();
            }
        });
    }
}