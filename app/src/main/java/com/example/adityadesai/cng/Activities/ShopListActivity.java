package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.adityadesai.cng.Adapters.ShopRecyclerAdapter;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ShopRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_shop_list);

        // Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get and display IID
        Intent i= getIntent();
        Bundle b = i.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("id");
        }

        // Recyclerview code
        mRecyclerView = (RecyclerView) findViewById(R.id.shop_list);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        final ArrayList<Shop> mShopList= new ArrayList<>();
        mShopList.add(new Shop("Shop A","Address A","0000"));
        mShopList.add(new Shop("Shop B","Address B","0001"));
        mShopList.add(new Shop("Shop C","Address C","0002"));
        mShopList.add(new Shop("Shop D","Address D","0003"));
        mShopList.add(new Shop("Shop E","Address E","0004"));
        mShopList.add(new Shop("Shop F","Address F","0005"));
        mShopList.add(new Shop("Shop G","Address G","0006"));
        mShopList.add(new Shop("Shop H","Address H","0007"));
        mShopList.add(new Shop("Shop I","Address I","0008"));
        mShopList.add(new Shop("Shop J","Address J","0009"));
        mShopList.add(new Shop("Shop K","Address K","0010"));

        mAdapter = new ShopRecyclerAdapter(mShopList);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Go back
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        return true;

    }
}
