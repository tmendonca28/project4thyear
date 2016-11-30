package com.tmend.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class BMRCalculator extends AppCompatActivity {

    //Firebase auth object
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView gender, weight, age, activitylevel, height, bmrValue, CBADValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrcalculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gender = (TextView) findViewById(R.id.textViewGender);
        age = (TextView) findViewById(R.id.textViewAge);
        weight = (TextView) findViewById(R.id.textViewWeight);
        activitylevel = (TextView) findViewById(R.id.textViewActivityLevelBMR);
        height = (TextView) findViewById(R.id.textViewHeight);
        bmrValue = (TextView) findViewById(R.id.textViewBMRValue);
        CBADValue = (TextView) findViewById(R.id.textViewCBADValue);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else
        {
            Log.e("TAG", "User ID: "+firebaseAuth.getCurrentUser().getUid());
        }

        //Intitialize reference to db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TAG", "User Details:"+ dataSnapshot.toString());
                UserInformation userinfo = dataSnapshot.getValue(UserInformation.class);

                gender.setText(userinfo.getGender());
                age.setText(userinfo.getAge());
                weight.setText(userinfo.getWeight());
                activitylevel.setText(userinfo.getActivitylevel());
                height.setText(userinfo.getHeight_feet() + " Feet " + userinfo.getHeight_inches() + " Inches");

                Log.e("TAG", "Gender: "+gender.getText().toString());


                if(gender.getText().toString().equals("Male")){
                    double feet =  Double.parseDouble(userinfo.getHeight_feet().toString());
                    double inches = Double.parseDouble(userinfo.getHeight_inches());
                    double feetplusinches = 2.54*(inches + (12*feet));
                    double dblweight = Double.parseDouble(weight.getText().toString());
                    double dblAge = Double.parseDouble(age.getText().toString());
                    double calculation = 66.47+(13.75*dblweight)+(5*feetplusinches)-(6.75*dblAge);
                    int roundCalculation = (int) Math.round(calculation);
                    bmrValue.setText(""+roundCalculation+ " Calories");
                    if(activitylevel.getText().toString().equals("Sedentary")){
                        double CBAD = roundCalculation*1.2;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");
                    }
                    if(activitylevel.getText().toString().equals("Moderately Active")){
                        double CBAD = calculation*1.55;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");;
                    }
                    if(activitylevel.getText().toString().equals("Active")){
                        double CBAD = calculation*1.9;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");
                    }

                }else if(gender.getText().toString().equals("Female")){
                    double feet =  Double.parseDouble(userinfo.getHeight_feet().toString());
                    double inches = Double.parseDouble(userinfo.getHeight_inches());
                    double feetplusinches = 2.54*(inches + (12*feet));
                    double dblweight = Double.parseDouble(weight.getText().toString());
                    double dblAge = Double.parseDouble(age.getText().toString());
                    double calculation = 665.0955+(9.5634*dblweight)+(1.8496*feetplusinches)-(4.6756*dblAge)-10;
                    int roundCalculation = (int) Math.round(calculation);
                    bmrValue.setText(""+roundCalculation+ " Calories");
                    Log.e("TAG", "Activity Level: "+activitylevel.getText().toString());
                    if(activitylevel.getText().toString().equals("Sedentary")){
                        double CBAD = roundCalculation*1.2;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");
                    }
                    if(activitylevel.getText().toString().equals("Moderately Active")){
                        double CBAD = calculation*1.55;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");;
                    }
                    if(activitylevel.getText().toString().equals("Active")){
                        double CBAD = calculation*1.9;
                        int roundCBAD = (int) Math.round(CBAD);
                        CBADValue.setText(""+ roundCBAD+ " Calories");
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BMRCalculator.this,CalorieCalculator.class));
            }
        });
    }

}
