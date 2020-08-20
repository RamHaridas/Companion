package com.whitehorse.dailydiary.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.callback.CallbackMoodPost;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.MoodData;
import com.whitehorse.dailydiary.popups.DataUploadSuccessPopup;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Day2Fragment extends Fragment {
    View view;
    Button saveday2;
    NavController navcont;
    CircularImageView[] sleep;
    CircularImageView[] time;
    CircularImageView[] doing;
    CircularImageView[] eat;
    boolean[] sl,ti,doi,ea;
    String sleep_status, relation_status, eat_status, day_status,activity,email;
    SharedPreferences sharedPreferences;
    List<String> daylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_day2, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        Log.i(TAG, "onCreateView: "+email);
        Bundle bundle = getArguments();
        try {
            Log.d("mood", bundle.getString("mood"));
            day_status = bundle.getString("mood");
        }catch (Exception r){}
        initializeciv();
        listners();
        daylist = new ArrayList<>();
        saveday2 = view.findViewById(R.id.saveday2);
        navcont = NavHostFragment.findNavController(Day2Fragment.this);
        saveday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(!checkallfields()){
                        Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(String s : daylist){
                    Log.i("daylist",s);
                }
                try {
                    storeData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DataUploadSuccessPopup dataUploadSuccessPopup = new DataUploadSuccessPopup();
                dataUploadSuccessPopup.show(getActivity().getSupportFragmentManager(),"success");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navcont.navigate(R.id.action_day2Fragment2_to_navigation_home);
                    }
                },2500);

            }
        });
        return view;
    }
    public void initializeciv(){
        sleep = new CircularImageView[4];
        time = new CircularImageView[2];
        eat = new CircularImageView[5];
        doing = new CircularImageView[5];
        sl = new boolean[4];
        ti = new boolean[2];
        ea = new boolean[5];
        doi = new boolean[5];
        sleep[0] = view.findViewById(R.id.sleep1);
        sleep[1] = view.findViewById(R.id.sleep2);
        sleep[2] = view.findViewById(R.id.sleep3);
        sleep[3] = view.findViewById(R.id.sleep4);

        time[0] = view.findViewById(R.id.time1);
        time[1] = view.findViewById(R.id.time2);

        eat[0] = view.findViewById(R.id.food1);
        eat[1] = view.findViewById(R.id.food2);
        eat[2] = view.findViewById(R.id.food3);
        eat[3] = view.findViewById(R.id.food4);
        eat[4] = view.findViewById(R.id.food5);

        doing[0]=view.findViewById(R.id.do1);
        doing[1]=view.findViewById(R.id.do2);
        doing[2]=view.findViewById(R.id.do3);
        doing[3]=view.findViewById(R.id.do4);
        doing[4]=view.findViewById(R.id.do5);

        sl[0] = false;
        sl[1] = false;
        sl[2] = false;
        sl[3] = false;

        ti[0] = false;
        ti[1] = false;

        doi[0] = false;
        doi[1] = false;
        doi[2] = false;
        doi[3] = false;
        doi[4] = false;
    }
    public void listners(){
        sleep[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleep[0].setBorderColor(Color.rgb(255,51,242));
                sleep[1].setBorderColor(Color.WHITE);
                sleep[2].setBorderColor(Color.WHITE);
                sleep[3].setBorderColor(Color.WHITE);
                sleep_status = "sleep early";
                sl[0] = true;
                sl[1] = false;
                sl[2] = false;
                sl[3] = false;
            }
        });
        sleep[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleep[0].setBorderColor(Color.WHITE);
                sleep[1].setBorderColor(Color.rgb(255,51,242));
                sleep[2].setBorderColor(Color.WHITE);
                sleep[3].setBorderColor(Color.WHITE);
                sleep_status = "sleep good";
                sl[0]=false;
                sl[1]=true;
                sl[2]=false;
                sl[3]=false;
            }
        });
        sleep[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleep[0].setBorderColor(Color.WHITE);
                sleep[1].setBorderColor(Color.WHITE);
                sleep[2].setBorderColor(Color.rgb(255,51,242));
                sleep[3].setBorderColor(Color.WHITE);
                sleep_status = "sleep medium";
                sl[0] = false;
                sl[1] = false;
                sl[2] = true;
                sl[3] = false;
            }
        });
        sleep[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleep[0].setBorderColor(Color.WHITE);
                sleep[1].setBorderColor(Color.WHITE);
                sleep[2].setBorderColor(Color.WHITE);
                sleep[3].setBorderColor(Color.rgb(255,51,242));
                sleep_status = "sleep bad";
                sl[0] = false;
                sl[1] = false;
                sl[2] = false;
                sl[3] = true;
            }
        });
        time[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time[0].setBorderColor(Color.rgb(255,51,242));
                time[1].setBorderColor(Color.WHITE);
                relation_status = "family";
                ti[0] = true;
                ti[1] = false;
            }
        });
        time[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time[0].setBorderColor(Color.WHITE);
                time[1].setBorderColor(Color.rgb(255,51,242));
                relation_status = "friend";
                ti[0] = false;
                ti[1] = true;
            }
        });

        eat[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eat[0].setBorderColor(Color.rgb(255,51,242));
                eat[1].setBorderColor(Color.WHITE);
                eat[2].setBorderColor(Color.WHITE);
                eat[3].setBorderColor(Color.WHITE);
                eat[4].setBorderColor(Color.WHITE);
                eat_status = "eat healthy";
                ea[0] = true;
                ea[1] = false;
                ea[2] = false;
                ea[3] = false;
                ea[4] = false;
            }
        });
        eat[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eat[0].setBorderColor(Color.WHITE);
                eat[1].setBorderColor(Color.rgb(255,51,242));
                eat[2].setBorderColor(Color.WHITE);
                eat[3].setBorderColor(Color.WHITE);
                eat[4].setBorderColor(Color.WHITE);
                eat_status = "eat homemade";
                ea[0] = false;
                ea[1] = true;
                ea[2] = false;
                ea[3] = false;
                ea[4] = false;
            }
        });
        eat[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eat[0].setBorderColor(Color.WHITE);
                eat[1].setBorderColor(Color.WHITE);
                eat[2].setBorderColor(Color.rgb(255,51,242));
                eat[3].setBorderColor(Color.WHITE);
                eat[4].setBorderColor(Color.WHITE);
                eat_status = "eat fastfood";
                ea[0] = false;
                ea[1] = false;
                ea[2] = true;
                ea[3] = false;
                ea[4] = false;
            }
        });
        eat[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eat[0].setBorderColor(Color.WHITE);
                eat[1].setBorderColor(Color.WHITE);
                eat[2].setBorderColor(Color.WHITE);
                eat[3].setBorderColor(Color.rgb(255,51,242));
                eat[4].setBorderColor(Color.WHITE);
                eat_status = "eat soda";
                ea[0] = false;
                ea[1] = false;
                ea[2] = false;
                ea[3] = true;
                ea[4] = false;
            }
        });
        eat[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eat[0].setBorderColor(Color.WHITE);
                eat[1].setBorderColor(Color.WHITE);
                eat[2].setBorderColor(Color.WHITE);
                eat[3].setBorderColor(Color.WHITE);
                eat[4].setBorderColor(Color.rgb(255,51,242));
                eat_status = "eat sweets";
                ea[0] = false;
                ea[1] = false;
                ea[2] = false;
                ea[3] = false;
                ea[4] = true;
            }
        });
        doing[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doi[0]){
                    doing[0].setBorderColor(Color.WHITE);
                    //remove("read");
                    doi[0] = false;
                }
                else {
                    doing[0].setBorderColor(Color.rgb(255,51,242));
                    doing[1].setBorderColor(Color.WHITE);
                    doing[2].setBorderColor(Color.WHITE);
                    doing[3].setBorderColor(Color.WHITE);
                    doing[4].setBorderColor(Color.WHITE);
                    //doing[1].setBorderColor(Color.WHITE);
                    doi[0] = true;
                    doi[1] = false;
                    doi[2] = false;
                    doi[3] = false;
                    doi[4] = false;
                    //daylist.add("read");
                    activity = "read";
                }

            }
        });

        doing[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doi[1]){
                    doing[1].setBorderColor(Color.WHITE);
                    //remove("gaming");
                    doi[1] = false;
                }
                else {
                    doing[1].setBorderColor(Color.rgb(255,51,242));
                    doing[0].setBorderColor(Color.WHITE);
                    doing[2].setBorderColor(Color.WHITE);
                    doing[3].setBorderColor(Color.WHITE);
                    doing[4].setBorderColor(Color.WHITE);
                    activity = "gaming";
                    doi[1] = true;
                    doi[0] = false;
                    doi[2] = false;
                    doi[3] = false;
                    doi[4] = false;
                    //daylist.add("gaming");
                }
            }
        });
        doing[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doi[2]){
                    doing[2].setBorderColor(Color.WHITE);
                    //remove("movie");
                    doi[2] = false;
                }
                else {
                    doing[2].setBorderColor(Color.rgb(255,51,242));
                    doing[0].setBorderColor(Color.WHITE);
                    doing[1].setBorderColor(Color.WHITE);
                    doing[3].setBorderColor(Color.WHITE);
                    doing[4].setBorderColor(Color.WHITE);
                    //daylist.add("movie");
                    activity = "movie";
                    doi[2] = true;
                    doi[0] = false;
                    doi[1] = false;
                    doi[3] = false;
                    doi[4] = false;
                }
            }
        });
        doing[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doi[3]){
                    doing[3].setBorderColor(Color.WHITE);
                    //remove("party");
                    doi[3] = false;
                }
                else {
                    doing[3].setBorderColor(Color.rgb(255,51,242));
                    doing[0].setBorderColor(Color.WHITE);
                    doing[1].setBorderColor(Color.WHITE);
                    doing[2].setBorderColor(Color.WHITE);
                    doing[4].setBorderColor(Color.WHITE);
                    //daylist.add("party");
                    activity = "party";
                    doi[3] = true;
                    doi[0] = false;
                    doi[1] = false;
                    doi[2] = false;
                    doi[4] = false;
                }
            }
        });

        doing[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doi[4]){
                    doing[4].setBorderColor(Color.WHITE);
                    //remove("workout");
                    doi[4] = false;
                }
                else {
                    doing[4].setBorderColor(Color.rgb(255,51,242));
                    doing[0].setBorderColor(Color.WHITE);
                    doing[1].setBorderColor(Color.WHITE);
                    doing[2].setBorderColor(Color.WHITE);
                    doing[3].setBorderColor(Color.WHITE);
                    //daylist.add("workout");
                    activity = "workout";
                    doi[4] = true;
                    doi[0] = false;
                    doi[1] = false;
                    doi[2] = false;
                    doi[3] = false;
                }
            }
        });
    }

    // in class use only
    public void remove(String name){
        if(!daylist.isEmpty()){
            try {
                daylist.remove(name);
            }catch (Exception e){}

        }
    }
    
    public boolean checkallfields() throws Exception{
        
        if(sleep_status.isEmpty()){
            return false;
        }else if(relation_status.isEmpty()){
            return false;
        }else if(eat_status.isEmpty()){
            return false;
        }else if(day_status.isEmpty()){
            day_status = "normal";
        }else if (activity.isEmpty()){
            return false;
        }

        return true;
    }

    public void storeData() throws Exception{

        API api = RestAdapter.createAPI();
        MoodData moodData = new MoodData(email,day_status,sleep_status,relation_status,eat_status,activity);
        Call<CallbackMoodPost> call = api.storeMood(moodData);
        call.enqueue(new Callback<CallbackMoodPost>() {
            @Override
            public void onResponse(Call<CallbackMoodPost> call, Response<CallbackMoodPost> response) {
                if(response.isSuccessful()){
                    CallbackMoodPost callbackMoodPost = response.body();
                    if(callbackMoodPost.getDay() != null){
                        //do your thing
                    }

                }else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackMoodPost> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



}