package com.example.adityadesai.cng;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adityadesai.cng.Activities.ShopDetailsActivity;

import static com.example.adityadesai.cng.Activities.ShopDetailsActivity.address;
import static com.example.adityadesai.cng.Activities.ShopDetailsActivity.phone;
import static com.example.adityadesai.cng.R.id.addressTextView;
import static com.example.adityadesai.cng.R.id.phoneTextView;

public class view_details extends Fragment {

    private TextView addressTextView;
    private TextView phoneTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.view_details, container, false);

        addressTextView=(TextView)view.findViewById(R.id.addressTextView);
        phoneTextView=(TextView)view.findViewById(R.id.phoneTextView);

        addressTextView.setText(ShopDetailsActivity.address);
        phoneTextView.setText(ShopDetailsActivity.phone);

        return view;
    }
}
