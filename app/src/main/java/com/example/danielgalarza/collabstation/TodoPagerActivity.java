package com.example.danielgalarza.collabstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * add the fragments returned to activity and help ViewPager identify the fragments’ views so they can be placed correctly
 *
 * Created by dustin on 11/25/15.
 */
public class TodoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.danielgalarza.collabstation.crime_id";

    private ViewPager mViewPager;
    private List<Todo> mTodos;

    public static Intent newIntent(Context packageContext, UUID todoID) {
        Intent intent = new Intent(packageContext, TodoPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, todoID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_pager);

        UUID todoID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_todo_pager_view_pager);

        mTodos = TodoLab.get(this).getTodos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Todo todo = mTodos.get(position);
                return TodoFragment.newInstance(todo.getId());
            }

            @Override
            public int getCount() {
                return mTodos.size();
            }

        });

        for (int i = 0; i < mTodos.size(); i++) {
            if (mTodos.get(i).getId().equals(todoID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    } // end onCreate


} // end TodoPagerActivity
