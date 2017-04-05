package com.example.adityadesai.cng.NavDrawerFragments;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityadesai.cng.Activities.MainActivity;
import com.example.adityadesai.cng.Adapters.HomeRecyclerAdapter;
import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by adityadesai on 12/02/17.
 */

public class HomePageFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private HomeRecyclerAdapter mAdapter;
    private int id=1000;
    private boolean isCustomer;
    private ArrayList<Industry> mIndustryList;
    private NavigationView navView;
    private TextView makeVendorTextView;
    private ProgressBar bar;



    /*Initializing Firebase variables*/

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventLiatener;


    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView =inflater.inflate(R.layout.home_page,null);

        bar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Industry");
        mStorageReference = mFirebaseStorage.getReference().child("Industry_photos");



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.industry_list);
        mGridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mIndustryList= new ArrayList<>();

        //mMessagesDatabaseReference.push().setValue(new Industry("Gym",++id));
        //mMessagesDatabaseReference.push().setValue(new Industry("Decor",++id));
        //mMessagesDatabaseReference.push().setValue(new Industry("Groceries",++id));
        /*mIndustryList.add(new Industry("Clothing",++id));
        mIndustryList.add(new Industry("Shoes",++id));
        mIndustryList.add(new Industry("Chemist",++id));
        mIndustryList.add(new Industry("Wholesale",++id));
        mIndustryList.add(new Industry("Furniture",++id));
        mIndustryList.add(new Industry("Sports",++id));*/


       // attachDatabaseReadListener();  ?*This is one method to read the data*/


        SharedPreferences sharedPrefs=getActivity().getSharedPreferences("signInMode", Context.MODE_APPEND);
        if(sharedPrefs.getBoolean("isCustomer",true)){
            navView=(NavigationView)getActivity().findViewById(R.id.nav_view);
            Menu menu=navView.getMenu();
            menu.findItem(R.id.myshop).setVisible(false);
        }
        else{
            makeVendorTextView=(TextView)getActivity().findViewById(R.id.become_vendor);
            makeVendorTextView.setVisibility(View.GONE);
        }

        /*Using AsyncTask to load the Data*/
        fetchIndustryList mFetch = new fetchIndustryList();
        mFetch.execute();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    public void updateUI(){
        bar.setVisibility(View.GONE);
        mAdapter = new HomeRecyclerAdapter(mIndustryList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Industry industry = dataSnapshot.getValue(Industry.class);
                    mIndustryList.add(industry);

                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Industry industry = dataSnapshot.getValue(Industry.class);
                    mIndustryList.remove(industry);
                }
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
            //mIndustryDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }



    public class fetchIndustryList extends AsyncTask<Void,Void,ArrayList<Industry>>{

        @Override
        protected void onPreExecute() {
            Log.v("tag","entered");
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Industry> doInBackground(Void... params) {

            mValueEventLiatener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mIndustryList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String name = (String) snapshot.child("name").getValue();
                        String id = snapshot.child("id").getValue().toString();
                        mIndustryList.add(new Industry(name,id));
                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mMessagesDatabaseReference.addValueEventListener(mValueEventLiatener);
            return mIndustryList;
        }
    }

}
