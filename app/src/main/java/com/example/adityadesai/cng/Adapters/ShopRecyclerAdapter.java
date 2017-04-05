package com.example.adityadesai.cng.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adityadesai.cng.R;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.Activities.ShopDetailsActivity;

import java.util.ArrayList;



/**
 * Created by adityadesai on 13/02/17.
 */

public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ShopHolder> {

    private Context context;

    private static ArrayList<Shop> mShops;
    private int lastPosition = -1;

    public static class ShopHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemName;
        private TextView mItemAddress;
        private ImageView mImageView;
        private Shop mShop;

        private String name;
        private String address;
        private String phone;
        private String id;
        private String industry;
        private String shopurl;
        private String ownerid;
        private ArrayList<String> offers;

        LinearLayout container;

        public ShopHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.shop_name);
            mItemAddress = (TextView) v.findViewById(R.id.shop_address);
            mImageView = (ImageView) v.findViewById(R.id.shop_image);
            container = (LinearLayout) v.findViewById(R.id.shopRootLayout);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position  =   getAdapterPosition();
            mShop = mShops.get(position);

            if(mShop!=null){
                industry=mShop.getIndustryName();
                name=mShop.getShopName();
                address= mShop.getShopAddress();
                phone= mShop.getShopPhone();
                id = mShop.getShop_id();
                shopurl = mShop.getShopUrl();
                ownerid = mShop.getOwnerId();
                offers = mShop.getOffers();
            }

            Intent i=new Intent(v.getContext(),ShopDetailsActivity.class);
            i.putExtra("shopName",name);
            i.putExtra("shopAddress",address);
            i.putExtra("shopPhone",phone);
            i.putExtra("shop_id",id);
            i.putExtra("industry_name",industry);
            i.putExtra("shop_url",shopurl);
            i.putExtra("owner_id",ownerid);
            i.putExtra("offers",offers);
            v.getContext().startActivity(i);
        }



        public void bindIndustry(Shop shop) {
            mShop = shop;
            mItemName.setText(shop.getShopName());
            mItemAddress.setText(shop.getShopAddress());
            if (shop.getShopUrl() != null) {
                Glide.with(mImageView.getContext()).load(mShop.getShopUrl()).into(mImageView);

            }
        }
    }

    public ShopRecyclerAdapter(ArrayList<Shop> shops, Context context) {
        mShops = shops;
        this.context = context;
    }

    @Override
    public ShopRecyclerAdapter.ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new ShopRecyclerAdapter.ShopHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ShopRecyclerAdapter.ShopHolder holder, int position) {
        Shop itemShop = mShops.get(position);
        holder.bindIndustry(itemShop);
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
        return mShops.size();
    }

    public static Shop getShopbyName(String name){
        for(Shop shop : mShops){
            if(shop.getShopName() != null && shop.getShopName().contains(name)){
                return shop;
            }
        }
        return null;
    }
}
