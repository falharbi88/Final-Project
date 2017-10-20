package edu.depaul.csc472.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.depaul.csc472.finalproject.Model.Meal;
import edu.depaul.csc472.finalproject.Model.Order;
import edu.depaul.csc472.finalproject.Model.Truck;

public class OrderHistoryActivity extends AppCompatActivity {

    public static List<Order> myOrders = new ArrayList<Order>();
    public ListView lv;
    public String username="";
    public String orderNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        username = getIntent().getStringExtra("Username");
        lv = (ListView) findViewById(R.id.orderlist);
        myOrders.clear();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_order = database.getReference("Orders");
        final ArrayList<Meal> myMeals = new ArrayList<Meal>();
        table_order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order =null;
                for(DataSnapshot data1: dataSnapshot.getChildren()) {
                    if(data1.getKey().equals(username)) {

                        for (DataSnapshot data2 : data1.getChildren()) {
                            for (DataSnapshot data3 : data2.getChildren()) {
                                for (DataSnapshot data4 : data3.getChildren()) {
                                    Meal meal = data4.getValue(Meal.class);
                                    myMeals.add(meal);
                                }
                                order = new Order(data1.getKey(), data2.getKey(), data3.getKey(), myMeals);
                                myOrders.add(order);
                            }
                        }
                    }
                }

                OrderHistoryListAdapter adapter = new OrderHistoryListAdapter(OrderHistoryActivity.this,R.layout.orderlistview,myOrders);
                lv.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderNo = myOrders.get(i).getOrderNumber();
                Intent orderHistoryDetailsActivity = new Intent(OrderHistoryActivity.this,OrderHistoryDetailsActivity.class);
                orderHistoryDetailsActivity.putExtra("OrderNumber",orderNo);
                startActivity(orderHistoryDetailsActivity);
            }
        });


    }
}
