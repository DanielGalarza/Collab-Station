package com.example.danielgalarza.collabstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
//import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controller class
 *
 *      RecyclerView recycles TextViews and puts them onscreen
 *
 *      LayoutManager handles the exact positioning of items on screen
 *
 * Created by dustin on 11/25/15.
 */

public class TodoListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mAdapter;
    private int mLastAdapterClickPosition = -1;
    private boolean mSubtitleVisible;

    private Firebase mFirebase;
    String dummy = "";                                  ////////////////TEST



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        //Giving the Firebase an Android context.
        Firebase.setAndroidContext(getActivity());
        //Our Firebase Reference.
        mFirebase = new Firebase("https://collaborationstation.firebaseio.com/todo");


        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /** UNNECESSARY OVERRIDE **
    @Override
    public void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
    **/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_todo:

                Todo todo = new Todo(dummy);                ////////////////TEST
                TodoLab.get(getActivity()).addTodo(todo);
                Intent intent = TodoPagerActivity.newIntent(getActivity(), todo.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateSubtitle() {
        TodoLab todoLab = TodoLab.get(getActivity());
        int todoCount = todoLab.getTodos().size();
        String subtitle = getString(R.string.subtitle_format, todoCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {

        TodoLab todoLab = TodoLab.get(getActivity());
        List<Todo> todos = todoLab.getTodos();

            if (mAdapter == null) {
                mAdapter = new TodoAdapter(todos);
                mTodoRecyclerView.setAdapter(mAdapter);

            } else {
                //mAdapter.notifyDataSetChanged();
                if (mLastAdapterClickPosition < 0) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.notifyItemChanged(mLastAdapterClickPosition);
                    mLastAdapterClickPosition = -1;
                }
            }
            updateSubtitle();

    }

    // custom ViewHolder maintains reference to view (TextView)
    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Todo mTodo;
        private TextView mTitleTextView;
        private TextView mDescriptionView;
        private TextView mDateTextView;
        private CheckBox mTaskCompleteCheckBox;


        public TodoHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_todo_title_text_view);
            mDescriptionView = (TextView)itemView.findViewById(R.id.list_item_todo_description_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_todo_date_text_view);
            mTaskCompleteCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_todo_complete_check_box);

            mTaskCompleteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //mTodo.setTodoComplete(isChecked);
                    mFirebase.child(mTodo.getId().toString() + "/todoComplete").setValue(isChecked);
                }
            });

        }

        public void bindTodo(Todo todo) {
            mTodo = todo;

           // mTitleTextView.setText(mTodo.getTitle());


            mFirebase.child(mTodo.getId().toString() + "/title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mTitleTextView.setText(dataSnapshot.getValue().toString());
                    //mTitleField.setSelection(mTitleField.getText().length());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



            //mDescriptionView.setText(mTodo.getDescription());

            mFirebase.child(mTodo.getId().toString() + "/description").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mDescriptionView.setText(dataSnapshot.getValue().toString());
                    //mTitleField.setSelection(mTitleField.getText().length());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mDateTextView.setText(DateFormat.format("EEEE, MMM dd, yyyy", mTodo.getDate()).toString());

                    mFirebase.child(mTodo.getId().toString() + "/date").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //mDateTextView.setText(DateFormat.format("EEEE, MMM dd, yyyy", (Date) dataSnapshot.getValue()));
                            //Log.d("date", (String) DateFormat.format("EEEE, MMM dd, yyyy", (Date) dataSnapshot.getValue()));
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

            //mTaskCompleteCheckBox.setChecked(mTodo.isTodoComplete());
            mFirebase.child(mTodo.getId().toString() + "/todoComplete").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mTaskCompleteCheckBox.setChecked((boolean)dataSnapshot.getValue());

                    //mFirebase.child(mTodo.getId().toString() + "/todoComplete").setValue(isChecked);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



        }

        @Override
        public void onClick(View v) {
            // For testing:
            //Toast.makeText(getActivity(), mTodo.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

            //Start an instance of TodoActivity from within this fragment using one of these three methods
            //Intent intent = new Intent(getActivity(), TodoActivity.class);
            //Intent intent = TodoActivity.newIntent(getActivity(), mTodo.getId());
            Intent intent = TodoPagerActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }

    }

    // custom Adapter
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

        private List<Todo> mTodos;

        public TodoAdapter(List<Todo> todos) {
            mTodos = todos;

        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_todo, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.bindTodo(todo);
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }
    }



} //end TodoListFragment class
