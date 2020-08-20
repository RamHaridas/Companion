package com.whitehorse.dailydiary.popups;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.whitehorse.dailydiary.R;

public class DataUploadSuccessPopup extends DialogFragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.data_upload_success, container, false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //navcont.navigate(R.id.action_day2Fragment2_to_navigation_home);
                getDialog().dismiss();
            }
        },2500);
        return view;
    }
}
