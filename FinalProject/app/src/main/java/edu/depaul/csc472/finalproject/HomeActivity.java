package edu.depaul.csc472.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.depaul.csc472.finalproject.Model.Truck;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int HOME_CODE=100;
    public static List<Truck> myTrucks = new ArrayList<Truck>();
    public ListView lv;
    public String truckName = "";
    public static String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Food Trucks");
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.trucksList);
        username = getIntent().getStringExtra("Username");
        myTrucks.clear();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_truck = database.getReference("Trucks");

        table_truck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    Truck truck = data.getValue(Truck.class);
                    myTrucks.add(truck);

                }
                TruckListAdapter adapter = new TruckListAdapter(HomeActivity.this,R.layout.listview,myTrucks);
                lv.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                truckName = myTrucks.get(i).getTruckName();
                Intent menuActivity = new Intent(HomeActivity.this,MenuActivity.class);
                menuActivity.putExtra("TruckName",truckName);
                //startActivity(menuActivity);
                startActivityForResult(menuActivity,HOME_CODE);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, myTrucks.get(1).getTruckName().toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //for(Truck truck: myTrucks){
                 //   Snackbar.make(view, truck.toString(), Snackbar.LENGTH_LONG)
                  //          .setAction("Action", null).show();

                //}
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode==HOME_CODE){
            if(resultCode==RESULT_OK){

            }
        }


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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        if(id == R.id.nav_profile){
            Intent profileActivity = new Intent(HomeActivity.this,ProfileActivity.class);
            profileActivity.putExtra("Username",username);
            startActivity(profileActivity);

        }else if (id == R.id.nav_order) {
            Intent orderHistoryActivity = new Intent(HomeActivity.this,OrderHistoryActivity.class);
            orderHistoryActivity.putExtra("Username",username);
            startActivity(orderHistoryActivity);

        } else if (id == R.id.nav_logout) {
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
