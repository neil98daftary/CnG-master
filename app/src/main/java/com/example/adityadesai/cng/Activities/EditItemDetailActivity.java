package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditItemDetailActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private EditText itemNameEditText;
    private EditText itemPriceEditText;
    private EditText itemDescriptionEditText;
    private ImageView itemPhoto;

    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private Uri itemUri;

    private static final int RC_PHOTO_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemNameEditText=(EditText)findViewById(R.id.edit_item_detail_name);
        itemPriceEditText=(EditText)findViewById(R.id.edit_item_detail_price);
        itemDescriptionEditText=(EditText)findViewById(R.id.edit_item_detail_description);
        itemPhoto = (ImageView)findViewById(R.id.itemImage);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("item_photos");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.item_detail_edit_done_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName=itemNameEditText.getText().toString();
                itemPrice=itemPriceEditText.getText().toString();
                itemDescription=itemDescriptionEditText.getText().toString();

                mFirebaseDatabase=FirebaseDatabase.getInstance();
                mDatabaseReference=mFirebaseDatabase.getReference().child(VendorItemsListActivity.industryName).child(VendorItemsListActivity.id).child(VendorItemDetailsActivity.item_name);

                mDatabaseReference.push().setValue(new ItemDetail(itemName,itemPrice,itemDescription,itemUri.toString()));

                Intent i =new Intent(view.getContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void uploadItemPic(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER){
            if(resultCode == RESULT_OK){
                Uri selectedImageUri = data.getData();
                StorageReference photoRef = mStorageReference.child(selectedImageUri.getLastPathSegment());

                photoRef.putFile(selectedImageUri)
                        .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // When the image has successfully uploaded, we get its download URL
                                itemUri = taskSnapshot.getDownloadUrl();
                                // Set the download URL to the message box, so that the user can send it to the database
                                Glide.with(itemPhoto.getContext()).load(itemUri).into(itemPhoto);
                            }
                        });
            }
        }

    }
}
