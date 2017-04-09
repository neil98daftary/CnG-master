package com.example.adityadesai.cngcustomer.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.adityadesai.cngcustomer.Adapters.CategoryAdapter;
import com.example.adityadesai.cngcustomer.Adapters.CustomPagerAdapter;
import com.example.adityadesai.cngcustomer.Adapters.MenuRecyclerAdapter;
import com.example.adityadesai.cngcustomer.Objects.MenuItem;
import com.example.adityadesai.cngcustomer.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShopDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MenuRecyclerAdapter mAdapter;
    ArrayAdapter mArrayAdapter;

    private String name;
    public static String address;
    public static String phone;
    public static String industry;
    public static String id;
    public static String url;
    public static String ownerid;
    public static ArrayList<String> offers;

    private ListView mListView;
    //public static ArrayList<ItemDetail> mItemDetails;
    int isFavourite=0;
    private ArrayList<MenuItem> mMenuItems;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private ValueEventListener mValueEventListener;



    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        offers = new ArrayList<String>();

        Intent i = getIntent();
        name = i.getStringExtra("shopName");
        address = i.getStringExtra("shopAddress");
        phone = i.getStringExtra("shopPhone");
        industry=i.getStringExtra("industry_name");
        id = i.getStringExtra("shop_id");
        url = i.getStringExtra("shop_url");
        ownerid = i.getStringExtra("owner_id");
        if(i.getStringArrayListExtra("offers") != null) {
            offers.addAll(0, i.getStringArrayListExtra("offers"));
        }


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Creating our pager adapter
        CategoryAdapter adapter = new CategoryAdapter(this,getSupportFragmentManager());


        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setupWithViewPager(viewPager);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollview);
        scrollView.setFillViewport (true);




        // Set title
        android.support.design.widget.CollapsingToolbarLayout toolbar=(android.support.design.widget.CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        toolbar.setTitle(name);

        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.shop_image_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);






        //mRecyclerView = (RecyclerView) findViewById(R.id.shop_menu_recycler);
        //mGridLayoutManager=new GridLayoutManager(this,2);
        //mRecyclerView.setLayoutManager(mGridLayoutManager);

        mMenuItems= new ArrayList<>();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mItemDatabaseReference = mFirebaseDatabase.getReference().child(industry);

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



//        fetchItemList fIL = new fetchItemList();
//        fIL.execute();

        /******* A sample on how to push data********/
       // mItemDatabaseReference.push().setValue(new MenuItem("Rice"));
        //mItemDatabaseReference.push().setValue(new MenuItem("Oil"));

    }
    //END OF onCreate//

    //    public void updateUI(){
    //        mAdapter = new MenuRecyclerAdapter(mMenuItems);
    //        mRecyclerView.setAdapter(mAdapter);
    //    }

    //    public class fetchItemList extends AsyncTask<Void,Void,ArrayList<MenuItem>> {
    //        @Override
    //        protected ArrayList<MenuItem> doInBackground(Void... params) {
    //
    //            mValueEventListener = new ValueEventListener() {
    //                @Override
    //                public void onDataChange(DataSnapshot dataSnapshot) {
    //                    mMenuItems.clear();
    //                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
    //                        String iName = (String) snapshot.child("itemName").getValue();
    //                        /*Trapping the price and Description????How???*/
    //                        //ItemDetail itemDetail = new ItemDetail(iName,iPrice,iDesc);
    //                        //mItemDetails.add(itemDetail);
    //                        if(iName != null) {
    //                            mMenuItems.add(new MenuItem(iName));
    //                        }
    //
    //                    }
    //                    updateUI();
    //                }
    //
    //                @Override
    //                public void onCancelled(DatabaseError databaseError) {
    //
    //                }
    //            };
    //
    //            mItemDatabaseReference.addValueEventListener(mValueEventListener);
    //            return null;
    //        }
    //    }

    public String getId(){
        return id;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void goToAddress(View v){
        //NY for now
        double latitude = 40.714728;
        double longitude = -73.998672;
        String label = name;
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void callPhone(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }

    public void favouriteChecked(View v){
        com.github.clans.fab.FloatingActionButton favFab=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab22);
        if(isFavourite==0){
            favFab.setImageResource(R.drawable.ic_favorite_white_24px);
            isFavourite=1;
        }
        else{
            favFab.setImageResource(R.drawable.ic_favorite_border_white_24px);
            isFavourite=0;
        }
    }


    public void ratingCheck(View v){
        final Dialog rankDialog = new Dialog(ShopDetailsActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        rankDialog.setContentView(R.layout.rank_dialog);
        rankDialog.setCancelable(true);
        final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
        ratingBar.setRating(0);

        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
        text.setText(name);

        ImageButton updateButton = (ImageButton) rankDialog.findViewById(R.id.rank_dialog_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            //Log.v("tag",snapshot.child("shop_id"));
                            if(snapshot.hasChild("shop_id") && snapshot.child("shop_id").getValue().toString().equals(id)){
                                String presentPoints;
                                String presentNum;
                                presentPoints = snapshot.child("totalRatePoints").getValue().toString();
                                presentNum = snapshot.child("numRates").getValue().toString();

//                                Log.v("tag",presentPoints);
//                                Log.v("tag",presentNum);
//                                Log.v("tag",Float.toString(Float.parseFloat(presentPoints)+ratingBar.getRating()));
//                                Log.v("tag",Integer.toString(Integer.parseInt(presentNum)+1));

                                //FirebaseDatabase.getInstance().getReference().child(industry).child(snapshot.getKey()).child("totalRatePoints").setValue(Float.toString(Float.parseFloat(presentPoints)+ratingBar.getRating()));
                                snapshot.getRef().child("totalRatePoints").setValue(Float.toString(Float.parseFloat(presentPoints)+ratingBar.getRating()));
                                snapshot.getRef().child("numRates").setValue(Integer.toString(Integer.parseInt(presentNum)+1));

                                rankDialog.dismiss();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //now that the dialog is set up, it's time to show it
        rankDialog.show();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
