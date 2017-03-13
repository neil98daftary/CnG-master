package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.adityadesai.cng.Adapters.MenuRecyclerAdapter;
import com.example.adityadesai.cng.Adapters.VendorItemListAdapter;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.R.attr.name;

public class VendorItemsListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private VendorItemListAdapter mAdapter;
    public static ArrayList<MenuItem> mMenuItems;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDatabaseReference;
    private ValueEventListener mValueEventListener;

    private String name;
    private String address;
    private String phone;
    public static String industryName;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        name = i.getStringExtra("shopName");
        address = i.getStringExtra("shopAddress");
        phone = i.getStringExtra("shopPhone");
        industryName=i.getStringExtra("industry_name");
        Log.v("tag",industryName);
        id = i.getStringExtra("shop_id");


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mItemDatabaseReference = mFirebaseDatabase.getReference().child(industryName).child(id);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),EditItemListActivity.class);
                startActivity(i);
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.vendor_item_list);
        mGridLayoutManager=new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMenuItems= new ArrayList<>();
        /*mMenuItems.add(new MenuItem("Item A"));
        mMenuItems.add(new MenuItem("Item B"));
        mMenuItems.add(new MenuItem("Item C"));
        mMenuItems.add(new MenuItem("Item D"));
        mMenuItems.add(new MenuItem("Item E"));
        mMenuItems.add(new MenuItem("Item F"));
        mMenuItems.add(new MenuItem("Item G"));
        mMenuItems.add(new MenuItem("Item H"));
        mMenuItems.add(new MenuItem("Item I"));
        mMenuItems.add(new MenuItem("Item J"));*/

        VendorItemsListActivity.fetchItemList fIL = new VendorItemsListActivity.fetchItemList();
        fIL.execute();


    }

    public void updateUI(){
        mAdapter = new VendorItemListAdapter(mMenuItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class fetchItemList extends AsyncTask<Void,Void,ArrayList<MenuItem>> {
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
