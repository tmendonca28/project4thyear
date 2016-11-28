package com.tmend.firebaseauth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewFruit extends AppCompatActivity {

    EditText edit_food_name, edit_local_name, edit_calories, edit_sugar_content;

    Button addFruit;

    //Create the database reference object
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fruit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intitialize reference to db pointing to child(table) named foods
        databaseReference = FirebaseDatabase.getInstance().getReference().child("fruits");

        //Intitialize the EditTexts
        edit_food_name = (EditText) findViewById(R.id.edit_fruit_name);
        edit_local_name = (EditText) findViewById(R.id.edit_fruit_local_name);
        edit_calories = (EditText) findViewById(R.id.edit_fruit_calories);
        edit_sugar_content = (EditText) findViewById(R.id.edit_fruit_sugar_content);

        //Initialize add fruits button
        addFruit = (Button) findViewById(R.id.addFruitButton);

        addFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFruit();//Call function that adds new foods to firebase on clicking the add food button


                //Clear fields of
                edit_food_name.setText("");
                edit_local_name.setText("");
                edit_calories.setText("");
                edit_sugar_content.setText("");
            }
        });
    }
//Function that adds the foods to the realtime firebase database
    public void newFruit(){
        //Assign the values of the EditTexts to Strings
        String food_name = edit_food_name.getText().toString();
        String local_name = edit_local_name.getText().toString();
        String calories = edit_calories.getText().toString();
        String sugar_content = edit_sugar_content.getText().toString();

        //Add the food to the firebase database with a unique identifier
        //This is done by use of the model
        //The arguments passed in the Fruits constructor should be the same
        databaseReference.push().setValue(new Fruits(food_name,local_name,calories,sugar_content));



    }

}
