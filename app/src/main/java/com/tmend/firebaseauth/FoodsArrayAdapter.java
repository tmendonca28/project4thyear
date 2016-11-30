package com.tmend.firebaseauth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tmend on 11/29/2016.
 */

public class FoodsArrayAdapter extends ArrayAdapter<Foods> {

    private static class ViewHolder {
        private TextView foodNameView;
    }

    public FoodsArrayAdapter(Context context, ArrayList<Foods> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.protein_alternatives_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.foodNameView = (TextView) convertView.findViewById(R.id.protein_alternatives_card_food_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Foods item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.foodNameView.setText(String.format("%s %d", item.getFood_name(), item.getGlycaemic_index()));
        }

        return convertView;
    }
}
