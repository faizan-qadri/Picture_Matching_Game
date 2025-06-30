package com2021.falquonapps.mynewmatchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Splash_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        ////// BISMILLAHIRRAHMANIRRAHIM /////

        Thread timer = new Thread() {

      @Override
           public void run() {
             try {
                    sleep(3000);
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   intent.putExtra("data", "alien");
                   startActivity(intent);
                   finish();
                  super.run();
                } catch (InterruptedException e) {
                   e.printStackTrace();
              }
           }
       };
       timer.start();



    }

}