package com.tmend.firebaseauth;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Fruits {

    private String food_name;
    private String local_name;
    private String calories;
    private String sugar_content;

    public Fruits() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Fruits(String food_name, String local_name, String calories, String sugar_content) {
        this.calories = calories;
        this.food_name = food_name;
        this.local_name = local_name;
        this.sugar_content = sugar_content;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
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

    public String getSugar_content() {
        return sugar_content;
    }

    public void setSugar_content(String sugar_content) {
        this.sugar_content = sugar_content;
    }
}
