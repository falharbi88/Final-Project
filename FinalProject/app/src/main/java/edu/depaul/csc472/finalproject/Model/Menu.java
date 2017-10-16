package edu.depaul.csc472.finalproject.Model;

/**
 * Created by mohammadalharbi on 10/15/17.
 */

public class Menu {
    public String imageMeal;
    public String nameMeal;
    public String priceMeal;

    public Menu(String imageMeal, String nameMeal, String priceMeal) {
        this.imageMeal = imageMeal;
        this.nameMeal = nameMeal;
        this.priceMeal = priceMeal;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "imageMeal='" + imageMeal + '\'' +
                ", nameMeal='" + nameMeal + '\'' +
                ", priceMeal='" + priceMeal + '\'' +
                '}';
    }

    public Menu() {
    }

    public String getImageMeal() {
        return imageMeal;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public String getPriceMeal() {
        return priceMeal;
    }
}
