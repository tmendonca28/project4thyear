package com.tmend.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView usergender, userweight, userage, useractivitylevel, userHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userage = (TextView) findViewById(R.id.textViewDisplayAge);
        usergender = (TextView) findViewById(R.id.textViewDisplayGender);
        userweight = (TextView) findViewById(R.id.textViewDisplayWeight);
        useractivitylevel = (TextView) findViewById(R.id.textViewDisplayActivityLevel);
        userHeight = (TextView) findViewById(R.id.textViewDisplayHeight);



        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else
        {
            Log.e("TAG", "User ID: "+firebaseAuth.getCurrentUser().getUid());
        }




        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Food Adviser");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Calorie Calculator");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Edit Details");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Basic Diabetes Information");

        //Intitialize reference to db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TAG", "User Details:"+ dataSnapshot.toString());
                UserInformation userinfo = dataSnapshot.getValue(UserInformation.class);

                usergender.setText(userinfo.getGender());
                userage.setText(userinfo.getAge());
                userweight.setText(userinfo.getWeight());
                useractivitylevel.setText(userinfo.getActivitylevel());
                userHeight.setText(userinfo.getHeight_feet() + " Feet " + userinfo.getHeight_inches() + " Inches");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
// Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(firebaseAuth.getCurrentUser().getDisplayName()).withEmail(firebaseAuth.getCurrentUser().getEmail()).withIcon((R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        item4,
                        item5
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainScreen.this, MainActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(MainScreen.this, FoodsMainHolder.class);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(MainScreen.this, CalorieCalculator.class);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(MainScreen.this, HealthCustomizationActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(MainScreen.this, DiabetesInfo.class);
                            }
                            if(intent != null){
                                MainScreen.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

}

