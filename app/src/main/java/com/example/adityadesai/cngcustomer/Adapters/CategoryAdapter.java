package com.example.adityadesai.cngcustomer.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.adityadesai.cngcustomer.R;
import com.example.adityadesai.cngcustomer.view_offers;
import com.example.adityadesai.cngcustomer.view_details;
import com.example.adityadesai.cngcustomer.view_items;

/**
 * Created by neil on 23/3/17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return new view_items();
            case 1:
                return new view_offers();
            case 2:
                return new view_details();
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_items);
        } else if (position == 1) {
            return mContext.getString(R.string.category_offers);
        } else{
            return mContext.getString(R.string.category_details);
        }
    }
}
