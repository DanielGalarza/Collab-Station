package com.example.danielgalarza.collabstation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by danielgalarza on 11/23/15.
 */
public class Splash extends Activity {
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final ImageView iv = (ImageView)findViewById(R.id.imageView);
        final Animation animCamera = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation fadeOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);


        iv.startAnimation(animCamera);

        animCamera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                iv.startAnimation(fadeOut);
                finish();
                Intent i  = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
