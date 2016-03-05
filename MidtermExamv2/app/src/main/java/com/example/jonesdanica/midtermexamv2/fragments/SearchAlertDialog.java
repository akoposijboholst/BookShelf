package com.example.jonesdanica.midtermexamv2.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jonesdanica.midtermexamv2.MainActivity;
import com.example.jonesdanica.midtermexamv2.R;

/**
 * Created by danica12 on 3/5/2016.
 */
public class SearchAlertDialog extends DialogFragment {

    private Spinner mSpinner;
    private Button mCancel;
    private Button mSearch;
    private EditText mEditText;
    public static String query;
    public static String toQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container, false);

        mCancel = (Button) view.findViewById(R.id.btnCancel);
        mSearch = (Button) view.findViewById(R.id.btnSearch);
        mEditText = (EditText) view.findViewById(R.id.eTSearch);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = mSpinner.getSelectedItem().toString();
                toQuery = mEditText.getText().toString();
                passData(new String[]{query, toQuery});
               // fetchBookTask.execute(new String[]{query, toQuery});
                Log.d("Passed", query + toQuery);
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
        String[] choices = {"Genre", "Author"};
        ArrayAdapter<String> search = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, choices);
        search.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(search);
        return view;
    }

    public String getItemSelected(){
        return query;
    }

    public String getInputtedItem(){
        return toQuery;
    }

    public interface  OnDataPass {
        void onDataPass(String... data);
    }

    private OnDataPass dataPasser;

    @Override
    public void onAttach (Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    public void passData (String... data) {
        dataPasser.onDataPass(data);
    }
}
