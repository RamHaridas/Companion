package com.whitehorse.dailydiary.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.connection.API;
import com.whitehorse.dailydiary.connection.RestAdapter;
import com.whitehorse.dailydiary.data.DailyData;
import com.whitehorse.dailydiary.data.WeeklyMoodStatus;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {
    View root;
    TextView weekmood,sleep_txt,eat_txt,times_txt,activities_txt;
    CircularImageView moodIV;
    PieChart moodpc;
    PieChart sleeplc;
    PieChart eatingbc, timebc, activitybc;
    LottieAnimationView friends_anim,family_anim,progress;
    private float[] mpyData = {1f, 2f, 4f};
    private String[] mpxData = {"Normal","Great","Sad"};
    List<DailyData> mList;
    SharedPreferences sharedPreferences;
    String email;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        mList = new ArrayList<>();
        initialize();

        try {
            getWeeklyData(email);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getWeeklyData(String email) throws Exception {
        if(email.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong, please login again", Toast.LENGTH_SHORT).show();
            return;
        }
        API api = RestAdapter.createAPI();
        Call<WeeklyMoodStatus> call = api.getWeeklyMoodData(email);
        call.enqueue(new Callback<WeeklyMoodStatus>() {
            @Override
            public void onResponse(Call<WeeklyMoodStatus> call, Response<WeeklyMoodStatus> response) {
                if (response.isSuccessful()){
                    WeeklyMoodStatus weeklyMoodStatus = response.body();
                    Log.i(TAG, "onResponse: "+weeklyMoodStatus.getGreat());
                    //fetching the required count from database and passing it on to create the respective  pie charts
                    setAnim(weeklyMoodStatus.getFamily(),weeklyMoodStatus.getFriend());
                    setMoodPie(weeklyMoodStatus.getGreat(),weeklyMoodStatus.getNormal(),weeklyMoodStatus.getSad());
                    max(weeklyMoodStatus.getGreat(),weeklyMoodStatus.getNormal(),weeklyMoodStatus.getSad());
                    setSleepLine(weeklyMoodStatus.getSleep_early(),weeklyMoodStatus.getSleep_good(),weeklyMoodStatus.getSleep_medium(),weeklyMoodStatus.getSleep_bad());
                    setEatBar(weeklyMoodStatus.getEat_healty(),weeklyMoodStatus.getEat_homemade(),weeklyMoodStatus.getEat_fastfood(),weeklyMoodStatus.getEat_soda(),weeklyMoodStatus.getEat_sweets());
                    setAcitvitiesPie(weeklyMoodStatus.getRead(),weeklyMoodStatus.getGaming(),weeklyMoodStatus.getMovie(),weeklyMoodStatus.getParty(),weeklyMoodStatus.getWorkout());
                }else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<WeeklyMoodStatus> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong, please login again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void  initialize(){
        //moodIV=root.findViewById(R.id.wmoodiv);
        weekmood = root.findViewById(R.id.week);
        moodpc = root.findViewById(R.id.moodpie);
        sleeplc = root.findViewById(R.id.sleepline);
        eatingbc = root.findViewById(R.id.eatingbar);
        timebc = root.findViewById(R.id.timebar);
        activitybc = root.findViewById(R.id.activitybar);
        sleep_txt = root.findViewById(R.id.sleepthis);
        eat_txt = root.findViewById(R.id.eating);
        times_txt = root.findViewById(R.id.times);
        friends_anim = root.findViewById(R.id.animationViewFriends);
        family_anim = root.findViewById(R.id.animationViewFamily);
        activities_txt = root.findViewById(R.id.activitytext);
        progress = root.findViewById(R.id.progress_home);
        progress.setVisibility(View.VISIBLE);
    }

    public void setMoodPie(int good,int normal,int sad){

        moodpc.setRotationEnabled(true);
        moodpc.setTransparentCircleAlpha(0);
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        mpyData[0] = (float)good;
        mpyData[1] = (float)normal;
        mpyData[2] = (float)sad;
        String empty = "";
        if(good == 0){
            mpxData[0] = empty;
        }
        if(sad == 0){
            mpxData[2] = empty;
        }
        if(normal == 0){
            mpxData[1] = empty;
        }
        for(int i = 0; i < mpyData.length; i++){
            yEntrys.add(new PieEntry(mpyData[i] , mpxData[i] , i));
        }
        for(int i = 1; i < mpxData.length; i++){
            xEntrys.add(mpxData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Moods");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueLineColor(Color.BLACK);
        /*Description description = new Description();
        description.setText("nigga");
        moodpc.setDescription(description);*/
        moodpc.getDescription().setEnabled(false);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);
        //add legend to chart
        Legend legend = moodpc.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        moodpc.setEntryLabelColor(Color.BLACK);
        moodpc.setData(pieData);
        moodpc.animateX(1500);
        moodpc.animateY(1500);
        moodpc.invalidate();

    }

    public void setSleepLine(int early, int good, int med, int bad){

        setSleepText(early,good,med,bad);
        float[] mpyData = {0,0,0,0};
        String[] mpxData = {"Early","Good","Medium","Bad"};
        sleeplc.setRotationEnabled(true);
        sleeplc.setTransparentCircleAlpha(0);
        Log.d(TAG, "addDataSet started");
        String empty = " ";
        if(early == 0){
            Log.i("zero check","early 0");
            mpxData[0] = empty;
        }
        if(good == 0){
            Log.i("zero check","good 0");
            mpxData[1] = empty;
        }
        if(med == 0){
            Log.i("zero check","med 0");
            mpxData[2] = empty;
        }
        if(bad == 0){
            Log.i("zero check","bad 0");
            mpxData[3] = empty;
        }
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        mpyData[0] = (float)early;
        mpyData[1] = (float)good;
        mpyData[2] = (float)med;
        mpyData[3] = (float)bad;
        for(int i = 0; i < mpyData.length; i++){
            yEntrys.add(new PieEntry(mpyData[i] , mpxData[i] , i));
        }
        for(int i = 1; i < mpxData.length; i++){
            xEntrys.add(mpxData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Moods");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueLineColor(Color.BLACK);
        /*Description description = new Description();
        description.setText("nigga");
        moodpc.setDescription(description);*/
        sleeplc.getDescription().setEnabled(false);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(106,133,66));
        colors.add(Color.rgb(61,145,244));
        colors.add(Color.rgb(149,43,96));
        colors.add(Color.rgb(241,182,32));
        pieDataSet.setColors(colors);
        //add legend to chart
        Legend legend = sleeplc.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        sleeplc.setEntryLabelColor(Color.BLACK);
        sleeplc.setData(pieData);
        sleeplc.animateX(1500);
        sleeplc.animateY(1500);
        sleeplc.invalidate();
    }

    public void setEatBar(int healthy, int homemade, int fastfood, int soda, int sweets){

        setEatText(healthy, homemade, fastfood, soda, sweets);
        float[] mpyData = {0,0,0,0,0};
        String[] mpxData = {"Healthy","Home made","Fast Food","Soda","Sweets"};
        eatingbc.setRotationEnabled(true);
        eatingbc.setTransparentCircleAlpha(0);
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        mpyData[0] = (float)healthy;
        mpyData[1] = (float)homemade;
        mpyData[2] = (float)fastfood;
        mpyData[3] = (float)soda;
        mpyData[4] = (float)sweets;
        String empty = "";
        if(healthy == 0){
            mpxData[0] = empty;
        }
        if(homemade == 0){
            mpxData[1] = empty;
        }
        if(fastfood == 0){
            mpxData[2] = empty;
        }
        if(soda == 0){
            mpxData[3] = empty;
        }
        if(sweets == 0){
            mpxData[4] = empty;
        }

        for(int i = 0; i < mpyData.length; i++){
            yEntrys.add(new PieEntry(mpyData[i] , mpxData[i] , i));
        }
        for(int i = 1; i < mpxData.length; i++){
            xEntrys.add(mpxData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Moods");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueLineColor(Color.BLACK);
        /*Description description = new Description();
        description.setText("nigga");
        moodpc.setDescription(description);*/
        eatingbc.getDescription().setEnabled(false);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#F51720"));
        colors.add(Color.parseColor("#A8E10C"));
        colors.add(Color.parseColor("#F8D210"));
        colors.add(Color.parseColor("#2FF3E0"));
        colors.add(Color.parseColor("#B8EE3D"));
        pieDataSet.setColors(colors);
        //add legend to chart
        Legend legend = eatingbc.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        eatingbc.setEntryLabelColor(Color.BLACK);
        eatingbc.setData(pieData);
        eatingbc.animateX(1500);
        eatingbc.animateY(1500);
        eatingbc.invalidate();
    }

    public void setAcitvitiesPie(int read, int gaming, int movie, int party, int workout){
        setActivitiesText(read, gaming, movie, party, workout);
        float[] mpyData = {0,0,0,0,0};
        String[] mpxData = {"Reading","Gaming","Movie","Party","Workout"};
        eatingbc.setRotationEnabled(true);
        eatingbc.setTransparentCircleAlpha(0);
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        mpyData[0] = (float)read;
        mpyData[1] = (float)gaming;
        mpyData[2] = (float)movie;
        mpyData[3] = (float)party;
        mpyData[4] = (float)workout;
        String empty = "";
        if(read == 0){
            mpxData[0] = empty;
        }
        if(gaming == 0){
            mpxData[1] = empty;
        }
        if(movie == 0){
            mpxData[2] = empty;
        }
        if(party == 0){
            mpxData[3] = empty;
        }
        if(workout == 0){
            mpxData[4] = empty;
        }
        for(int i = 0; i < mpyData.length; i++){
            yEntrys.add(new PieEntry(mpyData[i] , mpxData[i] , i));
        }
        for(int i = 1; i < mpxData.length; i++){
            xEntrys.add(mpxData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Moods");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueLineColor(Color.BLACK);
        /*Description description = new Description();
        description.setText("nigga");
        moodpc.setDescription(description);*/
        activitybc.getDescription().setEnabled(false);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#E5CA24"));
        colors.add(Color.parseColor("#CA824A"));
        colors.add(Color.parseColor("#AB4343"));
        colors.add(Color.parseColor("#4D2E77"));
        colors.add(Color.parseColor("#2D9D7E"));
        pieDataSet.setColors(colors);
        //add legend to chart
        Legend legend = activitybc.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        activitybc.setEntryLabelColor(Color.BLACK);
        activitybc.setData(pieData);
        activitybc.animateX(1500);
        activitybc.animateY(1500);
        activitybc.invalidate();
    }

    public void max(int a,int b,int c){
        int max;
        if (a > b && a > c){
            max = a;
            weekmood.setText("GREAT");

        }else if(b > a && b > c){
            max = b;
            weekmood.setText("NORMAL");

        }else if (c > a && c > b){
            max = c;
            weekmood.setText("SAD");

        }else {
            weekmood.setText("GREAT");
        }
    }

    public void setAnim(int family, int friends){
        String text = "Seems like you spend a lot of time with family";
        if (family > friends){
            family_anim.setVisibility(View.VISIBLE);
        }else{
            friends_anim.setVisibility(View.VISIBLE);
            text = "Good lad, you enjoyed a lot with your friends this week!";
        }
        times_txt.setText(text);
    }

    public void setSleepText(int early,int good, int medium, int bad){
        int max = early;
        String text = "Seems like you are indeed having a good sleep throughout the week!";
        if(good > max){
            max = good;
            sleep_txt.setText(text);
        }
        if(medium > max){
            max = medium;
            text = "It's okay to have medium sleep if you are working a lot, but try to take small naps";
        }
        if(bad > max){
            max = bad;
            text = "Seems like you are having sleeping issues! Try to meditate or visit a doctor";
        }
        if (max == early){
            text = "Wow! you are sleeping early throughout this week, its really good for health!";
        }
        sleep_txt.setText(text);
    }

    public void setEatText(int healthy , int homemade, int fastfood, int soda, int sweets){
        int max = healthy;
        String text = "Wow! Looks like someone is maintaining an healthy appetite";
        if(homemade > max){
            max = homemade;
            text = "Seems like you have consumed home made food throughout the week!";
        }
        if(fastfood > max){
            max = fastfood;
            text = "You have been majorly consuming fast food till now, reduce eating junk food for good health";
        }
        if(soda > max){
            max = soda;
            text = "You've been drinking a lot of soda this week!";
        }
        if(sweets > max){
            max = sweets;
            text = "Too much sweets!! Reduce the consumption of sweets";
        }
        eat_txt.setText(text);
    }

    public void setActivitiesText(int read, int gaming, int movie, int party, int workout){
        int max = read;
        String text = "Wow! Looks like someone's been reading a lot this week";
        if(gaming > max){
            max = gaming;
            text = "Seems like we have a pro gamer here! Lot of gaming this week";
        }
        if(movie > max){
            max = movie;
            text = "Someone's been watching a lot of movies lately, Good!";
        }
        if(party > max){
            max = party;
            text = "Seems like you are having a lot of fun in parties!";
        }
        if(workout > max){
            max = workout;
            text = "Health is wealth! You have spent too much time in workout this week, that's really good";
        }
        activities_txt.setText(text);
    }
}