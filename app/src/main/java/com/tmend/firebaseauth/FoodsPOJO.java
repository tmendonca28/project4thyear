package com.tmend.firebaseauth;

/**
 * Created by tmend on 11/27/2016.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FoodsPOJO {
    private String food_name;
    private String local_name;
    private Integer calories;
    private Integer glycaemic_index;
    private String benefits;

    public FoodsPOJO() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public FoodsPOJO(String food_name, String local_name, String food_type, Integer calories, Integer glycaemic_index, String benefits) {
        this.calories = calories;
        this.food_name = food_name;
        this.local_name = local_name;
        this.glycaemic_index = glycaemic_index;
        this.benefits = benefits;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getGlycaemic_index() {
        return glycaemic_index;
    }

    public void setGlycaemic_index(Integer glycaemic_index) {
        this.glycaemic_index = glycaemic_index;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }



}