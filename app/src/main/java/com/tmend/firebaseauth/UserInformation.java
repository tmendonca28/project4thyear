package com.tmend.firebaseauth;

/**
 * Created by tmend on 11/9/2016.
 */

public class UserInformation {

    public String gender;
    public String age;
    public String weight;
    public String allergicfood;
    public String activitylevel;

    public UserInformation(){

    }

    public UserInformation(String gender, String age, String weight, String allergicfood, String activitylevel) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.allergicfood = allergicfood;
        this.activitylevel = activitylevel;
    }
}
