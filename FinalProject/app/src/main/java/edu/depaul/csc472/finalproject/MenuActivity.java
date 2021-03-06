package edu.depaul.csc472.finalproject;

import android.content.Intent;
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

import edu.depaul.csc472.finalproject.Model.Meal;


public class MenuActivity extends AppCompatActivity {

    public static final int MENU_CODE=110;

    public static List<Meal> myMeals = new ArrayList<Meal>();
    public static List<Meal> myOrders = new ArrayList<Meal>();
    public ListView lv;
    public static String truckName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lv = (ListView) findViewById(R.id.menuList);
        truckName = getIntent().getStringExtra("TruckName");
        myMeals.clear();
        myOrders.clear();

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
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    Meal meal = data.getValue(Meal.class);
                    myMeals.add(meal);
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
                Intent mealActivity = new Intent(MenuActivity.this,MealDetailsActivity.class);
                mealActivity.putExtra("MealNumber",i);
                //startActivity(mealActivity);
                startActivityForResult(mealActivity,MENU_CODE);

            }
        });

    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode==MENU_CODE){
            if(resultCode==RESULT_OK){
                setResult(RESULT_OK);
                finish();
            }
        }


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
                Intent cart = new Intent(MenuActivity.this,CartActivity.class);
                //startActivity(cart);
                startActivityForResult(cart,MENU_CODE);
                return true;
            }

        return super.onOptionsItemSelected(item);
    }
}
