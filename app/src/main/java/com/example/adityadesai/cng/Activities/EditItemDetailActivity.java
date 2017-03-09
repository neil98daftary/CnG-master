package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemDetailActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private EditText itemNameEditText;
    private EditText itemPriceEditText;
    private EditText itemDescriptionEditText;

    private String itemName;
    private String itemPrice;
    private String itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemNameEditText=(EditText)findViewById(R.id.edit_item_detail_name);
        itemPriceEditText=(EditText)findViewById(R.id.edit_item_detail_price);
        itemDescriptionEditText=(EditText)findViewById(R.id.edit_item_detail_description);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.item_detail_edit_done_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName=itemNameEditText.getText().toString();
                itemPrice=itemPriceEditText.getText().toString();
                itemDescription=itemDescriptionEditText.getText().toString();

                mFirebaseDatabase=FirebaseDatabase.getInstance();
                mDatabaseReference=mFirebaseDatabase.getReference().child(VendorItemListActivity.industryName).child(VendorItemListActivity.id).child(VendorItemDetailsActivity.item_name);

                mDatabaseReference.push().setValue(new ItemDetail(itemName,itemPrice,itemDescription));

                Intent i =new Intent(view.getContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }

}
