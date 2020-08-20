package com.whitehorse.dailydiary.ui.advice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.callback.CallbackAdvice;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdviceFragment extends Fragment {
    View root;
    String email;
    LottieAnimationView anim1,anim2,anim3,anim4;
    TextView text1,text2,text3,text4;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_advice, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        anim1 = root.findViewById(R.id.adviceanim1);
        anim2 = root.findViewById(R.id.adviceanim2);
        anim3 = root.findViewById(R.id.adviceanim3);
        anim4 = root.findViewById(R.id.adviceanim4);
        text1 = root.findViewById(R.id.advicetv1);
        text2 = root.findViewById(R.id.advicetv2);
        text3 = root.findViewById(R.id.advicetv3);
        text4 = root.findViewById(R.id.advicetv4);
        try {
            getAdviceData(email);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getAdviceData(String email) throws Exception {
        if (email.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        API api = RestAdapter.createAPI();
        Call<CallbackAdvice> call = api.getAdviceData(email);
        call.enqueue(new Callback<CallbackAdvice>() {
            @Override
            public void onResponse(Call<CallbackAdvice> call, Response<CallbackAdvice> response) {
                if(response.isSuccessful()){
                    CallbackAdvice data = response.body();
/*                    mList = dayWiseMood.getCallbackMoodPostList();
                    mhomeAdapter = new HomeAdapter(getContext(),mList);
                    homeRR = view.findViewById(R.id.homerecycle);
                    homeRR.setLayoutManager(new LinearLayoutManager(getContext()));
                    homeRR.setHasFixedSize(true);
                    homeRR.setAdapter(mhomeAdapter);
                    progress.setVisibility(View.INVISIBLE);*/
                    List<String> l = data.getMin();
                    setData(l.get(0),l.get(1),l.get(2),l.get(3));
                }
            }

            @Override
            public void onFailure(Call<CallbackAdvice> call, Throwable t) {

            }
        });
    }
    public void setData(String s1,String s2,String s3,String s4){

        switch (s1){
            case "sleep early":
                text1.setText("You need to sleep better");
                break;
            case "sleep good":
                text1.setText("You sleep well but can do better");
                break;
            case "sleep medium":
                text1.setText("Your sleep has been nice");
                break;
            case "sleep bad":
                text1.setText("Your sleep has been great");
                break;
        }
        switch (s2){
            case "family":
                text2.setText("You should try spending more time with your family");
                break;
            case "friend":
                text2.setText("You should try spending more time with your friends");
                break;
        }
        switch (s3){
            case "eat healty":
            case "eat homemade":
                text3.setText("It's observed that you have been eating too much unhealthy");
                break;
            case "eat fastfood":
            case "eat sweets":
            case "eat soda":
                text3.setText("Your eating habits seem to be alright!");
                break;
        }
        switch (s4){
            case "read":
                text4.setText("You should try more reading, it's really good");
                break;
            case "gaming":
                text4.setText("Too much gaming can be harmful for your eyes and mental health!");
                break;
            case "movie":
                text4.setText("Watching movies too much can affect your eyes!");
                break;
            case "party":
                text4.setText("Party hard but work harder too!");
                break;
            case "workout":
                text4.setText("You must be getting ripped, take a day off from gym!");
                break;
        }
    }
}