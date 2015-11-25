package com.example.danielgalarza.collabstation;

import android.support.v4.app.Fragment;

/**
 * Controller class
 *
 * Created by dustin on 11/25/15.
 */
public class TodoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TodoListFragment();
    }
}
