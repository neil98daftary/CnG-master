package com.example.adityadesai.cng.NavDrawerFragments;

/**
 * Created by Neil on 02-03-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adityadesai.cng.Activities.EditShopActivity;
import com.example.adityadesai.cng.Activities.ShopListActivity;
import com.example.adityadesai.cng.Adapters.HomeRecyclerAdapter;
import com.example.adityadesai.cng.Adapters.ShopRecyclerAdapter;
import com.example.adityadesai.cng.Adapters.VendorShopListAdapter;
import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;


public class MyShopsFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private VendorShopListAdapter mAdapter;
    private int id=1000;
    private boolean isCustomer;
    NavigationView navView;
    private ArrayList<Shop> mShopList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener mValueEventListener;
    private SharedPreferences userspf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView =inflater.inflate(R.layout.myshops_page,null);

        SharedPreferences sharedPrefs=getActivity().getSharedPreferences("signInMode", Context.MODE_APPEND);
        userspf = getActivity().getSharedPreferences("userInfo",Context.MODE_APPEND);

        if(sharedPrefs.getBoolean("isCustomer",true)){
            navView=(NavigationView)getActivity().findViewById(R.id.nav_view);
            Menu menu=navView.getMenu();
            menu.findItem(R.id.myshop).setVisible(false);
        }

        //Configured to just show groceries till users feature is not added
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myshops_list);
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        /*final ArrayList<Shop> mShopList= new ArrayList<>();
        mShopList.add(new Shop("Shop A","Address A","0000","0"));
        mShopList.add(new Shop("Shop B","Address B","0001","1"));
        mShopList.add(new Shop("Shop C","Address C","0002","2"));
        mShopList.add(new Shop("Shop D","Address D","0003","3"));
        mShopList.add(new Shop("Shop E","Address E","0004","4"));
        mShopList.add(new Shop("Shop F","Address F","0005","5"));
        mShopList.add(new Shop("Shop G","Address G","0006","6"));
        mShopList.add(new Shop("Shop H","Address H","0007","7"));
        mShopList.add(new Shop("Shop I","Address I","0008","8"));
        mShopList.add(new Shop("Shop J","Address J","0009","9"));
        mShopList.add(new Shop("Shop K","Address K","0010","10"));*/

        mShopList= new ArrayList<>();

        MyShopsFragment.fetchShopList fSl = new MyShopsFragment.fetchShopList();
        fSl.execute();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.add_shop_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), EditShopActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }

    // Call this function....

    public void updateUI(){
        mAdapter = new VendorShopListAdapter(mShopList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class fetchShopList extends AsyncTask<Void,Void,ArrayList<Shop>> {
        @Override
        protected ArrayList<Shop> doInBackground(Void... params) {
            mDatabaseReference = mFirebaseDatabase.getReference().child("Groceries");
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mShopList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot.child("ownerId").getValue() == null){continue;}
                        if(snapshot.child("ownerId").getValue().equals(userspf.getString("uid",null))){
                            String shop_name = (String) snapshot.child("shopName").getValue();
                            String shop_address = (String) snapshot.child("shopAddress").getValue();
                            String shop_phonenum = (String) snapshot.child("shopPhone").getValue();
                            String shop_id =  (String) snapshot.child("shop_id").getValue();
                            String industry_name =  (String) snapshot.child("industryName").getValue();
                            String shop_uri = (String) snapshot.child("shopUrl").getValue();
                            String uid = (String) snapshot.child("ownerId").getValue();
                            mShopList.add(new Shop(shop_name, shop_address, shop_phonenum,shop_id,industry_name,shop_uri,uid));
                        }
                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mDatabaseReference = mFirebaseDatabase.getReference().child("Gym");
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot.child("ownerId").getValue() == null){continue;}
                        if(snapshot.child("ownerId").getValue().equals(userspf.getString("uid",null))){
                            String shop_name = (String) snapshot.child("shopName").getValue();
                            String shop_address = (String) snapshot.child("shopAddress").getValue();
                            String shop_phonenum = (String) snapshot.child("shopPhone").getValue();
                            String shop_id =  (String) snapshot.child("shop_id").getValue();
                            String industry_name =  (String) snapshot.child("industryName").getValue();
                            String shop_uri = (String) snapshot.child("shopUrl").getValue();
                            String uid = (String) snapshot.child("ownerId").getValue();
                            mShopList.add(new Shop(shop_name, shop_address, shop_phonenum,shop_id,industry_name,shop_uri,uid));
                        }
                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            return null;
        }
    }

}
