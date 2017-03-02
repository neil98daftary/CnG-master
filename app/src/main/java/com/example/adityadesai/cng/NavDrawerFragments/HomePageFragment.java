package com.example.adityadesai.cng.NavDrawerFragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.adityadesai.cng.Adapters.HomeRecyclerAdapter;
import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

/**
 * Created by adityadesai on 12/02/17.
 */

public class HomePageFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private HomeRecyclerAdapter mAdapter;
    private int id=1000;
    private boolean isCustomer;
    NavigationView navView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView =inflater.inflate(R.layout.home_page,null);

        SharedPreferences sharedPrefs=getActivity().getSharedPreferences("signInMode", Context.MODE_APPEND);
        if(sharedPrefs.getBoolean("isCustomer",true)){
            navView=(NavigationView)getActivity().findViewById(R.id.nav_view);
            Menu menu=navView.getMenu();
            menu.findItem(R.id.myshop).setVisible(false);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.industry_list);
        mGridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        final ArrayList<Industry> mIndustryList= new ArrayList<>();
        mIndustryList.add(new Industry("Groceries",++id));
        mIndustryList.add(new Industry("Clothing",++id));
        mIndustryList.add(new Industry("Shoes",++id));
        mIndustryList.add(new Industry("Chemist",++id));
        mIndustryList.add(new Industry("Wholesale",++id));
        mIndustryList.add(new Industry("Furniture",++id));
        mIndustryList.add(new Industry("Sports",++id));

        mAdapter = new HomeRecyclerAdapter(mIndustryList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
