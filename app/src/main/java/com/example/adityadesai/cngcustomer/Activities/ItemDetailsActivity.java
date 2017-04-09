package com.example.adityadesai.cngcustomer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    private ExpandableListView mExpandableListView;
    private ItemDetailsAdapter mAdapter;
    private ArrayList<ItemDetail> mDetailList;
    private ArrayList<ArrayList<String>> mDescriptionList;
    private String item_name;
    private ProgressBar bar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDetailDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventListener;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        bar = (ProgressBar)findViewById(R.id.progressBar);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshPage);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                fetchItemDetail fID = new fetchItemDetail();
                fID.execute();
            }
        });

        //mListView = (ListView) findViewById(R.id.item_details_list);
        mExpandableListView = (ExpandableListView) this.findViewById(R.id.item_details_list);
        mDetailList = new ArrayList<ItemDetail>();
        mDescriptionList = new ArrayList<ArrayList<String>>();

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
        swipeRefreshLayout.setRefreshing(false);
        bar.setVisibility(View.GONE);
        mAdapter = new ItemDetailsAdapter(mDetailList,mDescriptionList);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setChildIndicator(null);
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
                            mDescriptionList.add(new ArrayList<String>());
                            mDescriptionList.get(i).add(iDesc);
                            i++;
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

    public class ItemDetailsAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private ArrayList<ItemDetail> groups;
        private ArrayList<ArrayList<String>> children;

        public ItemDetailsAdapter(ArrayList<ItemDetail> groups, ArrayList<ArrayList<String>> children) {
            this.groups = groups;
            this.children = children;
            inf = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children.get(groupPosition).size();
        }

        @Override
        public ItemDetail getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ItemDetailsAdapter.ChildHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.shop_detail_item_child, parent, false);
                holder = new ItemDetailsAdapter.ChildHolder();

                holder.description = (TextView) convertView.findViewById(R.id.item_description);
                //holder.text.setBackgroundColor(getResources().getColor(R.color.colorTranslucent));
                convertView.setTag(holder);
            } else {
                holder = (ItemDetailsAdapter.ChildHolder) convertView.getTag();
            }

            holder.description.setText(getChild(groupPosition, childPosition).toString());
//        holder.text.setAutoLinkMask(Linkify.PHONE_NUMBERS);

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ItemDetailsAdapter.GroupHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.shop_detail_item, parent, false);

                holder = new ItemDetailsAdapter.GroupHolder();
                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.price = (TextView) convertView.findViewById(R.id.item_price);
                holder.image = (ImageView) convertView.findViewById(R.id.item_image);
                convertView.setTag(holder);
            } else {
                holder = (ItemDetailsAdapter.GroupHolder) convertView.getTag();
            }

            holder.name.setText(getGroup(groupPosition).getItemName().toString());
            holder.price.setText(getGroup(groupPosition).getItemPrice().toString());
            Glide.with(holder.image.getContext()).load(getGroup(groupPosition).getItemUrl()).into(holder.image);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class GroupHolder {
            TextView name;
            TextView price;
            ImageView image;

        }
        private  class ChildHolder{
            TextView description;
        }
    }

}
