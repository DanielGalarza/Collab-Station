package com.example.danielgalarza.collabstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
//import android.widget.Toast;

import java.util.List;

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


    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mAdapter;
    private int mLastAdapterClickPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
    }

    // custom ViewHolder maintains reference to view (TextView)
    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Todo mTodo;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mTaskCompleteCheckBox;

        public TodoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_todo_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_todo_date_text_view);
            mTaskCompleteCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_todo_complete_check_box);

        }

        public void bindTodo(Todo todo) {
            mTodo = todo;
            mTitleTextView.setText(mTodo.getTitle());
            mDateTextView.setText(mTodo.getDate().toString());
            mTaskCompleteCheckBox.setChecked(mTodo.isTodoComplete());
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
