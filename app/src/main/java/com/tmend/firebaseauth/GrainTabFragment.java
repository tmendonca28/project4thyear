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

public class GrainTabFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    //Firebase
    private DatabaseReference databaseReference;
    private OnFragmentInteractionListener mListener;

    private final List<Foods> grain_object = new ArrayList<Foods>();

    //Initializing
    private TextView grainLocalName, grainCalories, grainGlycaemicIndex, grainBenefits;
    private Spinner grainSpinner;

    public GrainTabFragment() {
        // Required empty public constructor
    }

    public static GrainTabFragment newInstance(String param1, String param2) {
        GrainTabFragment fragment = new GrainTabFragment();
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
        databaseReference.child("foods").orderByChild("food_type").equalTo("grain,nut or starch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> grain_name = new ArrayList<String>();


                for(DataSnapshot grainSnapshot: dataSnapshot.getChildren()){
                    Foods grainSnap = grainSnapshot.getValue(Foods.class);
                    grain_name.add(grainSnap.getFood_name());
                    grain_object.add(grainSnap);
                }
                grainSpinner.setOnItemSelectedListener(GrainTabFragment.this);
                ArrayAdapter<String> grainNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,grain_name);
                grainNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                grainSpinner.setAdapter(grainNamesAdapter);
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
        View rootView = inflater.inflate(R.layout.fragment_grain_tab, container, false);
        grainLocalName = (TextView) rootView.findViewById(R.id.textViewGrainLocalName);
        grainCalories = (TextView) rootView.findViewById(R.id.textViewGrainCalories);
        grainGlycaemicIndex = (TextView) rootView.findViewById(R.id.textViewGrainGlycaemicIndex);
        grainBenefits = (TextView) rootView.findViewById(R.id.textViewGrainBenefits);

        grainSpinner = (Spinner) rootView.findViewById(R.id.spinner_grain);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        grainLocalName.setText("" + grain_object.get(i).getLocal_name());
        grainCalories.setText("" + grain_object.get(i).getCalories());
        grainGlycaemicIndex.setText("" + grain_object.get(i).getGlycaemic_index());
        grainBenefits.setText("" + grain_object.get(i).getBenefits());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
