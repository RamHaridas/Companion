package com.whitehorse.dailydiary.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.callback.CallbackMoodPost;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.DayWiseMood;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    CardView addcard;
    View view;
    NavController navcont;
    List<CallbackMoodPost> mList;
    HomeAdapter mhomeAdapter;
    RecyclerView homeRR;
    LottieAnimationView progress;
    SharedPreferences sharedPreferences;
    String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        addcard = view.findViewById(R.id.addCard);
        progress = view.findViewById(R.id.progress_home);
        progress.setVisibility(View.VISIBLE);
        mList = new ArrayList<CallbackMoodPost>();
        navcont = NavHostFragment.findNavController(HomeFragment.this);
        /*mList=new ArrayList<>();
        mList.add(new DayItem(R.drawable.ic_launcher_background,"test","test",false,"test\ntest\ntest\ntest\ntest\n"));
        mList.add(new DayItem(R.drawable.ic_launcher_background,"test","test",false,"test"));
        mList.add(new DayItem(R.drawable.ic_launcher_background,"test","test",false,"test"));
        mList.add(new DayItem(R.drawable.ic_launcher_background,"test","test",false,"test"));
        mhomeAdapter=new HomeAdapter(getContext(),mList);
        homeRR=view.findViewById(R.id.homerecycle);
        homeRR.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRR.setHasFixedSize(true);
        homeRR.setAdapter(mhomeAdapter);*/
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navcont.navigate(R.id.action_navigation_home_to_day1Fragment);
            }
        });

        try {
            getWeeklyData(email);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void getWeeklyData(String email) throws Exception {
        if (email.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        API api = RestAdapter.createAPI();
        Call<DayWiseMood> call = api.getDayWiseMood(email);
        call.enqueue(new Callback<DayWiseMood>() {
            @Override
            public void onResponse(Call<DayWiseMood> call, Response<DayWiseMood> response) {
                if(response.isSuccessful()){
                    DayWiseMood dayWiseMood = response.body();
                    mList = dayWiseMood.getCallbackMoodPostList();
                    mhomeAdapter = new HomeAdapter(getContext(),mList);
                    homeRR = view.findViewById(R.id.homerecycle);
                    homeRR.setLayoutManager(new LinearLayoutManager(getContext()));
                    homeRR.setHasFixedSize(true);
                    homeRR.setAdapter(mhomeAdapter);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DayWiseMood> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });
    }
}