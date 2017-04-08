package com.example.adityadesai.cngcustomer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adityadesai.cngcustomer.Activities.ItemDetailsActivity;
import com.example.adityadesai.cngcustomer.Objects.MenuItem;
import com.example.adityadesai.cngcustomer.R;

import java.util.ArrayList;

/**
 * Created by adityadesai on 11/02/17.
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuHolder>{

    Context context;

    private  ArrayList<MenuItem> mMenuItems;
    private int lastPosition = -1;

    public static class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        private String mMenuItem;
        LinearLayout container;

        public MenuHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.industry_name);
            container = (LinearLayout) v.findViewById(R.id.industryRootLayout);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i=new Intent(v.getContext(), ItemDetailsActivity.class);
            i.putExtra("Item",mMenuItem);
            v.getContext().startActivity(i);
        }

        public void bindIndustry(String menuItem) {
            mMenuItem = menuItem;
            mItemName.setText(mMenuItem);
        }
    }

    public MenuRecyclerAdapter(ArrayList<MenuItem> menuItem, Context context) {
        mMenuItems = menuItem;
        this.context = context;
    }

    @Override
    public MenuRecyclerAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industry_item, parent, false);
        return new MenuHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MenuRecyclerAdapter.MenuHolder holder, int position) {
        String menuItem = mMenuItems.get(position).getItemName();
        holder.bindIndustry(menuItem);
        setAnimation(holder.container, position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}
