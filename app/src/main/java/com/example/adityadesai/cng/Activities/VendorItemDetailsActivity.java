package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.adityadesai.cng.Adapters.VendorItemDetailAdapter;
import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

import java.util.ArrayList;

public class VendorItemDetailsActivity extends AppCompatActivity {

    private ListView mListView;
    private VendorItemDetailAdapter mAdapter;
    private ArrayList<ItemDetail> mDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_item_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        mDetailList.add(new ItemDetail("Name A", "Rs 100", "Description A"));
        mDetailList.add(new ItemDetail("Name B", "Rs 100", "Description B"));
        mDetailList.add(new ItemDetail("Name C", "Rs 100", "Description C"));
        mDetailList.add(new ItemDetail("Name D", "Rs 100", "Description D"));
        mDetailList.add(new ItemDetail("Name E", "Rs 100", "Description E"));
        mDetailList.add(new ItemDetail("Name F", "Rs 100", "Description F"));
        mDetailList.add(new ItemDetail("Name G", "Rs 100", "Description G"));
        mDetailList.add(new ItemDetail("Name H", "Rs 100", "Description H"));
        mDetailList.add(new ItemDetail("Name I", "Rs 100", "Description I"));
        mDetailList.add(new ItemDetail("Name J", "Rs 100", "Description J"));

        mAdapter = new VendorItemDetailAdapter(this, mDetailList);
        mListView.setAdapter(new SlideExpandableListAdapter(
                mAdapter,
                R.id.item_detail_view,
                R.id.item_description
        ));
    }

}
