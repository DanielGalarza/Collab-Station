package com.example.danielgalarza.collabstation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import java.util.Date;
import java.util.UUID;

/**
 * Fragment for detail view of todoActivity
 *
 * Created by dustin on 11/25/15.
 */
public class TodoFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Todo mTodo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mTaskCompleteCheckBox;

    // call newInstance when creating a TodoFragment
    public static TodoFragment newInstance(UUID todoID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoID);

        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve intent's extra from todoActivity to display detail view of task item clicked
        //UUID todoID = (UUID) getActivity().getIntent().getSerializableExtra(TodoActivity.EXTRA_TODO_ID);
        // pass as Bundle instead of single extra (above)
        UUID todoID = (UUID) getArguments().getSerializable(ARG_TODO_ID);
        mTodo = TodoLab.get(getActivity()).getTodo(todoID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        mTitleField = (EditText) rootView.findViewById(R.id.todo_title);
        mTitleField.setText(mTodo.getTitle());
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
        //mDateButton.setText(mTodo.getDate().toString());
        updateDate();
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTodo.getDate());
                dialog.setTargetFragment(TodoFragment.this, REQUEST_DATE); // passing data
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //wire up checkbox for completed task items
        mTaskCompleteCheckBox = (CheckBox) rootView.findViewById(R.id.todo_complete);
        mTaskCompleteCheckBox.setChecked(mTodo.isTodoComplete());
        mTaskCompleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the task's complete property
                mTodo.setTodoComplete(isChecked);
            }
        });

        //setContentView(R.layout.fragment_todo);
        return rootView;

    } // end onCreateView

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTodo.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        //mDateButton.setText(mTodo.getDate().toString());
        mDateButton.setText(DateFormat.format("EEEE, MMM dd, yyyy", mTodo.getDate()).toString());
    }

} // end TodoFragment
