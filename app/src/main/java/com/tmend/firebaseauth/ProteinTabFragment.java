package com.tmend.firebaseauth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.snapshot.DoubleNode;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.common.data.DataBufferObserverSet;
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
    private String gi;

    //Initializing firebaselistadapter
    private FirebaseListAdapter proteinAlternativeAdapter;


    private final List<Foods> protein_object = new ArrayList<Foods>();
    private ArrayAdapter<Foods> proteinsAdapter;
    //Initializing
    private TextView proteinLocalName, proteinCalories, proteinGlycaemicIndex, proteinBenefits;
    private Spinner proteinSpinner;
    private ListView proteinAlternativesListView;


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
                final List<String> protein_name = new ArrayList<String>();


                for(DataSnapshot proteinSnapshot: dataSnapshot.getChildren()){
                    Foods proteinSnap = proteinSnapshot.getValue(Foods.class);
                    protein_name.add(proteinSnap.getFood_name());
                    protein_object.add(proteinSnap);
                }
                proteinSpinner.setOnItemSelectedListener(ProteinTabFragment.this);
                ArrayAdapter<String> proteinNamesAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,protein_name);
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
        //ArrayAdapter<Foods> proteinsAdapter = new ArrayAdapter<Foods>(getActivity(),R.layout.fragment_protein_tab,protein_object);

        proteinLocalName = (TextView) rootView.findViewById(R.id.textViewProteinLocalName);
        proteinCalories = (TextView) rootView.findViewById(R.id.textViewProteinCalories);
        proteinGlycaemicIndex = (TextView) rootView.findViewById(R.id.textViewProteinGlycaemicIndex);
        proteinBenefits = (TextView) rootView.findViewById(R.id.textViewProteinBenefits);
        gi = proteinGlycaemicIndex.getText().toString();
        proteinSpinner = (Spinner) rootView.findViewById(R.id.spinner_protein);

        proteinAlternativesListView = (ListView) rootView.findViewById(R.id.protein_alternatives);



        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup_proteins);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.rb_protein_higher_sugar_content:
                        proteinAlternativeAdapter = new FirebaseListAdapter <FoodsPOJO>(getActivity(),FoodsPOJO.class,R.layout.protein_alternatives_card,dref.child("proteins").orderByChild("glycaemic_index").startAt(gi)) {
                            @Override
                            protected void populateView(View v, FoodsPOJO model, int position) {
                                TextView protein_alternatives_food_name = (TextView) v.findViewById(R.id.protein_alternatives_card_food_name);
                                TextView protein_alternatives_benefits = (TextView) v.findViewById(R.id.protein_alternatives_card_benefits);

                                protein_alternatives_food_name.setText(model.getFood_name());
                                protein_alternatives_benefits.setText(model.getBenefits());
                            }
                        };
                        //Assigning list to the adapter
                        proteinAlternativesListView.setAdapter(proteinAlternativeAdapter);
                        Toast.makeText(getActivity(),proteinGlycaemicIndex.getText().toString(), Toast.LENGTH_SHORT).show();

                        // what happens when you choose higher
                        break;
                    case R.id.rb_protein_lower_sugar_content:
                        Toast.makeText(getActivity(),"Selected Lower", Toast.LENGTH_SHORT).show();

                        // what happens when you choose lower
                        break;
                }
            }
        });
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

        proteinLocalName.setText("" + protein_object.get(i).getLocal_name());
        proteinCalories.setText("" + protein_object.get(i).getCalories());
        proteinGlycaemicIndex.setText("" + protein_object.get(i).getGlycaemic_index());
        proteinBenefits.setText("" + protein_object.get(i).getBenefits());

// proteinBenefits.setText(protein_object.get(i+1).getBenefits());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.rb_protein_higher_sugar_content:
//                if (checked)
//                    Toast.makeText(getActivity(),"Selected Higher", Toast.LENGTH_LONG).show();
//                // Pirates are the best
//                break;
//            case R.id.rb_protein_lower_sugar_content:
//                if (checked)
//                    Toast.makeText(getActivity(),"Selected Lower", Toast.LENGTH_LONG).show();
//                // Ninjas rule
//                break;
//        }
//    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
