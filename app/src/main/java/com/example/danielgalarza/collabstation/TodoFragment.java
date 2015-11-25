package com.example.danielgalarza.collabstation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by dustin on 11/25/15.
 */
public class TodoFragment extends Fragment {

    private Todo mTodo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mTaskCompleteCheckBox;

    public TodoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTodo = new Todo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        mTitleField = (EditText) rootView.findViewById(R.id.todo_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Intentionally left blank
            }
        });

        //wire up button to select deadline date
        mDateButton = (Button) rootView.findViewById(R.id.todo_date);
        mDateButton.setText(mTodo.getDate().toString());
        mDateButton.setText(DateFormat.format("EEEE, MMM dd, yyyy", mTodo.getDate()).toString());
        mDateButton.setEnabled(false);

        //wire up checkbox for completed task items
        mTaskCompleteCheckBox = (CheckBox) rootView.findViewById(R.id.todo_complete);
        mTaskCompleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the task's complete property
                mTodo.setTodoComplete(isChecked);
            }
        });

        //setContentView(R.layout.fragment_todo);
        return rootView;
    }


}
