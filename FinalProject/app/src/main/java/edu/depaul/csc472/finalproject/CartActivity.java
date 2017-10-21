package edu.depaul.csc472.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.depaul.csc472.finalproject.Model.Order;


public class CartActivity extends AppCompatActivity {
    public boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Button submitButton = (Button) findViewById(R.id.SubmitButton);
        TextView textSummary = (TextView) findViewById(R.id.TextSummary);
        TextView mealsSummary = (TextView) findViewById(R.id.MealsSummary);
        TextView mealsPrices = (TextView) findViewById(R.id.MealsPrices);
        TextView totalPrice = (TextView) findViewById(R.id.PriceText);

        Double sum = 0.0;

        if(!MenuActivity.myOrders.isEmpty()){
            flag=true;
            textSummary.setText("Your Order is: \n");
            for (int i=0; i< MenuActivity.myOrders.size(); i++){
                mealsSummary.setText(mealsSummary.getText().toString()+"Meal "+String.valueOf(i+1)+ ":\n");
                mealsPrices.setText(mealsPrices.getText().toString()+ "\n");
                mealsSummary.setText(mealsSummary.getText().toString()+MenuActivity.myOrders.get(i).getNameMeal()+"\n\n");
                mealsPrices.setText(mealsPrices.getText().toString()+"$"+MenuActivity.myOrders.get(i).getPriceMeal()+"\n\n");
                sum = sum + Double.valueOf(MenuActivity.myOrders.get(i).getPriceMeal());
            }
            totalPrice.setText("$"+String.valueOf(sum));
        }

       submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(flag){
                    final Dialog dialog = new Dialog(CartActivity.this);
                    dialog.setContentView(R.layout.custom);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            writeIntoDatabase();

                        }
                    });
                    dialog.show();
                }
            }
        });


    }

    private void writeIntoDatabase() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_orders = database.getReference("Orders");
        final String username = HomeActivity.username;
        Random r = new Random();
        Integer count = r.nextInt(1000) + 1;
        table_orders.child(username).child(MenuActivity.truckName).child((count).toString());
        for(Integer i=0; i<MenuActivity.myOrders.size(); i++) {
            Integer j = i+1;
            table_orders.child(username).child(MenuActivity.truckName).child((count).toString()).child(j.toString()).setValue(MenuActivity.myOrders.get(i));
        }
        MenuActivity.myOrders.clear();
        setResult(RESULT_OK);
        finish();
    }
}
