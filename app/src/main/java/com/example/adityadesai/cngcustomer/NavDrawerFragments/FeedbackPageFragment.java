package com.example.adityadesai.cngcustomer.NavDrawerFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adityadesai.cngcustomer.R;

/**
 * Created by adityadesai on 12/02/17.
 */

public class FeedbackPageFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.feedback_page,null);
    }
}
