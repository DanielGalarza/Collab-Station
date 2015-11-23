package com.example.danielgalarza.collabstation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by danielgalarza on 11/22/15.
 */
public class TodoFragment extends android.support.v4.app.Fragment {

    public TodoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        //setContentView(R.layout.fragment_todo);
        return rootView;
    }
}
