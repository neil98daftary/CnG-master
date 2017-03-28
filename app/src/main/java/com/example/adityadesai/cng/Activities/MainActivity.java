package com.example.adityadesai.cng.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.adityadesai.cng.NavDrawerFragments.FeedbackPageFragment;
import com.example.adityadesai.cng.NavDrawerFragments.HomePageFragment;
import com.example.adityadesai.cng.NavDrawerFragments.MyShopsFragment;
import com.example.adityadesai.cng.NavDrawerFragments.OffersPageFragment;
//import com.example.adityadesai.cng.Objects.Id;
import com.example.adityadesai.cng.Objects.Industry;
import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN=1;

    FragmentManager mFragmentManager;

    SharedPreferences.Editor editor2;
    /*Initializing Firebase variables*/

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser user;


    private String mUsername;
    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = ANONYMOUS;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code here
            editor2=getSharedPreferences("signInMode",MODE_APPEND).edit();
            editor2.putBoolean("isCustomer",true);
            editor2.commit();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        /*Firebase Stuff*/




        //Initializing id folder
        /*FirebaseDatabase fd=FirebaseDatabase.getInstance();
        DatabaseReference dr=fd.getReference().child("id");
        dr.push().setValue(new Industry("Groceries","GR"));
        dr.push().setValue(new Industry("Decor","DE"));
        dr.push().setValue(new Industry("Gym","GY"));
        dr=fd.getReference().child("id").child("Groceries");
        dr.push().setValue(new Id("GR000000001"));
        dr=fd.getReference().child("id").child("Decor");
        dr.push().setValue(new Id("DE000000001"));
        dr=fd.getReference().child("id").child("Gym");
        dr.push().setValue(new Id("GY000000001"));*/



//        Intent i = getIntent();
//        Bundle b = i.getExtras();

//        name = b.getString("username");
//        url = b.getString("photoUrl");


        // Transition
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting default start-up fragment to home page
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame,new HomePageFragment());
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(MainActivity.this, "You're now signed in. Welcome to CnG!", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());

                } else {
                    // User is signed out

                    onSignedOutCleanup();
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
        //mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        //name=user.getDisplayName();

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

    private void onSignedInInitialize(String username) {
        mUsername = username;
        //user.sendEmailVerification();
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

    private void onSignedOutCleanup(){
        mUsername=ANONYMOUS;
    }

    public void makeVendor(View v){
        editor2=getSharedPreferences("signInMode",MODE_APPEND).edit();
        editor2.putBoolean("isCustomer",false);
        editor2.commit();
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(user.getDisplayName(),"username");
        //intent.putExtra(user.getPhotoUrl(),"photoUrl");
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify VendorItemListActivity parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment=null;

        if (id == R.id.home) {
            fragment=new HomePageFragment();
        } else if (id == R.id.offers) {
            fragment=new OffersPageFragment();
        } else if (id == R.id.feedback) {
            fragment=new FeedbackPageFragment();
        }else if (id == R.id.myshop) {
            fragment=new MyShopsFragment();
        } else if (id == R.id.sign_out) {
            // Till sign out page is not made
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                        }
                    });
        }


        // Switching out current fragment for new fragment
        if(id != R.id.sign_out) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
