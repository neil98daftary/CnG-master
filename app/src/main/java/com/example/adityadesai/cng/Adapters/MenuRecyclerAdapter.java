package com.example.adityadesai.cng.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adityadesai.cng.Activities.ItemDetailsActivity;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

/**
 * Created by adityadesai on 11/02/17.
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuHolder>{

    private static ArrayList<MenuItem> mMenuItems;

    public static class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        private MenuItem mMenuItem;

        public MenuHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.industry_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i=new Intent(v.getContext(), ItemDetailsActivity.class);
            v.getContext().startActivity(i);
        }

        public void bindIndustry(MenuItem menuItem) {
            mMenuItem = menuItem;
            mItemName.setText(menuItem.getItemName());
        }
    }

    public MenuRecyclerAdapter(ArrayList<MenuItem> menuItem) {
        mMenuItems = menuItem;
    }

    @Override
    public MenuRecyclerAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industry_item, parent, false);
        return new MenuHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MenuRecyclerAdapter.MenuHolder holder, int position) {
        MenuItem menuItem = mMenuItems.get(position);
        holder.bindIndustry(menuItem);
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}
