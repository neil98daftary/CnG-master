package com.example.adityadesai.cngcustomer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityadesai.cngcustomer.Activities.OwnerInfo;
import com.example.adityadesai.cngcustomer.Activities.ShopDetailsActivity;

public class view_details extends Fragment {

    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView ownerTextView;

    private CardView ownerCard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.view_details, container, false);

        addressTextView=(TextView)view.findViewById(R.id.addressTextView);
        phoneTextView=(TextView)view.findViewById(R.id.phoneTextView);
        ownerTextView = (TextView) view.findViewById(R.id.ownerInfoTextView);

        ownerCard = (CardView) view.findViewById(R.id.owner);

        addressTextView.setText(ShopDetailsActivity.address);
        phoneTextView.setText(ShopDetailsActivity.phone);
        //ownerTextView.setText(ShopDetailsActivity.ownerid);

        ownerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Clicking!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(),OwnerInfo.class);
                i.putExtra("uid",ShopDetailsActivity.ownerid);
                startActivity(i);
            }
        });

        return view;
    }


}
