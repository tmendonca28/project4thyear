package com.tmend.firebaseauth;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class HealthCustomizationActivity extends AppCompatActivity implements View.OnClickListener {


    private MaterialBetterSpinner spinner_gender, spinner_activity_level, spinner_height_feet, spinner_height_inches;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextAge, editTextWeight;
    private Button buttonSave,buttonProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_customization);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        spinner_gender = (MaterialBetterSpinner) findViewById(R.id.spinner_gender);
        spinner_activity_level = (MaterialBetterSpinner) findViewById(R.id.spinner_activitylevel);
        spinner_height_feet = (MaterialBetterSpinner) findViewById(R.id.spinner_height_feet);
        spinner_height_inches = (MaterialBetterSpinner) findViewById(R.id.spinner_height_inches);
//        editTextGender = (EditText) findViewById(R.id.etgender);
        editTextAge = (EditText) findViewById(R.id.etage);
        editTextWeight = (EditText) findViewById(R.id.etweight);
//        editTextActivityLevel = (EditText) findViewById(R.id.etactivitylevel);
        buttonSave = (Button) findViewById(R.id.btnSave);
        buttonProceed = (Button) findViewById(R.id.btnProceed);
        String[] GENDER = getResources().getStringArray(R.array.gender_types);
        String[] ACTIVITY_TYPES = getResources().getStringArray(R.array.activity_level_types);
        String[] HEIGHT_FEET = getResources().getStringArray(R.array.height_feet);
        String[] HEIGHT_INCHES = getResources().getStringArray(R.array.height_inches);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,GENDER);
        ArrayAdapter<String> activity_levelAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ACTIVITY_TYPES);
        ArrayAdapter<String> height_feetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,HEIGHT_FEET);
        ArrayAdapter<String> height_inchesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, HEIGHT_INCHES);

        spinner_gender.setAdapter(genderAdapter);
        spinner_activity_level.setAdapter(activity_levelAdapter);
        spinner_height_feet.setAdapter(height_feetAdapter);
        spinner_height_inches.setAdapter(height_inchesAdapter);
        editTextAge.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextWeight.setInputType(InputType.TYPE_CLASS_NUMBER);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //adding listener to buttons
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonProceed.setOnClickListener(this);



    }

    private void saveUserInformation(){
        String gender = spinner_gender.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String activitylevel = spinner_activity_level.getText().toString().trim();
        String height_feet = spinner_height_feet.getText().toString().trim();
        String height_inches = spinner_height_inches.getText().toString().trim();

        UserInformation userInformation = new UserInformation(gender,age,weight,activitylevel,height_feet, height_inches);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("users").child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Information Saved...", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (view == buttonSave) {
            saveUserInformation();
            //open main screen
            finish();
            startActivity(new Intent(this, MainScreen.class));
        }
        if (view == buttonProceed) {
            //open main screen
            finish();
            startActivity(new Intent(this, MainScreen.class));
        }
    }
}
