package edu.depaul.csc472.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.csc472.finalproject.Model.Menu;


public class MenuActivity extends AppCompatActivity {

    public static List<Menu> myMeals = new ArrayList<Menu>();
    public ListView lv;
    String menu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lv = (ListView) findViewById(R.id.menuList);
        String truckName = getIntent().getStringExtra("TruckName");
        myMeals.clear();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(truckName.toUpperCase());
        setSupportActionBar(toolbar);

        //TextView myTruck = (TextView) findViewById(R.id.textView);
        //myTruck.setText(truckName.toString());

        final FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        final DatabaseReference table_menu = database.getReference(truckName);

        table_menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Menu menu = dataSnapshot.child("02").getValue(Menu.class);
                //Toast.makeText(MenuActivity.this, menu.toString(), Toast.LENGTH_LONG).show();

                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    Menu menu = data.getValue(Menu.class);
                    Toast.makeText(MenuActivity.this, menu.toString(), Toast.LENGTH_LONG).show();
                    myMeals.add(menu);

                }
                MenuListAdapter adapter = new MenuListAdapter(MenuActivity.this, R.layout.menuview, myMeals);
                lv.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //menu = myTrucks.get(i).getTruckName();
                //Intent menuActivity = new Intent(HomeActivity.this,MenuActivity.class);
                //menuActivity.putExtra("TruckName",menu);
                //startActivity(menuActivity);
            }
        });

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meals_page, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_us) {
            Toast.makeText(MenuActivity.this, "You selected about us", Toast.LENGTH_LONG).show();
            return true;
        }else
            if(id == R.id.action_cart) {
                Toast.makeText(MenuActivity.this, "You selected cart page", Toast.LENGTH_LONG).show();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }
}
