package com.example.adityadesai.cng.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityadesai.cng.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.*;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Arrays;


public class ChooseActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;
    private TextView userName;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        mFirebaseAuth = FirebaseAuth.getInstance();
        userName = (TextView) findViewById(R.id.username);
        editor=getSharedPreferences("signInMode",MODE_APPEND).edit();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(ChooseActivity.this, "You're now signed in. Welcome to CnG!", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize();

                } else {
                    // User is signed out


                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void onSignedInInitialize() {
        userName.setText(user.getEmail());
        user.sendEmailVerification();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    public void Buyer(View view)
    {

        editor.putBoolean("isCustomer",true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(user.getDisplayName(),"username");
        //intent.putExtra(user.getPhotoUrl().toString(),"photoUrl");
        startActivity(intent);
    }

    public void Vendor(View view)
    {
        editor.putBoolean("isCustomer",false);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(user.getDisplayName(),"username");
        //intent.putExtra(user.getPhotoUrl(),"photoUrl");
        startActivity(intent);
    }
}
