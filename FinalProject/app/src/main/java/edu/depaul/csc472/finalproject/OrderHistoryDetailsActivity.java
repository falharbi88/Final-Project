package edu.depaul.csc472.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.depaul.csc472.finalproject.Model.Order;

public class OrderHistoryDetailsActivity extends AppCompatActivity {
    public static final int CODEFROMUSERPROFILE=200;
    public String orderNo="";
    public Order detailOrder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        Button orderAgainButton = (Button) findViewById(R.id.orderAgainButton1);
        TextView truckName = (TextView) findViewById(R.id.truckName1);
        TextView orderNumber = (TextView) findViewById(R.id.orderNumber1);
        TextView textSummary = (TextView) findViewById(R.id.TextSummary1);
        TextView mealsSummary = (TextView) findViewById(R.id.MealsSummary1);
        TextView mealsPrices = (TextView) findViewById(R.id.MealsPrices1);
        TextView totalPrice = (TextView) findViewById(R.id.PriceText1);

        Double sum = 0.0;
        orderNo=getIntent().getStringExtra("OrderNumber");
        detailOrder = OrderHistoryActivity.myOrders.get(Integer.valueOf(orderNo));

        textSummary.setText("Your Order is: \n");
        truckName.setText(detailOrder.getTruckName());
        orderNumber.setText(detailOrder.getOrderNumber());
        for (int i=0; i< detailOrder.getMeals().size(); i++){
            mealsSummary.setText(mealsSummary.getText().toString()+"Meal "+String.valueOf(i+1)+ ":\n");
            mealsPrices.setText(mealsPrices.getText().toString()+ "\n");
            mealsSummary.setText(mealsSummary.getText().toString()+detailOrder.getMeals().get(i).getNameMeal()+"\n\n");            mealsPrices.setText(mealsPrices.getText().toString()+"$"+detailOrder.getMeals().get(i).getPriceMeal()+"\n\n");
            sum = sum + Double.valueOf(detailOrder.getMeals().get(i).getPriceMeal());
        }
        totalPrice.setText("$"+String.valueOf(sum));


        orderAgainButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                MenuActivity.myOrders.clear();
                MenuActivity.truckName= detailOrder.getTruckName();
                for (int i = 0; i < detailOrder.getMeals().size(); i++) {
                    MenuActivity.myOrders.add(detailOrder.getMeals().get(i));

                }
                Intent cart = new Intent(OrderHistoryDetailsActivity.this,CartActivity.class);
                //startActivity(cart);
                startActivityForResult(cart,CODEFROMUSERPROFILE);
            }
        });



    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode==CODEFROMUSERPROFILE){
            if(resultCode==RESULT_OK){
                setResult(RESULT_OK);
                finish();

            }
        }


    }
}
