package com.tmend.firebaseauth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FruitSpinner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intitialize reference to db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("fruits");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> fruits = new ArrayList<String>();

                for(DataSnapshot fruitSnapshot: dataSnapshot.getChildren()){
                    String fruitName = fruitSnapshot.child("food_name").getValue(String.class);
                    fruits.add(fruitName);
                }
                Spinner fruitSpinner = (Spinner) findViewById(R.id.spinner_fruit);
// Spinner click listener
                fruitSpinner.setOnItemSelectedListener(FruitSpinner.this);
                ArrayAdapter<String> fruitsAdapter = new ArrayAdapter<String>(FruitSpinner.this,android.R.layout.simple_spinner_item,fruits);
                fruitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fruitSpinner.setAdapter(fruitsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_higher_sugar_content:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rb_lower_sugar_content:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String item = adapterView.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
