package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adityadesai.cng.Objects.MenuItem;
import com.example.adityadesai.cng.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemListActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private EditText itemNameEditText;

    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemNameEditText=(EditText)findViewById(R.id.edit_item_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.item_edit_done_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName=itemNameEditText.getText().toString();

                mFirebaseDatabase=FirebaseDatabase.getInstance();
                mDatabaseReference=mFirebaseDatabase.getReference().child(VendorItemsListActivity.industryName).child(VendorItemsListActivity.id);

                mDatabaseReference.push().setValue(new MenuItem(itemName));

                Intent i =new Intent(view.getContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }

}
