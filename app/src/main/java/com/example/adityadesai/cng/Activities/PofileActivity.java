package com.example.adityadesai.cng.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.adityadesai.cng.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.adityadesai.cng.R;
import com.google.firebase.database.ValueEventListener;

public class PofileActivity extends AppCompatActivity {

    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserPic;
    private EditText mUserPhone;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,"Please dont save if u have already with this account",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pofile);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");

        mUserName = (TextView) findViewById(R.id.userName);
        mUserEmail = (TextView) findViewById(R.id.userEmail);
        mUserPic = (ImageView) findViewById(R.id.userPic);
        mUserPhone = (EditText) findViewById(R.id.userPhone);

        sharedPrefs = getSharedPreferences("userInfo",MODE_APPEND);
        mUserName.setText(sharedPrefs.getString("name",null));
        mUserEmail.setText(sharedPrefs.getString("email",null));

        Glide.with(this).load(sharedPrefs.getString("pic",null)).into(mUserPic);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("uid").getValue().equals(sharedPrefs.getString("uid",null))){
                        Toast.makeText(getBaseContext(),"U have already registered",Toast.LENGTH_SHORT);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void saveUserDetails(View view){
        SharedPreferences.Editor e = sharedPrefs.edit();
        e.putString("phone",mUserPhone.getText().toString());
        mDatabaseReference.push().setValue(new User(sharedPrefs.getString("name","test"),
                                                    sharedPrefs.getString("uid","test"),
                                                    sharedPrefs.getString("email","test"),
                                                    mUserPhone.getText().toString(),
                                                    sharedPrefs.getString("pic",null)));

        Toast.makeText(this,"Details Saved",Toast.LENGTH_SHORT).show();
        finish();
    }
}
