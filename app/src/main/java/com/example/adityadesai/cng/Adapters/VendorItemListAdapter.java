package com.example.adityadesai.cng.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adityadesai.cng.Activities.ItemDetailsActivity;
import com.example.adityadesai.cng.Activities.VendorItemDetailsActivity;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

/**
 * Created by adityadesai on 11/02/17.
 */

public class VendorItemListAdapter extends RecyclerView.Adapter<VendorItemListAdapter.MenuHolder>{

    private  ArrayList<MenuItem> mMenuItems;

    public static class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        private String mMenuItem;

        public MenuHolder(View v) {
            super(v);


            mItemName = (TextView) v.findViewById(R.id.industry_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i=new Intent(v.getContext(), VendorItemDetailsActivity.class);
            i.putExtra("Item",mMenuItem);
            v.getContext().startActivity(i);
        }

        public void bindIndustry(String menuItem) {
            mMenuItem = menuItem;
            mItemName.setText(mMenuItem);
        }
    }

    public VendorItemListAdapter(ArrayList<MenuItem> menuItem) {
        mMenuItems = menuItem;
    }

    @Override
    public VendorItemListAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industry_item, parent, false);
        return new MenuHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(VendorItemListAdapter.MenuHolder holder, int position) {
        String menuItem = mMenuItems.get(position).getItemName();
        holder.bindIndustry(menuItem);
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}
