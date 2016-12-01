package com.tmend.firebaseauth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FruitTabFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    //Firebase
    private DatabaseReference databaseReference;
    private OnFragmentInteractionListener mListener;

    private final List<Foods> fruit_object = new ArrayList<Foods>();
    private ArrayAdapter<Foods> fruitsAdapter;
    //Initializing
    private TextView fruitLocalName, fruitCalories, fruitGlycaemicIndex, fruitBenefits;
    private Spinner fruitSpinner;

    public FruitTabFragment() {
        // Required empty public constructor
    }

    public static FruitTabFragment newInstance(String param1, String param2) {
        FruitTabFragment fragment = new FruitTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("foods").orderByChild("food_type").equalTo("fruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List <String> fruit_name = new ArrayList<String>();

                for(DataSnapshot fruitSnapshot: dataSnapshot.getChildren()){
                    Foods fruitSnap = fruitSnapshot.getValue(Foods.class);
                    fruit_name.add(fruitSnap.getFood_name());
                    fruit_object.add(fruitSnap);
                }
                fruitSpinner.setOnItemSelectedListener(FruitTabFragment.this);
                ArrayAdapter<String> fruitNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,fruit_name);
                fruitNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fruitSpinner.setAdapter(fruitNamesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fruit_tab, container, false);

        fruitLocalName = (TextView) rootView.findViewById(R.id.textViewFruitLocalName);
        fruitCalories = (TextView) rootView.findViewById(R.id.textViewFruitCalories);
        fruitGlycaemicIndex = (TextView) rootView.findViewById(R.id.textViewFruitGlycaemicIndex);
        fruitBenefits = (TextView) rootView.findViewById(R.id.textViewFruitBenefits);

        fruitSpinner = (Spinner) rootView.findViewById(R.id.spinner_fruit);

        return rootView;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fruitLocalName.setText("" + fruit_object.get(i).getLocal_name());
        fruitCalories.setText("" + fruit_object.get(i).getCalories());
        fruitGlycaemicIndex.setText("" + fruit_object.get(i).getGlycaemic_index());
        fruitBenefits.setText("" + fruit_object.get(i).getBenefits());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
