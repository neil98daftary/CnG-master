package com.example.adityadesai.cngcustomer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adityadesai.cngcustomer.Activities.ShopDetailsActivity;

import java.util.ArrayList;

public class view_offers extends Fragment {

    private ListView mListView;
    ArrayAdapter mArrayAdapter;
    ArrayList<String> mOfferItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view1 = inflater.inflate(R.layout.view_offers, container, false);
        mOfferItems = new ArrayList<String>();

        if(ShopDetailsActivity.offers != null) {
            mOfferItems.addAll(0,ShopDetailsActivity.offers);
        }
        /*mOfferItems.add("Buy 1 get 1 free on Item AA");
        mOfferItems.add("Buy 2 get 1 free on Item B");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Buy 1 get 1 free on Item D");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Flat 50% off on Item C");
        */


        mListView = (ListView) view1.findViewById(R.id.offers_list1);
        mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mOfferItems);
        mListView.setAdapter(mArrayAdapter);



        return view1;
    }
}
