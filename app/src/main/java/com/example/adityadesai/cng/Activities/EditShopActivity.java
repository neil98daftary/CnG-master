package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.adityadesai.cng.Activities.ShopListActivity.shop_type;
import static com.example.adityadesai.cng.Activities.VendorItemListActivity.industryName;
import static java.security.AccessController.getContext;

public class EditShopActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mIdFirebasDatabase;
    private DatabaseReference mIdDatabaseReference;
    private DatabaseReference mIdDatabaseReference2;

    private EditText industryNameEditText;
    private EditText shopNameEditText;
    private EditText shopPhoneEditText;
    private EditText shopAddressEditText;

    private String shopIndustry;
    private String shopName;
    private String shopPhone;
    private String shopAddress;
    private String shopId;
    private String finalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        industryNameEditText=(EditText)findViewById(R.id.edit_shop_industry);
        shopNameEditText=(EditText)findViewById(R.id.edit_shop_name);
        shopPhoneEditText=(EditText)findViewById(R.id.edit_shop_phone);
        shopAddressEditText=(EditText)findViewById(R.id.edit_shop_address);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shop_edit_done_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopIndustry=industryNameEditText.getText().toString();
                shopName=shopNameEditText.getText().toString();
                shopPhone=shopPhoneEditText.getText().toString();
                shopAddress=shopAddressEditText.getText().toString();

                mFirebaseDatabase=FirebaseDatabase.getInstance();
                mDatabaseReference=mFirebaseDatabase.getReference().child(shopIndustry);

                getId();

                /*mDatabaseReference.push().setValue(new Shop(shopName,shopAddress,shopPhone,shopId,shopIndustry));

                Intent i =new Intent(view.getContext(),MainActivity.class);
                startActivity(i);*/

            }
        });
    }

    private void getId(){
        mIdFirebasDatabase=FirebaseDatabase.getInstance();
        mIdDatabaseReference=mIdFirebasDatabase.getReference().child("id");

        mIdDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(((String)snapshot.child("name").getValue()).equals(shopIndustry)){
                        shopId=(String)snapshot.child("id").getValue();
                    }
                }
                pushShop(shopId);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void pushShop(String shopId){
        finalId=Integer.toString((Integer.parseInt(shopId))+1);
        mDatabaseReference.push().setValue(new Shop(shopName,shopAddress,shopPhone,finalId,shopIndustry));
        updateId(finalId);

        Intent i =new Intent(getBaseContext(),MainActivity.class);
        startActivity(i);
    }

    private void updateId(final String id){
        mIdDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(((String)snapshot.child("name").getValue()).equals(shopIndustry)){
                        snapshot.getRef().child("id").setValue(id);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
