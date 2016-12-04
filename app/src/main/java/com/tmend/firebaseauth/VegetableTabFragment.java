package com.tmend.firebaseauth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VegetableTabFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    //Firebase
    private DatabaseReference databaseReference;
    private OnFragmentInteractionListener mListener;

    private final List<Foods> vegetable_object = new ArrayList<Foods>();

    //The two filter lists
    private final ArrayList<Foods> vegetable_obj_higher_gly = new ArrayList<Foods>();
    private final ArrayList<Foods> vegetable_obj_lower_gly = new ArrayList<Foods>();

    //Initializing
    private TextView vegetableLocalName, vegetableCalories, vegetableGlycaemicIndex, vegetableBenefits;
    private Spinner vegetableSpinner;
    RecyclerView vrecyclerview;

    public VegetableTabFragment() {
        // Required empty public constructor
    }

    public static VegetableTabFragment newInstance(String param1, String param2) {
        VegetableTabFragment fragment = new VegetableTabFragment();
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
        databaseReference.child("foods").orderByChild("food_type").equalTo("vegetable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> vegetable_name = new ArrayList<String>();


                for(DataSnapshot vegetableSnapshot: dataSnapshot.getChildren()){
                    Foods vegetableSnap = vegetableSnapshot.getValue(Foods.class);
                    vegetable_name.add(vegetableSnap.getFood_name());
                    vegetable_object.add(vegetableSnap);
                }
                vegetableSpinner.setOnItemSelectedListener(VegetableTabFragment.this);
                ArrayAdapter<String> vegetableNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,vegetable_name);
                vegetableNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vegetableSpinner.setAdapter(vegetableNamesAdapter);
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
        View rootView = inflater.inflate(R.layout.fragment_vegetable_tab, container, false);
        vegetableLocalName = (TextView) rootView.findViewById(R.id.textViewVegetableLocalName);
        vegetableCalories = (TextView) rootView.findViewById(R.id.textViewVegetableCalories);
        vegetableGlycaemicIndex = (TextView) rootView.findViewById(R.id.textViewVegetableGlycaemicIndex);
        vegetableBenefits = (TextView) rootView.findViewById(R.id.textViewVegetableBenefits);
        vrecyclerview = (RecyclerView) rootView.findViewById(R.id.rvVegetableAlternatives);
        vegetableSpinner = (Spinner) rootView.findViewById(R.id.spinner_vegetable);

        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup_vegetable);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final ArrayList<Foods> vegetable_obj_higher_gly = new ArrayList<Foods>();
                final ArrayList<Foods> vegetable_obj_lower_gly = new ArrayList<Foods>();
                for (Foods vegetable: vegetable_object){
                    if(vegetable.getGlycaemic_index() > Integer.parseInt(vegetableGlycaemicIndex.getText().toString())){
                        vegetable_obj_higher_gly.add(vegetable);
                    }else if(vegetable.getGlycaemic_index() < Integer.parseInt(vegetableGlycaemicIndex.getText().toString())){
                        vegetable_obj_lower_gly.add(vegetable);
                    }
                }

                //checkedID is the RadioButton that is selected
                switch (checkedId){
                    case R.id.rb_vegetable_higher_sugar_content:

                        ProteinAlternativesFoodAdapter vegetableAltHigherAdapter = new ProteinAlternativesFoodAdapter(getActivity(), vegetable_obj_higher_gly);
                        vrecyclerview.setAdapter(vegetableAltHigherAdapter);
                        break;

                    case R.id.rb_vegetable_lower_sugar_content:

                        ProteinAlternativesFoodAdapter vegetableAltLowerAdapter = new ProteinAlternativesFoodAdapter(getActivity(), vegetable_obj_lower_gly);
                        vrecyclerview.setAdapter(vegetableAltLowerAdapter);
                        break;
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        vrecyclerview.setLayoutManager(mLayoutManager);
        vrecyclerview.setItemAnimator(new DefaultItemAnimator());
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

        vegetableLocalName.setText("" + vegetable_object.get(i).getLocal_name());
        vegetableCalories.setText("" + vegetable_object.get(i).getCalories());
        vegetableGlycaemicIndex.setText("" + vegetable_object.get(i).getGlycaemic_index());
        vegetableBenefits.setText("" + vegetable_object.get(i).getBenefits());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}