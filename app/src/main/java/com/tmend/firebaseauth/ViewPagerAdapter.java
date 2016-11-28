package com.tmend.firebaseauth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by tmend on 11/22/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("MainActivity.LOG_TAG", "Position: " + position);
        switch (position) {
            case 0:
                return new ProteinTabFragment();
            case 1:
                return new VegetableTabFragment();
            case 2:
                return new GrainTabFragment();
            case 3:
                return new FruitTabFragment();

        }
        return new ProteinTabFragment();
    }

    @Override
    public int getCount() {
        Log.d("MainActivity.LOG_TAG", "Getting count!");
        return 4;           // As there are only 4 Tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Proteins";
            case 1:
                return "Vegetable";
            case 2:
                return "Grains, Nuts and Starch";
            case 3:
                return "Fruits";
        }
        return super.getPageTitle(position);
    }

}
