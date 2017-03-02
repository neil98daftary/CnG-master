package com.example.adityadesai.cng.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adityadesai.cng.Adapters.CustomPagerAdapter;
import com.example.adityadesai.cng.Adapters.MenuRecyclerAdapter;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ShopDetailsActivity extends AppCompatActivity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MenuRecyclerAdapter mAdapter;
    ArrayAdapter mArrayAdapter;

    private String name;
    private String address;
    private String phone;

    private ListView mListView;
    int isFavourite=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        Intent i = getIntent();
        name = i.getStringExtra("shopName");
        address = i.getStringExtra("shopAddress");
        phone = i.getStringExtra("shopPhone");

        // Set title
        android.support.design.widget.CollapsingToolbarLayout toolbar=(android.support.design.widget.CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        toolbar.setTitle(name);

        // Transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Back button
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Sliding panel
        SlidingUpPanelLayout slidingPanel= (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingPanel.setPanelHeight(100);

        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.shop_image_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        mRecyclerView = (RecyclerView) findViewById(R.id.shop_menu_recycler);
        mGridLayoutManager=new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        final ArrayList<MenuItem> mMenuItems= new ArrayList<>();
        mMenuItems.add(new MenuItem("Item A"));
        mMenuItems.add(new MenuItem("Item B"));
        mMenuItems.add(new MenuItem("Item C"));
        mMenuItems.add(new MenuItem("Item D"));
        mMenuItems.add(new MenuItem("Item E"));
        mMenuItems.add(new MenuItem("Item F"));
        mMenuItems.add(new MenuItem("Item G"));
        mMenuItems.add(new MenuItem("Item H"));
        mMenuItems.add(new MenuItem("Item I"));
        mMenuItems.add(new MenuItem("Item J"));

        mAdapter = new MenuRecyclerAdapter(mMenuItems);
        mRecyclerView.setAdapter(mAdapter);

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
    }

    // Go back
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        Intent i = new Intent(this, ShopListActivity.class);
        startActivity(i);
        return true;
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
}
