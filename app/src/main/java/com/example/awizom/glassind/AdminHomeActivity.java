package com.example.awizom.glassind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.Adapters.UserProfileAdapter;
import com.example.awizom.glassind.Model.DataWorkOrder;
import com.example.awizom.glassind.Model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    TextView email;
    RecyclerView recyclerView;
    ProgressDialog progressDialog ;
    DatabaseReference datauser;
    List<UserProfile> userList;
    UserProfileAdapter adapter;
    UserProfile currentUserProfile;
    String useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_home );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        mAuth = FirebaseAuth.getInstance();

        View header = navigationView.getHeaderView(0);
        email = header.findViewById(R.id.textView);
        email.setText(  mAuth.getCurrentUser().getEmail() );
      /*  View hView = navigationView.inflateHeaderView(R.layout.nav_header_admin_home);
        email = hView.findViewById(R.id.textView);*/
        progressDialog = new ProgressDialog(this);
        userList=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getUserList();
        getUser();
        getSupportActionBar().setTitle(currentUserProfile.getRole());


    }
    private void getUser() {
        try {
            //String res="";
            useremail=mAuth.getCurrentUser().getEmail();
            progressDialog.setMessage("loading...");
            progressDialog.show();
           datauser = FirebaseDatabase.getInstance().getReference("userprofile");
            //without filter
            // dataorder.addListenerForSingleValueEvent(valueEventListener);
            Query query= FirebaseDatabase.getInstance().getReference("userprofile")
                    .orderByChild("email")  .equalTo( useremail );
            // query.addListenerForSingleValueEvent(valueEventListener);
            query.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   userList.clear();

                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        currentUserProfile=new UserProfile(  );
                      currentUserProfile = postSnapshot.getValue(UserProfile.class);
                        //adding artist to the list

                    }

                    progressDialog.dismiss();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
            //   new MyCourse.GETCourseList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
            //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Error: " + e);
        }
    }
    private void getUserList() {
        try {
            //String res="";

            progressDialog.setMessage("loading...");
            progressDialog.show();
            datauser = FirebaseDatabase.getInstance().getReference("userprofile");
            //without filter
            // dataorder.addListenerForSingleValueEvent(valueEventListener);
            // Query query= FirebaseDatabase.getInstance().getReference("userprofile")
            //   .orderByChild("id");
            // query.addListenerForSingleValueEvent(valueEventListener);
            datauser.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    userList.clear();

                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        UserProfile userProfile = postSnapshot.getValue(UserProfile.class);
                        //adding artist to the list
                        userList.add(userProfile);
                    }
                    //creating adapter
                    adapter=new UserProfileAdapter( AdminHomeActivity.this, userList);
                    //attaching adapter to the listview
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    //when u want to see only perticuller update then add notify hear and
                    //creating adapter and attaching adapter to the listview at oncreate method
                    // adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
            //   new MyCourse.GETCourseList().execute(SharedPrefManager.getInstance(this).getUser().access_token);
            //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Error: " + e);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.admin_home, menu );
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_track_order) {

            startActivity(new Intent(this,TrackOrderActivity.class));

        } else if (id == R.id.nav_user_setting) {

        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,LogInActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
