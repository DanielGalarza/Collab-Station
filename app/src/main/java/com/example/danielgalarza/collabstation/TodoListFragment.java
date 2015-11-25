package com.example.danielgalarza.collabstation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    // Nothing yet

    private RecyclerView mTodoRecyclerView;
    private TodoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        TodoLab todoLab = TodoLab.get(getActivity());
        List<Todo> todos = todoLab.getTodos();

        mAdapter = new TodoAdapter(todos);
        mTodoRecyclerView.setAdapter(mAdapter);
    }

    // custom ViewHolder maintains reference to view (TextView)
    private class TodoHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;

        public TodoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
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
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TodoHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.mTitleTextView.setText(todo.getTitle());
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }
    }



} //end TodoListFragment class
