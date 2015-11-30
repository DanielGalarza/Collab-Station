package com.example.danielgalarza.collabstation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by danielgalarza on 11/29/15.
 */
public class WelcomeFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        /********* ANIMATION ****************/
        final ImageView iv = (ImageView)rootView.findViewById(R.id.imageView2);
        final Animation animCamera = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        final Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);


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

        return  rootView;
    }
}
