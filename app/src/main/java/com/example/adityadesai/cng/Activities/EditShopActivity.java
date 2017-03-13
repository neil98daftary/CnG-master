package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.Objects.Shop;
import com.example.adityadesai.cng.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.adityadesai.cng.Activities.ShopListActivity.shop_type;
import static java.security.AccessController.getContext;

public class EditShopActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mIdFirebasDatabase;
    private DatabaseReference mIdDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    private EditText industryNameEditText;
    private EditText shopNameEditText;
    private EditText shopPhoneEditText;
    private EditText shopAddressEditText;
    private ImageView mImageView;

    private String shopIndustry;
    private String shopName;
    private String shopPhone;
    private String shopAddress;
    private String shopId;
    private String finalId;
    private Uri imageUrl;

    private static final int RC_PHOTO_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("shop_photos");

        industryNameEditText=(EditText)findViewById(R.id.edit_shop_industry);
        shopNameEditText=(EditText)findViewById(R.id.edit_shop_name);
        shopPhoneEditText=(EditText)findViewById(R.id.edit_shop_phone);
        shopAddressEditText=(EditText)findViewById(R.id.edit_shop_address);
        mImageView = (ImageView) findViewById(R.id.imageView);

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

    public void uploadPic(View view){
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
                                imageUrl = taskSnapshot.getDownloadUrl();
                                // Set the download URL to the message box, so that the user can send it to the database
                                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this,imageUrl);
                                Glide.with(mImageView.getContext())
                                        .load(imageUrl.toString())
                                        .into(mImageView);
                            }
                        });
            }
        }

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
        mDatabaseReference.push().setValue(new Shop(shopName,shopAddress,shopPhone,finalId,shopIndustry,imageUrl.toString()));
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
