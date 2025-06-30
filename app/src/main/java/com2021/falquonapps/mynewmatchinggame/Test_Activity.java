package com2021.falquonapps.mynewmatchinggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test_Activity extends AppCompatActivity implements View.OnClickListener {


    private ImageView[] imageViews;
    private int[] imageIds;
    private int firstClickedImageId = -1;
    private int secondClickedImageId = -1;
    private boolean isProcessing = false;
    private CountDownTimer timer;
    private TextView timerTextView;
    private String data;
    private ImageView mainbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");

        mainbg = findViewById(R.id.mainBgEasy);

        if (data.compareTo("alien")==0){
            mainbg.setImageResource(R.drawable.monsterbg);

        }else if (data.compareTo("angle")==0){
            mainbg.setImageResource(R.drawable.fairytailesbg);

        }else if (data.compareTo("cartoon")==0){
            mainbg.setImageResource(R.drawable.icebg);

        }else if (data.compareTo("animal")==0){
            mainbg.setImageResource(R.drawable.fishbg);

        }else if (data.compareTo("flag")==0){
            mainbg.setImageResource(R.drawable.fishbg);

        }else if (data.compareTo("fruit")==0){
            mainbg.setImageResource(R.drawable.foodbg);

        }else if (data.compareTo("game")==0) {
            mainbg.setImageResource(R.drawable.gamesbg);

        }

        imageViews = new ImageView[8];
        imageViews[0] = findViewById(R.id.imageView1);
        imageViews[1] = findViewById(R.id.imageView2);
        imageViews[2] = findViewById(R.id.imageView3);
        imageViews[3] = findViewById(R.id.imageView4);
        imageViews[4] = findViewById(R.id.imageView5);
        imageViews[5] = findViewById(R.id.imageView6);
        imageViews[6] = findViewById(R.id.imageView7);
        imageViews[7] = findViewById(R.id.imageView8);


        // Set click listeners
        for (ImageView imageView : imageViews) {
            imageView.setOnClickListener(this);
        }

        timerTextView = findViewById(R.id.timerTextView);
        startTimer();


        imageIds = getRandomImageIds();
        setImages();

    }

    private int[] getRandomImageIds() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ids.add(i);
            ids.add(i);
        }
        Collections.shuffle(ids);

        int[] result = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            result[i] = ids.get(i);
        }
        return result;
    }

    private void setImages() {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setImageResource(getImageResource(imageIds[i],data));
        }
    }

    @Override
    public void onClick(View view) {
        if (isProcessing) {
            return;
        }

        int clickedImageId = getImageId(view);

        // Ignore if already matched or clicked on the same image twice
        if (imageViews[clickedImageId].getDrawable() == null || clickedImageId == firstClickedImageId) {
            return;
        }

        // Show the image or perform any other action
        animateImageRotation(imageViews[clickedImageId], 0f, 180f);

        if (firstClickedImageId == -1) {
            // First image clicked
            firstClickedImageId = clickedImageId;
        } else {
            // Second image clicked
            secondClickedImageId = clickedImageId;

            // Check if the two clicked images are a match
            if (imageIds[firstClickedImageId] == imageIds[secondClickedImageId]) {
                // Matched
                // Perform actions when images are matched
                imageViews[firstClickedImageId].setOnClickListener(null);
                imageViews[secondClickedImageId].setOnClickListener(null);
                resetClickedImages();
            } else {
                // Not matched
                // Perform actions when images are not matched
                isProcessing = true;
                turnBackImagesWithDelay(1000, data);
            }
        }
    }
    private int getImageId(View view) {
        // Return the index of the clicked image view
        for (int i = 0; i < imageViews.length; i++) {
            if (imageViews[i] == view) {
                return i;
            }
        }
        return -1;
    }

    private void animateImageRotation(final ImageView imageView, float startAngle, float endAngle) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, startAngle, endAngle);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageResource(getImageResource(imageIds[getImageId(imageView)],data));
            }
        });
        animator.start();
    }

    private int getImageResource(int imageId, String alienL) {
        // Return the resource ID based on the image ID
        if (alienL.compareTo("alien")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.alien_1;
                case 2:
                    return R.drawable.alien_2;
                case 3:
                    return R.drawable.alien_3;
                case 4:
                    return R.drawable.alien_4;
                default:
                    return R.drawable.aleanmain;
            }
        }else if (alienL.compareTo("angle")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.angel1;
                case 2:
                    return R.drawable.angel2;
                case 3:
                    return R.drawable.angel3;
                case 4:
                    return R.drawable.angel4;
                default:
                    return R.drawable.angelmain;
            }
        }else if (alienL.compareTo("cartoon")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.cartoon1;
                case 2:
                    return R.drawable.cartoon2;
                case 3:
                    return R.drawable.cartoon3;
                case 4:
                    return R.drawable.cartoon4;
                default:
                    return R.drawable.cartoonmain;
            }
        }else if (alienL.compareTo("animal")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.animal1;
                case 2:
                    return R.drawable.animal2;
                case 3:
                    return R.drawable.animal3;
                case 4:
                    return R.drawable.animal4;
                default:
                    return R.drawable.animalmain;
            }
        }else if (alienL.compareTo("flag")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.flag1;
                case 2:
                    return R.drawable.flag2;
                case 3:
                    return R.drawable.flag3;
                case 4:
                    return R.drawable.flag4;
                default:
                    return R.drawable.flagmain;
            }
        }else if (alienL.compareTo("fruit")==0){
            switch (imageId) {
                case 1:
                    return R.drawable.fruit1;
                case 2:
                    return R.drawable.fruit2;
                case 3:
                    return R.drawable.fruit3;
                case 4:
                    return R.drawable.fruit4;
                default:
                    return R.drawable.fruitmain;
            }
        }else if (alienL.compareTo("game")==0) {
            switch (imageId) {
                case 1:
                    return R.drawable.game1;
                case 2:
                    return R.drawable.game2;
                case 3:
                    return R.drawable.game3;
                case 4:
                    return R.drawable.game4;
                default:
                    return R.drawable.gamemain;
            }
        }

        return 0;
    }

    private void resetClickedImages() {
        firstClickedImageId = -1;
        secondClickedImageId = -1;
        isProcessing = false;
    }

    private void turnBackImagesWithDelay(int delayMillis, String data) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Turn back the images after a delay
                if (firstClickedImageId != -1 && secondClickedImageId != -1) {
                    if (data.compareTo("alien")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.aleanmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.aleanmain);
                    }else if (data.compareTo("angle")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.angelmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.angelmain);
                    }else if (data.compareTo("cartoon")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.cartoonmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.cartoonmain);
                    }else if (data.compareTo("animal")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.animalmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.animalmain);
                    }else if (data.compareTo("flag")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.flagmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.flagmain);
                    }else if (data.compareTo("fruit")==0){
                        imageViews[firstClickedImageId].setImageResource(R.drawable.fruitmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.fruitmain);
                    }else if (data.compareTo("game")==0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.gamemain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.gamemain);
                    }
                    resetClickedImages();
                }
            }
        }, delayMillis);
    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update timer display every second
                if (millisUntilFinished<=2000){
                    timerTextView.setText("GO");
                }else {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {
                // Timer expired
                timerTextView.setVisibility(View.INVISIBLE);
                // Turn back the images
                turnBackAllImages();
            }
        }.start();
    }

    private void turnBackAllImages() {
        for (ImageView imageView : imageViews) {
            if (data.compareTo("alien")==0){
                imageView.setImageResource(R.drawable.aleanmain);

            }else if (data.compareTo("angle")==0){
                imageView.setImageResource(R.drawable.angelmain);

            }else if (data.compareTo("cartoon")==0){
                imageView.setImageResource(R.drawable.cartoonmain);

            }else if (data.compareTo("animal")==0){
                imageView.setImageResource(R.drawable.animalmain);

            }else if (data.compareTo("flag")==0){
                imageView.setImageResource(R.drawable.flagmain);

            }else if (data.compareTo("fruit")==0){
                imageView.setImageResource(R.drawable.fruitmain);

            }else if (data.compareTo("game")==0) {
                imageView.setImageResource(R.drawable.gamemain);

            }
        }
        resetClickedImages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

}