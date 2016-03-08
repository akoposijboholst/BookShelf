package com.example.jonesdanica.midtermexamv2.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jonesdanica.midtermexamv2.R;
import com.example.jonesdanica.midtermexamv2.interfaces.OnDataPass;

/**
 * Created by danica12 on 3/5/2016.
 */
public class SearchFragment extends DialogFragment{
    private OnDataPass dataPasser;
    private Spinner mSpinner;
    private Button mCancel;
    private Button mSearch;
    private EditText mEditText;
    private String query;
    private String toQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mCancel = (Button) view.findViewById(R.id.btnCancel);
        mSearch = (Button) view.findViewById(R.id.btnSearch);
        mEditText = (EditText) view.findViewById(R.id.eTSearch);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = mSpinner.getSelectedItem().toString();
                toQuery = mEditText.getText().toString();
                passData(new String[]{query, toQuery});
                dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mSpinner = (Spinner) view.findViewById(R.id.spinnerSearch);
        String[] searchBy = {"Genre", "Author"};
        ArrayAdapter<String> search = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, searchBy);
        search.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(search);
        return view;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;
    }

    public void passData (String... data) {
        dataPasser.onDataPass(data);
    }
}
