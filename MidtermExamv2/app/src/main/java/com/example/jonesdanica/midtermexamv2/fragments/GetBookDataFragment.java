package com.example.jonesdanica.midtermexamv2.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonesdanica.midtermexamv2.R;
import com.example.jonesdanica.midtermexamv2.constants.Constants;

/**
 * Created by danica12 on 3/3/2016.
 */
public class GetBookDataFragment extends DialogFragment {

    private int mAction;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = getArguments().getInt(Constants.EXTRA_ACTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_getbook, container, false);
        mTextView = (TextView)view.findViewById(R.id.tvGetBookData);

        switch(mAction){
            case Constants.VIEW_BOOKDETAIL:{
                mTextView.setText("Getting book...");
                break;
            }
            case Constants.EDIT_BOOKDETAIL:{
                mTextView.setText("Updating book...");
                break;
            }
            case Constants.DELETE_BOOKDETAIL:{
                mTextView.setText("Deleting book...");
                break;
            }
            case Constants.ADD_BOOKDETAIL:{
                mTextView.setText("Adding books...");
            }
        }
        return view;
    }
}
