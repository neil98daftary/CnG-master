package com.example.adityadesai.cng;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.adityadesai.cng.Activities.ShopDetailsActivity;
import com.example.adityadesai.cng.Adapters.MenuRecyclerAdapter;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class view_items extends Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<MenuItem> mMenuItems;
    private MenuRecyclerAdapter mAdapter;
    private ProgressBar bar;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventListener;
    private DatabaseReference mItemDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.view_items, container, false);

        bar = (ProgressBar) view.findViewById(R.id.progressBar);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mItemDatabaseReference = mFirebaseDatabase.getReference().child(ShopDetailsActivity.industry).child(ShopDetailsActivity.id);
        mStorageReference = mFirebaseStorage.getReference().child("item_photos");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.shop_menu_recycler);
        mGridLayoutManager=new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMenuItems= new ArrayList<>();

        fetchItemList mFetch = new fetchItemList();
        mFetch.execute();

        return view;

    }

        public void updateUI(){
            bar.setVisibility(View.GONE);
            mAdapter = new MenuRecyclerAdapter(mMenuItems, getActivity());
            mRecyclerView.setAdapter(mAdapter);
    }

    public class fetchItemList extends AsyncTask<Void,Void,ArrayList<MenuItem>> {
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MenuItem> doInBackground(Void... params) {

            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mMenuItems.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String iName = (String) snapshot.child("itemName").getValue();
                        /*Trapping the price and Description????How???*/
                        //ItemDetail itemDetail = new ItemDetail(iName,iPrice,iDesc);
                        //mItemDetails.add(itemDetail);
                        if(iName != null) {
                            mMenuItems.add(new MenuItem(iName));
                        }

                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mItemDatabaseReference.addValueEventListener(mValueEventListener);
            return null;
        }
    }

}
