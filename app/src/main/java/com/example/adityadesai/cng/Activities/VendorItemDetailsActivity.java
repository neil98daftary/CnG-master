package com.example.adityadesai.cng.Activities;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.adityadesai.cng.Adapters.ItemDetailAdapter;
import com.example.adityadesai.cng.Adapters.VendorItemDetailsAdapter;

import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import java.util.ArrayList;

import static com.example.adityadesai.cng.R.id.item_name;

public class VendorItemDetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private VendorItemDetailsAdapter mAdapter;
    private ArrayList<ItemDetail> mDetailList;
    public static String item_name;
    private ProgressBar bar;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDetailDatabaseReference;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_item_details);

        bar = (ProgressBar)findViewById(R.id.progressBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        item_name = i.getStringExtra("Item");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item_detail_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),EditItemDetailActivity.class);
                startActivity(i);
            }
        });

        mListView = (ListView) this.findViewById(R.id.vendor_item_details);
        mDetailList = new ArrayList<ItemDetail>();

        /*mDetailList.add(new ItemDetail("Name A", "Rs 100", "Description A"));
        mDetailList.add(new ItemDetail("Name B", "Rs 100", "Description B"));
        mDetailList.add(new ItemDetail("Name C", "Rs 100", "Description C"));
        mDetailList.add(new ItemDetail("Name D", "Rs 100", "Description D"));
        mDetailList.add(new ItemDetail("Name E", "Rs 100", "Description E"));
        mDetailList.add(new ItemDetail("Name F", "Rs 100", "Description F"));
        mDetailList.add(new ItemDetail("Name G", "Rs 100", "Description G"));
        mDetailList.add(new ItemDetail("Name H", "Rs 100", "Description H"));
        mDetailList.add(new ItemDetail("Name I", "Rs 100", "Description I"));
        mDetailList.add(new ItemDetail("Name J", "Rs 100", "Description J"));*/

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mItemDetailDatabaseReference = mFirebaseDatabase.getReference().child(VendorItemsListActivity.industryName).child(VendorItemsListActivity.id).child(item_name);

        VendorItemDetailsActivity.fetchItemDetail fID = new VendorItemDetailsActivity.fetchItemDetail();
        fID.execute();

        /*mAdapter = new VendorItemDetailsAdapter(this, mDetailList);
        mListView.setAdapter(new SlideExpandableListAdapter(
                mAdapter,
                R.id.item_detail_view,
                R.id.item_description
        ));*/

//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setVisibility(View.INVISIBLE);
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
//                view.startDrag(data, shadow, null, 0);
//                return false;
//            }
//        });

    }

    public void updateUI(){
        bar.setVisibility(View.GONE);
        mAdapter = new VendorItemDetailsAdapter(VendorItemDetailsActivity.this, mDetailList);
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
                    mDetailList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String iName = (String) snapshot.child("itemName").getValue();
                        String iPrice = (String) snapshot.child("itemPrice").getValue();
                        String iDesc = (String) snapshot.child("itemDescription").getValue();
                        String iUrl = (String) snapshot.child("itemUrl").getValue();
                        /*Trapping the price and Description????How???*/
                        if(iName != null && iPrice != null && iDesc != null) {
                            ItemDetail itemDetail = new ItemDetail(iName, iPrice, iDesc,iUrl);
                            mDetailList.add(itemDetail);
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

}
