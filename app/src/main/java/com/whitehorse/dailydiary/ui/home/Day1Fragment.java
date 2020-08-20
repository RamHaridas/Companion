package com.whitehorse.dailydiary.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.whitehorse.dailydiary.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Day1Fragment extends Fragment {
    View view;
    Button nextday1;
    NavController navcont;
    CircularImageView[] moods;
    String day_status;
    boolean[] mo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day1, container, false);

        initializeciv();
        listeners();
        day_status = "";
        nextday1 = view.findViewById(R.id.day1next);
        navcont = NavHostFragment.findNavController(Day1Fragment.this);
        nextday1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day_status.isEmpty()){
                    Toast.makeText(getContext(), "Please tell us how you feel?", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle args = new Bundle();
                args.putString("mood",day_status);
                navcont.navigate(R.id.action_day1Fragment_to_day2Fragment2,args);
            }
        });
        return view;
    }
    public void initializeciv(){
        moods = new CircularImageView[3];
        mo = new boolean[3];
        moods[0] = view.findViewById(R.id.greate);
        moods[1] = view.findViewById(R.id.normal);
        moods[2] = view.findViewById(R.id.sad);
        mo[0] = false;
        mo[1] = false;
        mo[2] = false;
    }
    public void listeners(){
        moods[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moods[0].setBorderColor(Color.GREEN);
                moods[1].setBorderColor(Color.WHITE);
                moods[2].setBorderColor(Color.WHITE);
                day_status = "great";
                mo[0] = true;
                mo[1] = false;
                mo[2] = false;
            }
        });
        moods[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moods[0].setBorderColor(Color.WHITE);
                moods[1].setBorderColor(Color.GREEN);
                moods[2].setBorderColor(Color.WHITE);
                day_status = "normal";
                mo[0] = false;
                mo[1] = true;
                mo[2] = false;
            }
        });
        moods[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moods[0].setBorderColor(Color.WHITE);
                moods[1].setBorderColor(Color.WHITE);
                moods[2].setBorderColor(Color.GREEN);
                day_status = "sad";
                mo[0] = false;
                mo[1] = false;
                mo[2] = true;
            }
        });

    }
}