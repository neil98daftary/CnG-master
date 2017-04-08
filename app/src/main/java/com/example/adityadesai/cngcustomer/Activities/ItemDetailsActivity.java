package com.example.adityadesai.cngcustomer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.adityadesai.cngcustomer.Adapters.ItemDetailAdapter;
import com.example.adityadesai.cngcustomer.Objects.ItemDetail;
import com.example.adityadesai.cngcustomer.R;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import java.util.ArrayList;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ItemDetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private ItemDetailAdapter mAdapter;
    private ArrayList<ItemDetail> mItemDetails;
    private String item_name;
    private ProgressBar bar;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDetailDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        bar = (ProgressBar)findViewById(R.id.progressBar);

        //mListView = (ListView) findViewById(R.id.item_details_list);
        mListView = (ListView) this.findViewById(R.id.item_details_list);
        mItemDetails = new ArrayList<ItemDetail>();

        Intent i = getIntent();
        item_name = i.getStringExtra("Item");

        /*mDetailList.add(new ItemDetail("Name A", "Rs 100", "Description A"));
        mDetailList.add(new ItemDetail("Name B", "Rs 100", "Description B"));
        mDetailList.add(new ItemDetail("Name C", "Rs 100", "Description C"));
        mDetailList.add(new ItemDetail("Name D", "Rs 100", "Description D"));
        mDetailList.add(new ItemDetail("Name E", "Rs 100", "Description E"));
        mDetailList.add(new ItemDetail("Name F", "Rs 100", "Description F"));
        mDetailList.add(new ItemDetail("Name G", "Rs 100", "Description G"));
        mDetailList.add(new ItemDetail("Name H", "Rs 100", "Description H"));
        mDetailList.add(new ItemDetail("Name I", "Rs 100", "Description I"));
        mDetailList.add(new ItemDetail("Name J", "Rs 100", "Description J"));
        */



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mItemDetailDatabaseReference = mFirebaseDatabase.getReference().child(ShopListActivity.shop_type).child(ShopDetailsActivity.id).child(item_name);
        mStorageReference = mFirebaseStorage.getReference().child("item_photos");

        fetchItemDetail fID = new fetchItemDetail();
        fID.execute();


        /******SAMPLE ON HOW TO PUSH THE DATA*******/
        //mItemDetailDatabaseReference.push().setValue(new ItemDetail("Groundnut","100 per L","Excellent Quality"));
        //mItemDetailDatabaseReference.push().setValue(new ItemDetail("Sunflower","200 per L","Excellent Quality Also"));

    }

    public void updateUI(){
        bar.setVisibility(View.GONE);
        mAdapter = new ItemDetailAdapter(this, mItemDetails);
        mListView.setAdapter(new SlideExpandableListAdapter(
                mAdapter,
                R.id.item_detail_view,
                R.id.item_description
        ));
    }

    public class fetchItemDetail extends AsyncTask<Void,Void,ArrayList<ItemDetail>> {
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<ItemDetail> doInBackground(Void... params) {

            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mItemDetails.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String iName = (String) snapshot.child("itemName").getValue();
                        String iPrice = (String) snapshot.child("itemPrice").getValue();
                        String iDesc = (String) snapshot.child("itemDescription").getValue();
                        String iUrl = (String) snapshot.child("itemUrl").getValue();
                        /*Trapping the price and Description????How???*/
                        if(iName != null && iPrice != null && iDesc != null) {
                            ItemDetail itemDetail = new ItemDetail(iName, iPrice, iDesc,iUrl);
                            mItemDetails.add(itemDetail);
                        }

                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mItemDetailDatabaseReference.addValueEventListener(mValueEventListener);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
