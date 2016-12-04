package com.tmend.firebaseauth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProteinTabFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    //Firebase
    private DatabaseReference databaseReference,dref;
    private OnFragmentInteractionListener mListener;

    //Initializing firebaselistadapter
    private FirebaseListAdapter proteinAlternativeAdapter;



    private final List<Foods> protein_objects = new ArrayList<Foods>();
    // The two lists
    private final ArrayList<Foods> protein_obj_higher_gly = new ArrayList<Foods>();
    private final ArrayList<Foods> protein_obj_lower_gly = new ArrayList<Foods>();

    private ArrayAdapter<Foods> proteinsAdapter;
    //Initializing
    private TextView proteinLocalName, proteinCalories, proteinGlycaemicIndex, proteinBenefits;
    private Spinner proteinSpinner;
    private ListView proteinAlternativesListView;
    RecyclerView precyclerView;


    public ProteinTabFragment() {
        // Required empty public constructor
    }

    public static ProteinTabFragment newInstance(String param1, String param2) {
        ProteinTabFragment fragment = new ProteinTabFragment();
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
        dref = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("foods").orderByChild("food_type").equalTo("protein").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> protein_names = new ArrayList<String>();


                for(DataSnapshot proteinSnapshot: dataSnapshot.getChildren()){
                    Foods proteinSnap = proteinSnapshot.getValue(Foods.class);
                    protein_names.add(proteinSnap.getFood_name());
                    protein_objects.add(proteinSnap);

                }
                Log.e("TAG", "protein_objects size1: " + protein_objects.size());
                proteinSpinner.setOnItemSelectedListener(ProteinTabFragment.this);
                ArrayAdapter<String> proteinNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,protein_names);
                proteinNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                proteinSpinner.setAdapter(proteinNamesAdapter);
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
        View rootView = inflater.inflate(R.layout.fragment_protein_tab, container, false);
        //ArrayAdapter<Foods> proteinsAdapter = new ArrayAdapter<Foods>(getActivity(),R.layout.fragment_protein_tab,protein_objects);
        proteinLocalName = (TextView) rootView.findViewById(R.id.textViewProteinLocalName);
        proteinCalories = (TextView) rootView.findViewById(R.id.textViewProteinCalories);
        proteinGlycaemicIndex = (TextView) rootView.findViewById(R.id.textViewProteinGlycaemicIndex);
        proteinBenefits = (TextView) rootView.findViewById(R.id.textViewProteinBenefits);
        proteinSpinner = (Spinner) rootView.findViewById(R.id.spinner_protein);
        precyclerView = (RecyclerView) rootView.findViewById(R.id.rvProteinAlternatives);




//        proteinAlternativesListView = (ListView) rootView.findViewById(R.id.protein_alternatives);


        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup_proteins);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "protein_objects size3: " + protein_objects.size());
                Log.e("TAG", "Current Glycaemic index:" + proteinGlycaemicIndex.getText());
                // Loop through all the proteins to determine which goes in which list
                final ArrayList<Foods> protein_obj_higher_gly = new ArrayList<Foods>();
                final ArrayList<Foods> protein_obj_lower_gly = new ArrayList<Foods>();
                for (Foods protein: protein_objects ) {
                    // First, let's log the glycaemic index to see if it gets it
                    Log.e("TAG", "Glycaemic index:" + protein.getGlycaemic_index().toString());
                    // Do the if statements here to decide what object goes into which list
                    if(protein.getGlycaemic_index() > Integer.parseInt(proteinGlycaemicIndex.getText().toString())) {
                        protein_obj_higher_gly.add(protein);
                    } else if(protein.getGlycaemic_index() < Integer.parseInt(proteinGlycaemicIndex.getText().toString())){
                        protein_obj_lower_gly.add(protein);
                    }

                }

                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.rb_protein_higher_sugar_content:

                        // Using the default android array adapter
                        ProteinAlternativesFoodAdapter proteinAltHigherAdapter = new ProteinAlternativesFoodAdapter(getActivity(), protein_obj_higher_gly);
                        precyclerView.setAdapter(proteinAltHigherAdapter);

                        Toast.makeText(getActivity(),proteinGlycaemicIndex.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rb_protein_lower_sugar_content:
                        Toast.makeText(getActivity(),"Selected Lower", Toast.LENGTH_SHORT).show();
                        ProteinAlternativesFoodAdapter proteinAltLowerAdapter = new ProteinAlternativesFoodAdapter(getActivity(), protein_obj_lower_gly);
                        precyclerView.setAdapter(proteinAltLowerAdapter);
                        // what happens when you choose lower
                        break;
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        precyclerView.setLayoutManager(mLayoutManager);
        precyclerView.setItemAnimator(new DefaultItemAnimator());
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

        proteinLocalName.setText("" + protein_objects.get(i).getLocal_name());
        proteinCalories.setText("" + protein_objects.get(i).getCalories());
        proteinGlycaemicIndex.setText("" + protein_objects.get(i).getGlycaemic_index());
        proteinBenefits.setText("" + protein_objects.get(i).getBenefits());
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
