package com.tmend.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class FoodAdviser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_adviser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Food Adviser");
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName("Calorie Calculator");
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName("Edit Details");

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        item4,
                        new SecondaryDrawerItem().withName("Help")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(FoodAdviser.this, MainScreen.class);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(FoodAdviser.this, FoodAdviser.class);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(FoodAdviser.this, CalorieCalculator.class);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(FoodAdviser.this, HealthCustomizationActivity.class);
                            }
                            if(intent != null){
                                FoodAdviser.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

}

