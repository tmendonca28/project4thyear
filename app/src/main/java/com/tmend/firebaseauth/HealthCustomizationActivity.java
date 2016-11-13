package com.tmend.firebaseauth;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class HealthCustomizationActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextGender, editTextAge, editTextWeight, editTextAllergicFood, editTextActivityLevel;
    private Button buttonSave;

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

        editTextGender = (EditText) findViewById(R.id.etgender);
        editTextAge = (EditText) findViewById(R.id.etage);
        editTextWeight = (EditText) findViewById(R.id.etweight);
        editTextAllergicFood = (EditText) findViewById(R.id.etallergy);
        editTextActivityLevel = (EditText) findViewById(R.id.etactivitylevel);
        buttonSave = (Button) findViewById(R.id.btnSave);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //adding listener to buttons
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);



    }

    private void saveUserInformation(){
        String gender = editTextGender.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String allergicfood = editTextAllergicFood.getText().toString().trim();
        String activitylevel = editTextActivityLevel.getText().toString().trim();

        UserInformation userInformation = new UserInformation(gender,age,weight,allergicfood,activitylevel);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

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
        }
    }
}
