package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.adityadesai.cng.Adapters.VendorItemListAdapter;
import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

public class VendorItemListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private VendorItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        mAdapter = new VendorItemListAdapter(mMenuItems);
        mRecyclerView.setAdapter(mAdapter);
    }

}
