package edu.depaul.csc472.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

import edu.depaul.csc472.finalproject.Model.Order;
import edu.depaul.csc472.finalproject.Model.Truck;

/**
 * Created by Fahad on 10/12/17.
 */

public class OrderHistoryListAdapter extends ArrayAdapter<Order> {

    public List<Order> orders;

    public OrderHistoryListAdapter (Context context, int resource, List<Order> objects){
        super(context, resource,objects);
        orders = OrderHistoryActivity.myOrders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.orderlistview,parent,false);
        }

        Order order = orders.get(position);

        TextView orderNumber = (TextView) convertView.findViewById(R.id.orderNumber);
        TextView truckName = (TextView) convertView.findViewById(R.id.truckName);

        orderNumber.setText(order.getOrderNumber());
        truckName.setText(order.getTruckName());

        return convertView;

    }

}
