package com.example.danielgalarza.collabstation;

import android.support.v4.app.Fragment;


/**
 * Implements the reusable abstract class "SingleFragmentActivity" via createFragment()
 *
 * Created by dustin on 11/25/15.
 */

public class TodoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TodoFragment();
    }
}
