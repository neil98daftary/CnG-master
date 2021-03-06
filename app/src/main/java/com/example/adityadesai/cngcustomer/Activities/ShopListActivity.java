package com.example.adityadesai.cngcustomer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adityadesai.cngcustomer.Adapters.ShopRecyclerAdapter;
import com.example.adityadesai.cngcustomer.Objects.Shop;
import com.example.adityadesai.cngcustomer.R;

import java.util.ArrayList;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShopListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ShopRecyclerAdapter mAdapter;
    private ArrayList<Shop> mShopList;
    private ProgressBar bar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mShopDatabaseReference,test;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventListener;

    public static String shop_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_list);

        bar = (ProgressBar) findViewById(R.id.progressBar);


        // Get and display IID
        Intent i= getIntent();
        Bundle b = i.getExtras();
        shop_type =(String) b.get("name");


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mShopDatabaseReference = mFirebaseDatabase.getReference().child(ShopListActivity.shop_type);
        mStorageReference = mFirebaseStorage.getReference().child("shop_photos");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshPage);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                fetchShopList fSl = new fetchShopList();
                fSl.execute();
            }
        });

        // Recyclerview code
        mRecyclerView = (RecyclerView) findViewById(R.id.shop_list);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        Toast.makeText(this,"Shops",Toast.LENGTH_SHORT).show();

        mShopList= new ArrayList<>();
        /*mShopList.add(new Shop("Shop A","Address A","0000"));
        mShopList.add(new Shop("Shop B","Address B","0001"));
        mShopList.add(new Shop("Shop C","Address C","0002"));
        mShopList.add(new Shop("Shop D","Address D","0003"));
        mShopList.add(new Shop("Shop E","Address E","0004"));
        mShopList.add(new Shop("Shop F","Address F","0005"));
        mShopList.add(new Shop("Shop G","Address G","0006"));
        mShopList.add(new Shop("Shop H","Address H","0007"));
        mShopList.add(new Shop("Shop I","Address I","0008"));
        mShopList.add(new Shop("Shop J","Address J","0009"));
        mShopList.add(new Shop("Shop K","Address K","0010"));*/

        fetchShopList fSl = new fetchShopList();
        fSl.execute();


        /******sAMPLE ON HOW TO PUSH DATA******/
        //mShopDatabaseReference.push().setValue(new Shop("Big Bazaar","Ghatkopar","69696969","1001"));
        //mShopDatabaseReference.push().setValue(new Shop("Big Bazaar","Thane","420420420","1002"));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }




    public void updateUI(){
        swipeRefreshLayout.setRefreshing(false);
        bar.setVisibility(View.GONE);
        mAdapter = new ShopRecyclerAdapter(mShopList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class fetchShopList extends AsyncTask<Void,Void,ArrayList<Shop>> {

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Shop> doInBackground(Void... params) {

            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mShopList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String shop_name = (String) snapshot.child("shopName").getValue();
                        String shop_address = (String) snapshot.child("shopAddress").getValue();
                        String shop_phonenum = (String) snapshot.child("shopPhone").getValue();
                        String shop_id =  (String) snapshot.child("shop_id").getValue();
                        String industry_name =  (String) snapshot.child("industryName").getValue();
                        ArrayList<String> shop_uri = (ArrayList<String>) snapshot.child("shopUrl").getValue();
                        String owner_id = (String) snapshot.child("ownerId").getValue();
                        ArrayList<String> offers = (ArrayList<String>) snapshot.child("offers").getValue();
                        String totalRatePoints;
                        String numRates;
                        if(snapshot.hasChild("totalRatePoints") && snapshot.hasChild("numRates")){
                            totalRatePoints = (String) snapshot.child("totalRatePoints").getValue();
                            numRates = (String) snapshot.child("numRates").getValue();
                        }
                        else{
                            totalRatePoints = "0";
                            numRates = "0";
                        }

                        if(shop_name != null && shop_address != null && shop_phonenum != null && shop_id != null) {
                            mShopList.add(new Shop(shop_name, shop_address, shop_phonenum,shop_id,industry_name,shop_uri,owner_id,offers,totalRatePoints,numRates));
                        }

                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mShopDatabaseReference.addValueEventListener(mValueEventListener);
            return null;
        }
    }
}
