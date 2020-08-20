package com.whitehorse.dailydiary.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.whitehorse.dailydiary.R;


public class AboutFragment extends Fragment {
    Animation topAnim, bottomAnim;
    ImageView gmail,website,logo;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        gmail = view.findViewById(R.id.gmail);
        website = view.findViewById(R.id.website);
        logo = view.findViewById(R.id.logo);
        topAnim = AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        logo.setAnimation(bottomAnim);
        gmail.setAnimation(bottomAnim);
        website.setAnimation(bottomAnim);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"business@mrwhitehorse.com"});
                //i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mrwhitehorse.com"));
                try {
                    startActivity(browserIntent);
                }catch (Exception e){
                    Toast.makeText(getContext(),"ERROR",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}