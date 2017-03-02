package com.example.adityadesai.cng.NavDrawerFragments;

/**
 * Created by Neil on 02-03-2017.
 */

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

public class MyShopsFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private HomeRecyclerAdapter mAdapter;
    private int id=1000;
    private boolean isCustomer;
    NavigationView navView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView =inflater.inflate(R.layout.myshops_page,null);

        SharedPreferences sharedPrefs=getActivity().getSharedPreferences("signInMode", Context.MODE_APPEND);
        if(sharedPrefs.getBoolean("isCustomer",true)){
            navView=(NavigationView)getActivity().findViewById(R.id.nav_view);
            Menu menu=navView.getMenu();
            menu.findItem(R.id.myshop).setVisible(false);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myshops_list);
        mGridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        final ArrayList<Industry> mIndustryList= new ArrayList<>();
        mIndustryList.add(new Industry("Shop A",++id));
        mIndustryList.add(new Industry("Shop B",++id));
        mIndustryList.add(new Industry("Shop C",++id));
        mIndustryList.add(new Industry("Shop D",++id));
        mIndustryList.add(new Industry("Shop E",++id));
        mIndustryList.add(new Industry("Shop F",++id));
        mIndustryList.add(new Industry("Shop G",++id));

        mAdapter = new HomeRecyclerAdapter(mIndustryList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
