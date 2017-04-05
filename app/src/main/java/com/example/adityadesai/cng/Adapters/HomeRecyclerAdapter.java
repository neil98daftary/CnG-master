package com.example.adityadesai.cng.Adapters;

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

import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.R;
import com.example.adityadesai.cng.Activities.ShopListActivity;

import java.util.ArrayList;

/**
 * Created by adityadesai on 11/02/17.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.IndustryHolder>{

    private Context context;

    private static ArrayList<Industry> mIndustries;
    private Industry itemIndustry;
    private int lastPosition = -1;

    public static class IndustryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        //private Industry mIndustry;
        LinearLayout container;

        public IndustryHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.industry_name);
            container = (LinearLayout) v.findViewById(R.id.industryRootLayout);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position  =   getAdapterPosition();
            Intent i=new Intent(v.getContext(),ShopListActivity.class);
            i.putExtra("name",mIndustries.get(position).getName());
            v.getContext().startActivity(i);
        }

        public void bindIndustry(Industry industry) {
            mItemName.setText(industry.getName());
        }
    }

    public HomeRecyclerAdapter(ArrayList<Industry> industries, Context context) {
        mIndustries = industries;
        this.context = context;
    }

    @Override
    public HomeRecyclerAdapter.IndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.industry_item, parent, false);

        return new IndustryHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.IndustryHolder holder, int position) {
        itemIndustry = mIndustries.get(position);
        holder.bindIndustry(itemIndustry);
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
        return mIndustries.size();
    }
}
