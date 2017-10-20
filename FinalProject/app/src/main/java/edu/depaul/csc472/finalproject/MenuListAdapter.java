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

import edu.depaul.csc472.finalproject.Model.Meal;

/**
 * Created by mohammadalharbi on 10/15/17.
 */

public class MenuListAdapter extends ArrayAdapter<Meal> {


    public List<Meal> meals;

    public MenuListAdapter (Context context, int resource, List<Meal> objects){
        super(context,resource,objects);
        meals = MenuActivity.myMeals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.menuview,parent,false);
        }

        Meal meal = meals.get(position);

        TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
        TextView mealPrice = (TextView) convertView.findViewById(R.id.mealPrice);
        ImageView mealImage = convertView.findViewById(R.id.mealImage);

        mealName.setText(meal.getNameMeal());
        mealPrice.setText(meal.getPriceMeal());
        Picasso.with(getContext()).load(meal.getImageMeal()).into(mealImage);

        return convertView;

    }
}
