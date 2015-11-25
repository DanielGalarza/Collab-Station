package com.example.danielgalarza.collabstation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;


/**
 * Implements the reusable abstract class "SingleFragmentActivity" via createFragment()
 *
 * Created by dustin on 11/25/15.
 */

public class TodoActivity extends SingleFragmentActivity {

    private static final String EXTRA_TODO_ID = "com.example.danielgalarza.collabstation.todo_id";

    public static Intent newIntent(Context packageContext, UUID todoID) {
        Intent intent = new Intent(packageContext, TodoActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new TodoFragment();
        UUID todoID = (UUID) getIntent().getSerializableExtra(EXTRA_TODO_ID);
        return TodoFragment.newInstance(todoID);
    }
}
