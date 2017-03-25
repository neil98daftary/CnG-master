package com.example.adityadesai.cng.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adityadesai.cng.Objects.ModelObject;
import com.example.adityadesai.cng.view_blue;
import com.example.adityadesai.cng.view_green;
import com.example.adityadesai.cng.view_red;

/**
 * Created by neil on 23/3/17.
 */

public class CustomAdapter extends FragmentStatePagerAdapter{
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public CustomAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                view_red tab1 = new view_red();
                return tab1;
            case 1:
                view_blue tab2 = new view_blue();
                return tab2;
            case 2:
                view_green tab3 = new view_green();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
