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

import edu.depaul.csc472.finalproject.Model.Truck;

/**
 * Created by Fahad on 10/12/17.
 */

public class TruckListAdapter extends ArrayAdapter<Truck> {

    public List<Truck> trucks;

    public TruckListAdapter (Context context, int resource, List<Truck> objects){
        super(context, resource,objects);
        trucks = HomeActivity.myTrucks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.listview,parent,false);
        }

        Truck truck = trucks.get(position);

        TextView truckName = (TextView) convertView.findViewById(R.id.truckName);
        TextView truckType = (TextView) convertView.findViewById(R.id.truckType);
        ImageView truckImage = convertView.findViewById(R.id.truckImage);

        truckName.setText(truck.getTruckName());
        truckType.setText(truck.getTruckType());
        Picasso.with(getContext()).load(truck.getTruckImage()).into(truckImage);


        return convertView;

    }


}
