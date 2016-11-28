package com.tmend.firebaseauth;

/**
 * Created by tmend on 11/9/2016.
 */

public class UserInformation {

    public String gender;
    public String age;
    public String weight;
    public String activitylevel;

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

    public UserInformation(String gender, String age, String weight, String activitylevel) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.activitylevel = activitylevel;
    }
}
