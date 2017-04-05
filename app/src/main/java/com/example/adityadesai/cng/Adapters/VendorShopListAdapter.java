package com.example.adityadesai.cng.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import com.example.adityadesai.cng.Activities.EditShopActivity;

//import com.example.adityadesai.cng.Activities.VendorItemListActivity;
import com.example.adityadesai.cng.Activities.VendorItemsListActivity;
import com.example.adityadesai.cng.R;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.Activities.ShopDetailsActivity;

import java.util.ArrayList;

import static com.example.adityadesai.cng.Activities.ShopDetailsActivity.industry;


/**
 * Created by adityadesai on 13/02/17.
 */


public class VendorShopListAdapter extends RecyclerView.Adapter<VendorShopListAdapter.ShopHolder> {

    static Context context;

    private static ArrayList<Shop> mShops;
    int lastPosition = -1;

    public static class ShopHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mItemName;
        private TextView mItemAddress;
        private ImageView mImageView;
        private Shop mShop;
        private LinearLayout container;

        private String industryName;
        private String name;
        private String address;
        private String phone;
        private String id;
        private String Url;

        public ShopHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.shop_name);
            mItemAddress = (TextView) v.findViewById(R.id.shop_address);
            mImageView = (ImageView) v.findViewById(R.id.shop_image);
            container = (LinearLayout) v.findViewById(R.id.shopRootLayout);
            v.setOnClickListener(this);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    new AlertDialog.Builder(context)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int position = getAdapterPosition();
                                    mShop = mShops.get(position);
                                    if(mShop!=null){
                                        industryName = mShop.getIndustryName();
                                        id = mShop.getShop_id();
                                    }
                                    Intent i = new Intent(v.getContext(),EditShopActivity.class);
                                    i.putExtra("editShop","yes");
                                    i.putExtra("industry",industryName);
                                    i.putExtra("id",id);
                                    v.getContext().startActivity(i);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position  =   getAdapterPosition();
            mShop = mShops.get(position);

            if(mShop!=null){
                name = mShop.getShopName();
                industryName=mShop.getIndustryName();
                address= mShop.getShopAddress();
                phone= mShop.getShopPhone();
                id = mShop.getShop_id();
                Url = mShop.getShopUrl();
            }

            Intent i=new Intent(v.getContext(),VendorItemsListActivity.class);
            i.putExtra("shopName",name);
            i.putExtra("shopAddress",address);
            i.putExtra("shopPhone",phone);
            i.putExtra("shop_id",id);
            i.putExtra("industry_name",industryName);
            i.putExtra("shop_url",Url);
            v.getContext().startActivity(i);

        }

        public void bindIndustry(Shop shop) {
            mShop = shop;
            mItemName.setText(shop.getShopName());
            mItemAddress.setText(shop.getShopAddress());
            if(shop.getShopUrl() != null){
                Glide.with(mImageView.getContext()).load(shop.getShopUrl()).into(mImageView);
            }
        }
    }

    public VendorShopListAdapter(ArrayList<Shop> shops, Context context) {
        mShops = shops;
        this.context = context;
    }

    @Override
    public VendorShopListAdapter.ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new VendorShopListAdapter.ShopHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(VendorShopListAdapter.ShopHolder holder, int position) {
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
