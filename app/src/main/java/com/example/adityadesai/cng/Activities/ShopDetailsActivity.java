package com.example.adityadesai.cng.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.adityadesai.cng.Adapters.CustomAdapter;
import com.example.adityadesai.cng.Adapters.CustomPagerAdapter;
import com.example.adityadesai.cng.Adapters.MenuRecyclerAdapter;
import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
    private String address;
    private String phone;
    private String industry;
    public static String id;

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

        Intent i = getIntent();
        name = i.getStringExtra("shopName");
        address = i.getStringExtra("shopAddress");
        phone = i.getStringExtra("shopPhone");
        industry=i.getStringExtra("industry_name");
        id = i.getStringExtra("shop_id");

//fa


//        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar1);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Offers"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Creating our pager adapter
        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);














        // Set title
        android.support.design.widget.CollapsingToolbarLayout toolbar=(android.support.design.widget.CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        toolbar.setTitle(name);


        // Sliding panel
        SlidingUpPanelLayout slidingPanel= (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingPanel.setPanelHeight(100);

        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.shop_image_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mItemDatabaseReference = mFirebaseDatabase.getReference().child(industry).child(id); //change industry to ShopListActivity.shop_type
        mStorageReference = mFirebaseStorage.getReference().child("item_photos");// if it doesnt work




        //mRecyclerView = (RecyclerView) findViewById(R.id.shop_menu_recycler);
        //mGridLayoutManager=new GridLayoutManager(this,2);
        //mRecyclerView.setLayoutManager(mGridLayoutManager);

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





        final ArrayList<String> mOfferItems = new ArrayList<>();
        mOfferItems.add("Buy 1 get 1 free on Item A");
        mOfferItems.add("Buy 2 get 1 free on Item B");
        mOfferItems.add("Flat 50% off on Item C");
        mOfferItems.add("Buy 1 get 1 free on Item D");


        mListView = (ListView)findViewById(R.id.offers_list);
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mOfferItems);
        mListView.setAdapter(mArrayAdapter);

        SlidingUpPanelLayout.PanelSlideListener mSlidePanelListener=new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                TextView title=(TextView)panel.findViewById(R.id.title_offer);
                ImageView icon=(ImageView)panel.findViewById(R.id.slide_panel_icon);
                View decorView = getWindow().getDecorView();
                if(slideOffset==1){
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                    title.setText("Slide down to close!");
                    icon.setImageResource(android.R.drawable.arrow_down_float);
                }
                else{
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
                    title.setText("Slide up to view offers!");
                    icon.setImageResource(android.R.drawable.arrow_up_float);
                }
            }
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        };

        slidingPanel.addPanelSlideListener(mSlidePanelListener);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void openAddress(View v){
        new AlertDialog.Builder(this)
                .setTitle("Address")
                .setMessage(address)
                .setIcon(R.drawable.ic_home_24dp)
                .show();
    }

    public void openPhone(View v){
        new AlertDialog.Builder(this)
                .setTitle("Phone Number")
                .setMessage(phone)
                .setNeutralButton("Call",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phone));
                        startActivity(intent);
                    }
                })
                .setIcon(R.drawable.ic_home_24dp)
                .show();
    }

    public void favouriteChecked(View v){
        FloatingActionButton favFab=(FloatingActionButton)findViewById(R.id.favFab);
        if(isFavourite==0){
            favFab.setImageResource(R.drawable.ic_favorite);
            isFavourite=1;
        }
        else{
            favFab.setImageResource(R.drawable.ic_unfavorite);
            isFavourite=0;
        }
    }


    public void ratingCheck(View v){
        final Dialog rankDialog = new Dialog(ShopDetailsActivity.this, R.style.FullHeightDialog);
        rankDialog.setContentView(R.layout.rank_dialog);
        rankDialog.setCancelable(true);
        RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
        ratingBar.setRating(0);

        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
        text.setText(name);

        Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankDialog.dismiss();
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
