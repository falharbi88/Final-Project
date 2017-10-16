package edu.depaul.csc472.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.depaul.csc472.finalproject.Model.Menu;
import edu.depaul.csc472.finalproject.Model.Truck;

/**
 * Created by mohammadalharbi on 10/15/17.
 */

public class MenuListAdapter extends ArrayAdapter<Menu> {


    public List<Menu> meals;

    public MenuListAdapter (Context context, int resource, List<Menu> objects){
        super(context,resource,objects);
        meals = MenuActivity.myMeals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.menuview,parent,false);
        }

        Menu menu = meals.get(position);

        TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
        TextView mealPrice = (TextView) convertView.findViewById(R.id.mealPrice);
        ImageView mealImage = convertView.findViewById(R.id.mealImage);

        mealName.setText(menu.getNameMeal());
        mealPrice.setText(menu.getPriceMeal());
        Picasso.with(getContext()).load(menu.getImageMeal()).into(mealImage);

        return convertView;

    }
}
