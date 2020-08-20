package com.whitehorse.dailydiary.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whitehorse.dailydiary.R;
import com.whitehorse.dailydiary.animations.Animations;
import com.whitehorse.dailydiary.callback.CallbackMoodPost;
import com.whitehorse.dailydiary.data.DailyData;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context mContext;
    List<CallbackMoodPost> mDayList;
    List<String> stringList;
    public HomeAdapter(Context cont, List<CallbackMoodPost> dayList){
        mContext=cont;
        mDayList=dayList;

    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.homecard,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {
        stringList = new ArrayList<String>();
        final DailyData curr2 = new DailyData();
        final CallbackMoodPost curr = mDayList.get(position);
        holder.hometv.setText(curr.getDay().toUpperCase());
        switch (curr.getDay()){
            case "great":
                holder.iv.setImageResource(R.drawable.ic_great_2020);
                break;
            case "normal":
                holder.iv.setImageResource(R.drawable.ic_normal_2020);
                break;
            case "sad":
                holder.iv.setImageResource(R.drawable.ic_sad_2020);
                break;
        }
        holder.hometv1.setText(curr.getDate());
        stringList.add(curr.getSleep().toUpperCase());
        stringList.add(curr.getTime().toUpperCase());
        stringList.add(curr.getEat().toUpperCase());
        stringList.add(curr.getActivity().toUpperCase());

        for(String s : stringList){
            holder.hometv2.append("\n");
            holder.hometv2.append(s);
        }
        //holder.iv.setImageResource(curr.getImage());
        holder.homell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!curr2.isExpanded(),holder.arrowbt, holder.hidell);
                curr2.setExpanded(show);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView hometv,hometv1,hometv2;
        LinearLayout homell,hidell;
        ImageButton arrowbt;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.homeiv);
            //iv.setVisibility(View.INVISIBLE);
            hometv = itemView.findViewById(R.id.hometv);
            hometv1 = itemView.findViewById(R.id.hometv1);
            hometv2 = itemView.findViewById(R.id.hometv2);
            homell = itemView.findViewById(R.id.homell);
            hidell = itemView.findViewById(R.id.homehidell);
            arrowbt = itemView.findViewById(R.id.viewMoreBtn);
        }
    }
    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;
    }


}
