package com.example.adityadesai.cng.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.R;
import com.example.adityadesai.cng.Activities.ShopListActivity;

import java.util.ArrayList;

/**
 * Created by adityadesai on 11/02/17.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.IndustryHolder>{

    private static ArrayList<Industry> mIndustries;

    public static class IndustryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        private Industry mIndustry;

        public IndustryHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.industry_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position  =   getAdapterPosition();
            Intent i=new Intent(v.getContext(),ShopListActivity.class);
            i.putExtra("id",Integer.toString(mIndustries.get(position).getId()));
            v.getContext().startActivity(i);
        }

        public void bindIndustry(Industry industry) {
            mIndustry = industry;
            mItemName.setText(industry.getName());
        }
    }

    public HomeRecyclerAdapter(ArrayList<Industry> industries) {
        mIndustries = industries;
    }

    @Override
    public HomeRecyclerAdapter.IndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industry_item, parent, false);
        return new IndustryHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.IndustryHolder holder, int position) {
        Industry itemIndustry = mIndustries.get(position);
        holder.bindIndustry(itemIndustry);
    }

    @Override
    public int getItemCount() {
        return mIndustries.size();
    }
}
