package com.tmend.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class FoodCalorieCalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Firebase
    private DatabaseReference databaseReference,dref,mref,myref;
    private final List<Foods> protein_obj = new ArrayList<>();
    private final List<Foods> vegetable_obj = new ArrayList<>();
    private final List<Foods> grain_obj = new ArrayList<>();
    private final List<Foods> fruit_obj = new ArrayList<>();
    private Spinner spinnerProteinName,spinnerVegetableName,spinnerGrainsName,spinnerFruitsName;
    private TextView loadedProteinCalories,loadedVegetableCalories,loadedGrainsCalories,loadedFruitCalories,totalProteinCalories,totalVegetableCalories,totalGrainsCalories,totalFruitCalories;
    private EditText proteinWeight,vegetableWeight,grainsWeight,fruitWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_calorie_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing items within view



        loadedProteinCalories = (TextView) findViewById(R.id.textview_protein_calories);
        loadedVegetableCalories = (TextView) findViewById(R.id.textview_vegetable_calories);
        loadedGrainsCalories = (TextView) findViewById(R.id.textview_grain_calories);
        loadedFruitCalories = (TextView) findViewById(R.id.textview_fruit_calories);

        proteinWeight = (EditText) findViewById(R.id.edittext_protein_weight);
        vegetableWeight = (EditText) findViewById(R.id.edittext_vegetable_weight);
        grainsWeight = (EditText) findViewById(R.id.edittext_grain_weight);
        fruitWeight = (EditText) findViewById(R.id.edittext_fruit_weight);
        proteinWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        vegetableWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        grainsWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        fruitWeight.setInputType(InputType.TYPE_CLASS_NUMBER);

        totalProteinCalories = (TextView) findViewById(R.id.textview_protein_total_calories);
        totalVegetableCalories = (TextView) findViewById(R.id.textview_vegetable_total_calories);
        totalGrainsCalories = (TextView) findViewById(R.id.textview_grain_total_calories);
        totalFruitCalories = (TextView) findViewById(R.id.textview_fruit_total_calories);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference();
        mref = FirebaseDatabase.getInstance().getReference();
        myref = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("foods").orderByChild("food_type").equalTo("protein").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> protein = new ArrayList<String>();

                for(DataSnapshot proteinSnapshot: dataSnapshot.getChildren()){
                    Foods proteinSnap = proteinSnapshot.getValue(Foods.class);
                    String proteinName = proteinSnapshot.child("food_name").getValue(String.class);
                    protein.add(proteinName);
                    protein_obj.add(proteinSnap);
                }
                spinnerProteinName = (Spinner) findViewById(R.id.spinner_calorie_calculator_protein_name);
                ArrayAdapter<String> proteinNamesAdapter = new ArrayAdapter<String>(FoodCalorieCalculator.this,android.R.layout.simple_dropdown_item_1line,protein);
                spinnerProteinName.setAdapter(proteinNamesAdapter);
                spinnerProteinName.setOnItemSelectedListener(FoodCalorieCalculator.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dref.child("foods").orderByChild("food_type").equalTo("vegetable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> vegetable = new ArrayList<String>();

                for(DataSnapshot vegetableSnapshot: dataSnapshot.getChildren()){
                    Foods vegetableSnap = vegetableSnapshot.getValue(Foods.class);
                    String vegetableName = vegetableSnapshot.child("food_name").getValue(String.class);
                    vegetable.add(vegetableName);
                    vegetable_obj.add(vegetableSnap);
                }

                spinnerVegetableName = (Spinner) findViewById(R.id.spinner_calorie_calculator_vegetable_name);
                ArrayAdapter<String> vegetableNamesAdapter = new ArrayAdapter<String>(FoodCalorieCalculator.this,android.R.layout.simple_dropdown_item_1line,vegetable);
                spinnerVegetableName.setAdapter(vegetableNamesAdapter);
                spinnerVegetableName.setOnItemSelectedListener(FoodCalorieCalculator.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mref.child("foods").orderByChild("food_type").equalTo("grain,nut or starch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> grain = new ArrayList<String>();

                for(DataSnapshot grainSnapshot: dataSnapshot.getChildren()){
                    Foods grainSnap = grainSnapshot.getValue(Foods.class);
                    String grainName = grainSnapshot.child("food_name").getValue(String.class);
                    grain.add(grainName);
                    grain_obj.add(grainSnap);

                }
                spinnerGrainsName = (Spinner) findViewById(R.id.spinner_calorie_calculator_grains_name);
                ArrayAdapter<String> grainNamesAdapter = new ArrayAdapter<String>(FoodCalorieCalculator.this,android.R.layout.simple_dropdown_item_1line,grain);
                spinnerGrainsName.setAdapter(grainNamesAdapter);
                spinnerGrainsName.setOnItemSelectedListener(FoodCalorieCalculator.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myref.child("foods").orderByChild("food_type").equalTo("fruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> fruit = new ArrayList<String>();

                for(DataSnapshot fruitSnapshot: dataSnapshot.getChildren()){
                    Foods fruitSnap = fruitSnapshot.getValue(Foods.class);
                    String fruitName = fruitSnapshot.child("food_name").getValue(String.class);
                    fruit.add(fruitName);
                    fruit_obj.add(fruitSnap);
                }
                spinnerFruitsName = (Spinner) findViewById(R.id.spinner_calorie_calculator_fruit_name);
                ArrayAdapter<String> fruitNamesAdapter = new ArrayAdapter<String>(FoodCalorieCalculator.this,android.R.layout.simple_dropdown_item_1line,fruit);
                spinnerFruitsName.setAdapter(fruitNamesAdapter);
                spinnerFruitsName.setOnItemSelectedListener(FoodCalorieCalculator.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        loadedFruitCalories.setText("" + fruit_obj.get(i).getCalories()+ " Calories in a single fruit");
        loadedVegetableCalories.setText("" + vegetable_obj.get(i).getCalories() + " Calories in a single vegetable");
        loadedGrainsCalories.setText("" + grain_obj.get(i).getCalories() + " Calories in 100 Grams");
        loadedProteinCalories.setText("" + protein_obj.get(i).getCalories() + " Calories in 100 Grams");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
