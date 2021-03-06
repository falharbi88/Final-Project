package edu.depaul.csc472.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.depaul.csc472.finalproject.Model.Meal;

public class MealDetailsActivity extends AppCompatActivity {
    public static final int MEAL_CODE=120;

    public int mealNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mealNumber = getIntent().getIntExtra("MealNumber",0);
        final Meal meal = MenuActivity.myMeals.get(mealNumber);
        toolbar.setTitle(meal.getNameMeal().toUpperCase());
        setSupportActionBar(toolbar);
        ImageView image = (ImageView) findViewById(R.id.mealImage);
        TextView mealNameText = (TextView) findViewById(R.id.mealName);
        TextView mealPriceText = (TextView) findViewById(R.id.mealPrice);
        Picasso.with(MealDetailsActivity.this).load(meal.getImageMeal()).into(image);
        mealNameText.setText(meal.getNameMeal());
        mealPriceText.setText("$"+meal.getPriceMeal());
        TextView adText = (TextView) findViewById(R.id.adText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, meal.getNameMeal() + " Added To Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                MenuActivity.myOrders.add(meal);

            }
        });

    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode==MEAL_CODE){
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
            Toast.makeText(MealDetailsActivity.this, "You selected about us", Toast.LENGTH_LONG).show();
            return true;
        }else
        if(id == R.id.action_cart) {
            Toast.makeText(MealDetailsActivity.this, "You selected cart page", Toast.LENGTH_LONG).show();
            Intent cart = new Intent(MealDetailsActivity.this,CartActivity.class);
            //startActivity(cart);
            startActivityForResult(cart,MEAL_CODE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

