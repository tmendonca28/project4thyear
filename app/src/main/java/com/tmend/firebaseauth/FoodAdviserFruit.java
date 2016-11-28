package com.tmend.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodAdviserFruit extends AppCompatActivity {

    DatabaseReference databaseReference;
    private FirebaseListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_adviser_fruit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intitialize reference to db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("fruits");

        //Initialize food listview
        ListView fruitlist = (ListView) findViewById(R.id.fruitlistview);

        //Initialize listAdapter and set properties such as model class, food layout and reference to the realtime database
        listAdapter = new FirebaseListAdapter<Fruits>(this,Fruits.class,R.layout.fruit_card,databaseReference){

            @Override
            protected void populateView(View v, Fruits model, int position) {
                TextView food_name = (TextView) v.findViewById(R.id.fruitcard_food_name);
                TextView local_name = (TextView) v.findViewById(R.id.fruitcard_local_name);
                TextView calories = (TextView) v.findViewById(R.id.fruitcard_calories);
                TextView sugar_content = (TextView) v.findViewById(R.id.fruitcard_sugar_content);

                food_name.setText(model.getFood_name());
                local_name.setText(model.getLocal_name());
                calories.setText(model.getCalories());
                sugar_content.setText(model.getSugar_content());
            }

            @Override
            protected void onCancelled(DatabaseError error) {
                super.onCancelled(error);

                Log.e("firebase_error",error.toString());
            }
        };

        //Assigning list to the adapter
        fruitlist.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodAdviserFruit.this,NewFruit.class));
            }
        });
    }

}
