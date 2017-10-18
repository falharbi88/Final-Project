package edu.depaul.csc472.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
        //String st = "";
        //String my = "";
        if(!MenuActivity.myOrders.isEmpty()){
            flag=true;
            textSummary.setText("Your Order is: \n");
            for (int i=0; i< MenuActivity.myOrders.size(); i++){
                mealsSummary.setText(mealsSummary.getText().toString()+"Meal "+String.valueOf(i+1)+ ":\n");
                mealsPrices.setText(mealsPrices.getText().toString()+ "\n");
                mealsSummary.setText(mealsSummary.getText().toString()+MenuActivity.myOrders.get(i).getNameMeal()+"\n\n");
                mealsPrices.setText(mealsPrices.getText().toString()+"$"+MenuActivity.myOrders.get(i).getPriceMeal()+"\n\n");
                sum = sum + Double.valueOf(MenuActivity.myOrders.get(i).getPriceMeal());
                //Toast.makeText(CartActivity.this, MenuActivity.myOrders.get(i).toString(), Toast.LENGTH_LONG).show();
            }
            totalPrice.setText("$"+String.valueOf(sum));
        }

//        submitButton.setOnClickListener(new View.OnClickListener(){
//
//
//            @Override
//            public void onClick(View view) {
//                if(flag){
//                    final ProgressDialog myDialog = new ProgressDialog(CartActivity.this);
//                    myDialog.setMessage("Your Order has bee submitted");
//                    myDialog.show();
//                }
//            }
//        });


    }
}
