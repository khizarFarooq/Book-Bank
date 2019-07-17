package com.namaltechnologysolutions.bunny.bookbank.Activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Contact_Us_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Home_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.My_Requests_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Others_Request_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Post_a_Request_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Notification.Notification_Service;
import com.namaltechnologysolutions.bunny.bookbank.R;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.User_Profile_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.View_Setting_Fragment;

public class Navigation_Drawer_Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Post_a_Request_Fragment.postARequestInterface,Home_Fragment.Home_Fragment_Interface,View_Setting_Fragment.view_Setting_Fragment_Interface{
    // Objects Declaration
    ImageView profileImage;
    TextView userName;
    String currentProfileImageUrl,currentUserName,currentUserUid;
    FragmentTransaction ft;
    public static Context context;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabaseRef;
    private DatabaseReference databaseReference;
    private ProgressDialog logOutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_navigation_drawer_home);
        loadNotifications();

        // Objects Initialization
        firebaseDatabaseRef=firebaseDatabaseRef.getInstance();
        databaseReference=firebaseDatabaseRef.getReference();
        loadCurrentUserImageAndName();
        // Progress Dialogue instance initialization
        logOutProgress=new ProgressDialog(this);
        // Fragment Replacement
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet, new Home_Fragment() );
        ft.addToBackStack(null).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerViews=navigationView.getHeaderView(0);
        profileImage=headerViews.findViewById(R.id.profile_image);
        userName=headerViews.findViewById(R.id.userName);
    }

    private void loadNotifications() {
        FirebaseDatabase.getInstance().getReference().child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userId: dataSnapshot.getChildren()){
                    if(!userId.getKey().equals("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        Intent intent=new Intent(Navigation_Drawer_Home_Activity.this, Notification_Service.class);
                        startService(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Load Current user information from Firebase
    private void loadCurrentUserImageAndName() {
        firebaseAuth=firebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            currentUserUid=currentUser.getUid();
            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    currentUserName=(String) dataSnapshot.child("Name").getValue();
                    currentProfileImageUrl=(String)dataSnapshot.child("Profile Image Url").getValue();
                    displayUserProfileImageAndName();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
// Display Current user information
    private void displayUserProfileImageAndName() {
        if(!currentProfileImageUrl.equals("None")){
            Glide.with(this/* context */)
                    .load(currentProfileImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileImage);
        }
        userName.setText(currentUserName);
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        FrameLayout  frameLayout  = findViewById(R.id.homefragmnet);
        if ( frameLayout .getChildCount() == 1) {
            super.onBackPressed();
            if (frameLayout.getChildCount() == 0) {
                finish();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer__home_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home_Fragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
    // Navigation on item selection of Navigation Drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.home:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new Home_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.myAccount:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new User_Profile_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.viewOtheRequest:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new Others_Request_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.postARequest:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new Post_a_Request_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.myRequests:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new My_Requests_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.ContactUs:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new Contact_Us_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.viewSetting:
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.homefragmnet, new View_Setting_Fragment() );
                ft.addToBackStack(null).commit();
                break;
            case R.id.signOut:
                    logOutProgress.setTitle("Signing Out...");
                    logOutProgress.setMessage("Thanks for using Book Bank");
                    logOutProgress.setCanceledOnTouchOutside(false);
                    logOutProgress.show();
                firebaseAuth.getInstance().signOut();
                Intent moveToLoginRegisterActivity=new Intent(Navigation_Drawer_Home_Activity.this,Login_Register_Activity.class);
                startActivity(moveToLoginRegisterActivity);
                finish();
                logOutProgress.dismiss();
                break;
        }
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // Interface Methods to replace fragments.
    @Override
    public void postARequestInterfaceMethod() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet, new Home_Fragment() );
        ft.addToBackStack(null).commit();
    }

    @Override
    public void Home_Fragment_Interface_Method_Post_Request_Icon() {
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet,new Post_a_Request_Fragment());
        ft.addToBackStack(null).commit();
    }

    @Override
    public void Home_Fragment_Interface_Method_ContactUs_Icon() {
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet,new Contact_Us_Fragment());
        ft.addToBackStack(null).commit();
    }

    @Override
    public void Home_Fragment_Interface_Method_ownRequests_Icon() {
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet,new My_Requests_Fragment());
        ft.addToBackStack(null).commit();
    }

    @Override
    public void Home_Fragment_Interface_Method_OtherReuest_Icon() {
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet,new Others_Request_Fragment());
        ft.addToBackStack(null).commit();
    }

    @Override
    public void view_Setting_Fragment_Interface_Method_Language_Change() {
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.homefragmnet,new Home_Fragment());
        ft.addToBackStack(null).commit();
    }
}
