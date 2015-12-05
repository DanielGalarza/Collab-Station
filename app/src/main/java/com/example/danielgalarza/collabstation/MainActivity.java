package com.example.danielgalarza.collabstation;

import android.content.Intent;
import android.content.res.Configuration;
import android.preference.PreferenceActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        // Set our drawer view.
        setupDrawerContent(nvDrawer);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(mDrawerToggle);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

        /********* ANIMATION ****************/
        final ImageView iv = (ImageView)findViewById(R.id.imageView2);
        final Animation animCamera = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation fadeOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);

        //Starting animation
        iv.startAnimation(animCamera);

        animCamera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv.startAnimation(fadeOut);
                iv.startAnimation(animCamera);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(animCamera);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    /** DRAWER TOGGLE **/
    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d("menu item listener", "Im listening for a menu item");
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    /** HANDLE FOR FRAGMENTS IN VIEW **/
    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = null;

        Class fragmentClass;

        switch(menuItem.getItemId()) {
            case R.id.nav_chat_fragment:
                fragmentClass = ChatFragment.class;
                break;
            case R.id.nav_todo_fragment:
                fragmentClass = TodoFragment.class;
                break;
            case R.id.nav_home_fragment:
                fragmentClass = WelcomeFragment.class;
                break;
            default:
                fragmentClass = ChatFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

        mDrawer.closeDrawers();
    }


    /** MENU ITEMS **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //////// MIGHT NOT NEED THIS ///////
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}