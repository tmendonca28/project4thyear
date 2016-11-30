package com.tmend.firebaseauth;

/**
 * Created by tmend on 11/9/2016.
 */

public class UserInformation {

    public String gender;
    public String age;
    public String weight;
    public String activitylevel;
    public String height_feet;
    public String height_inches;

    public UserInformation(){

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActivitylevel() {
        return activitylevel;
    }

    public void setActivitylevel(String activitylevel) {
        this.activitylevel = activitylevel;
    }
    public String getHeight_feet() {
        return height_feet;
    }

    public void setHeight_feet(String height_feet) {
        this.height_feet = height_feet;
    }

    public String getHeight_inches() {
        return height_inches;
    }

    public void setHeight_inches(String height_inches) {
        this.height_inches = height_inches;
    }

    public UserInformation(String gender, String age, String weight, String activitylevel, String height_feet, String height_inches) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.activitylevel = activitylevel;
        this.height_feet = height_feet;
        this.height_inches = height_inches;
    }


}
