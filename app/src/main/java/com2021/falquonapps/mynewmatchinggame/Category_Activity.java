package com2021.falquonapps.mynewmatchinggame;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class Category_Activity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_);

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,getString(R.string.Interstitial_Ad_ID), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                onBackPressed();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });



        try {
            SharedPreferences sharedPreferences = getSharedPreferences("PictureMatchGamePreferences", Context.MODE_PRIVATE);
            allMethods();
            allMethods2(sharedPreferences);
        }catch (Exception e){
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show();
        }


    }
    private void AlienMethod(View customLayout1, int background, int title, int btnBg,String data,String UnlockLevel1, String UnlockLevel2, String UnlockLevel3){
        ImageView backgroundImage = customLayout1.findViewById(R.id.backgroundImage);
        ImageView titleImage = customLayout1.findViewById(R.id.titleImg);
        ImageView titleShadow = customLayout1.findViewById(R.id.titleImgShadow);
        backgroundImage.setImageResource(background);
        titleImage.setImageResource(title);
        titleShadow.setImageResource(title);
        int tintColor = getResources().getColor(R.color.colorShadow);
        titleShadow.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);

        ////////Easy
        ConstraintLayout easyBtn = customLayout1.findViewById(R.id.easyBtn);
        ImageView easyImage = customLayout1.findViewById(R.id.easyImage);
        animation(easyBtn);
        easyImage.setImageResource(btnBg);

        easyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Category_Activity.this, FragmentContainer.class);
            intent.putExtra("container","easy");
            intent.putExtra("data",data);
            intent.putExtra("UnlockLevel1",UnlockLevel1);
            intent.putExtra("UnlockLevel2","none");
            intent.putExtra("UnlockLevel3","none");
            startActivity(intent);

        });

        //////////Medium
        ConstraintLayout MediumBtn = customLayout1.findViewById(R.id.mediumBtn);
        ImageView MediumImage = customLayout1.findViewById(R.id.mediumImage);
        animation(MediumBtn);
        MediumImage.setImageResource(btnBg);

        MediumBtn.setOnClickListener(v -> {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(Category_Activity.this);
            } else {
                Intent intent = new Intent(Category_Activity.this, FragmentContainer.class);
                intent.putExtra("container","medium");
                intent.putExtra("data",data);
                intent.putExtra("UnlockLevel2",UnlockLevel2);
                intent.putExtra("UnlockLevel1","none");
                intent.putExtra("UnlockLevel3","none");
                startActivity(intent);
            }


        });

        ////////////Hard
        ConstraintLayout hardBtn = customLayout1.findViewById(R.id.hardBtn);
        ImageView hardImage = customLayout1.findViewById(R.id.hardImage);
        animation(hardBtn);
        hardImage.setImageResource(btnBg);
        hardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Category_Activity.this, FragmentContainer.class);
            intent.putExtra("container","hard");
            intent.putExtra("data",data);
            intent.putExtra("UnlockLevel3",UnlockLevel3);
            intent.putExtra("UnlockLevel1","none");
            intent.putExtra("UnlockLevel2","none");
            startActivity(intent);

        });


    }

    private void AlienMethod2(View customLayout1,int level1, int level2, int level3, int catLevel1,int catLevel2,int catLevel3){
        ////////Easy
        TextView easyScore = customLayout1.findViewById(R.id.easyScore);
        if (level1<8){
            easyScore.setTextColor(Color.parseColor("#FAEC74"));
        }else if (level1>8){
            easyScore.setTextColor(Color.parseColor("#FF7469"));
        }else {
            easyScore.setTextColor(Color.parseColor("#8BC34A"));
        }
        easyScore.setText(""+level1);

        //////////Medium
        TextView mediumScore = customLayout1.findViewById(R.id.mediumScore);
        if (level2<12){
            mediumScore.setTextColor(Color.parseColor("#FAEC74"));
        }else if (level2>12){
            mediumScore.setTextColor(Color.parseColor("#FF7469"));
        }else {
            mediumScore.setTextColor(Color.parseColor("#8BC34A"));
        }
        mediumScore.setText(""+level2);

        ////////////Hard
        TextView hardScore = customLayout1.findViewById(R.id.hardScore);
        if (level3<16){
            hardScore.setTextColor(Color.parseColor("#FAEC74"));
        }else if (level3>16){
            hardScore.setTextColor(Color.parseColor("#FF7469"));
        }else {
            hardScore.setTextColor(Color.parseColor("#8BC34A"));
        }
        hardScore.setText(""+level3);


        ConstraintLayout easyBtn = customLayout1.findViewById(R.id.easyBtn);
        easyBtn.setEnabled(false);
        TextView easyText = customLayout1.findViewById(R.id.easyText);
        ImageView easyLock = customLayout1.findViewById(R.id.easyLock);



        ConstraintLayout MediumBtn = customLayout1.findViewById(R.id.mediumBtn);
        MediumBtn.setEnabled(false);
        TextView MediumText = customLayout1.findViewById(R.id.mediumText);
        ImageView MediumLock = customLayout1.findViewById(R.id.mediumLock);

        ConstraintLayout hardBtn = customLayout1.findViewById(R.id.hardBtn);
        hardBtn.setEnabled(false);
        TextView hardText = customLayout1.findViewById(R.id.hardText);
        ImageView hardLock = customLayout1.findViewById(R.id.hardLock);


        if (catLevel1==0){
            easyBtn.setEnabled(false);
            easyText.setAlpha(0f);
            easyLock.setAlpha(1f);
        }else {
            easyBtn.setEnabled(true);
            easyText.setAlpha(1f);
            easyLock.setAlpha(0f);

        }
        if (catLevel2==0){
            MediumBtn.setEnabled(false);
            MediumText.setAlpha(0f);
            MediumLock.setAlpha(1f);
        }else {
            MediumBtn.setEnabled(true);
            MediumText.setAlpha(1f);
            MediumLock.setAlpha(0f);
        }
        if (catLevel3==0){
            hardBtn.setEnabled(false);
            hardText.setAlpha(0f);
            hardLock.setAlpha(1f);
        }else {
            hardBtn.setEnabled(true);
            hardText.setAlpha(1f);
            hardLock.setAlpha(0f);
        }

    }

    private void allMethods2(SharedPreferences sharedPreferences){
        /////////////////AlienMonster
        View customLayout1 = findViewById(R.id.customLayout1);
        int cat1lvl1 = sharedPreferences.getInt("Level1", 0);
        int cat1lvl2 = sharedPreferences.getInt("Level2", 0);
        int cat1lvl3 = sharedPreferences.getInt("Level3", 0);
        int cat1level1 = sharedPreferences.getInt("UnlockLevel1", 0);
        int cat1level2 = sharedPreferences.getInt("UnlockLevel2", 0);
        int cat1level3 = sharedPreferences.getInt("UnlockLevel3", 0);
        AlienMethod2(customLayout1,cat1lvl1,cat1lvl2,cat1lvl3,cat1level1,cat1level2,cat1level3);

        /////////////////Angel
        View customLayout2 = findViewById(R.id.customLayout2);
        int cat2lvl4 = sharedPreferences.getInt("Level4", 0);
        int cat2lvl5 = sharedPreferences.getInt("Level5", 0);
        int cat2lvl6 = sharedPreferences.getInt("Level6", 0);
        int cat2level1 = sharedPreferences.getInt("UnlockLevel4", 0);
        int cat2level2 = sharedPreferences.getInt("UnlockLevel5", 0);
        int cat2level3 = sharedPreferences.getInt("UnlockLevel6", 0);
        AlienMethod2(customLayout2,cat2lvl4,cat2lvl5,cat2lvl6,cat2level1,cat2level2,cat2level3);

        ///////////////////////Cartoon
        View customLayout3 = findViewById(R.id.customLayout3);
        int cat3lvl7 = sharedPreferences.getInt("Level7", 0);
        int cat3lvl8 = sharedPreferences.getInt("Level8", 0);
        int cat3lvl9 = sharedPreferences.getInt("Level9", 0);
        int cat3level1 = sharedPreferences.getInt("UnlockLevel7", 0);;
        int cat3level2 = sharedPreferences.getInt("UnlockLevel8", 0);;
        int cat3level3 = sharedPreferences.getInt("UnlockLevel9", 0);;
        AlienMethod2(customLayout3,cat3lvl7,cat3lvl8,cat3lvl9,cat3level1,cat3level2,cat3level3);



        ///////////////////////Animal
        View customLayout4 = findViewById(R.id.customLayout4);
        int cat4lvl10 = sharedPreferences.getInt("Level10", 0);
        int cat4lvl11 = sharedPreferences.getInt("Level11", 0);
        int cat4lvl12 = sharedPreferences.getInt("Level12", 0);
        int cat4level1 = sharedPreferences.getInt("UnlockLevel10", 0);;
        int cat4level2 = sharedPreferences.getInt("UnlockLevel11", 0);;
        int cat4level3 = sharedPreferences.getInt("UnlockLevel12", 0);;
        AlienMethod2(customLayout4,cat4lvl10,cat4lvl11,cat4lvl12,cat4level1,cat4level2,cat4level3);


        /////////////////////////////FLAG
        View customLayout5 = findViewById(R.id.customLayout5);
        int cat5lvl13 = sharedPreferences.getInt("Level13", 0);
        int cat5lvl14 = sharedPreferences.getInt("Level14", 0);
        int cat5lvl15 = sharedPreferences.getInt("Level15", 0);
        int cat5level1 = sharedPreferences.getInt("UnlockLevel13", 0);;
        int cat5level2 = sharedPreferences.getInt("UnlockLevel14", 0);;
        int cat5level3 = sharedPreferences.getInt("UnlockLevel15", 0);;
        AlienMethod2(customLayout5,cat5lvl13,cat5lvl14,cat5lvl15,cat5level1,cat5level2,cat5level3);


        //////////////////////////Fruit
        View customLayout6 = findViewById(R.id.customLayout6);
        int cat6lvl16 = sharedPreferences.getInt("Level16", 0);
        int cat6lvl17 = sharedPreferences.getInt("Level17", 0);
        int cat6lvl18 = sharedPreferences.getInt("Level18", 0);
        int cat6level1 = sharedPreferences.getInt("UnlockLevel16", 0);;
        int cat6level2 = sharedPreferences.getInt("UnlockLevel17", 0);;
        int cat6level3 = sharedPreferences.getInt("UnlockLevel18", 0);;
        AlienMethod2(customLayout6,cat6lvl16,cat6lvl17,cat6lvl18,cat6level1,cat6level2,cat6level3);


        ////////////////////////Games
        View customLayout7 = findViewById(R.id.customLayout7);
        int cat7lvl19 = sharedPreferences.getInt("Level19", 0);
        int cat7lvl20 = sharedPreferences.getInt("Level20", 0);
        int cat7lvl21 = sharedPreferences.getInt("Level21", 0);
        int cat7level1 = sharedPreferences.getInt("UnlockLevel19", 0);;
        int cat7level2 = sharedPreferences.getInt("UnlockLevel20", 0);;
        int cat7level3 = sharedPreferences.getInt("UnlockLevel21", 0);;
        AlienMethod2(customLayout7,cat7lvl19,cat7lvl20,cat7lvl21,cat7level1,cat7level2,cat7level3);


    }


    private void allMethods(){
        /////////////////AlienMonster
        View customLayout1 = findViewById(R.id.customLayout1);
        int cat1background = R.drawable.monsterbg;
        int cat1title = R.drawable.alienmonster;
        int cat1btnBg = R.drawable.btn1;
        String cat1Data = "alien";
        String UnlockLevel1 = "1";
        String UnlockLevel2 = "2";
        String UnlockLevel3 = "3";
        AlienMethod(customLayout1,cat1background,cat1title,cat1btnBg,cat1Data,UnlockLevel1,UnlockLevel2,UnlockLevel3);

        /////////////////Angel
        View customLayout2 = findViewById(R.id.customLayout2);
        int cat2background = R.drawable.fairytailesbg;
        int cat2title = R.drawable.princesfairy;
        int cat2btnBg = R.drawable.btn2;
        String cat2Data = "angle";
        String UnlockLevel4 = "4";
        String UnlockLevel5 = "5";
        String UnlockLevel6 = "6";
        AlienMethod(customLayout2,cat2background,cat2title,cat2btnBg,cat2Data,UnlockLevel4,UnlockLevel5,UnlockLevel6);

        ///////////////////////Cartoon
        View customLayout3 = findViewById(R.id.customLayout3);
        int cat3background = R.drawable.icebg;
        int cat3title = R.drawable.emojicartoon;
        int cat3btnBg = R.drawable.btn3;
        String cat3Data = "cartoon";
        String UnlockLevel7 = "7";
        String UnlockLevel8 = "8";
        String UnlockLevel9 = "9";
        AlienMethod(customLayout3,cat3background,cat3title,cat3btnBg,cat3Data,UnlockLevel7,UnlockLevel8,UnlockLevel9);



        ///////////////////////Animal
        View customLayout4 = findViewById(R.id.customLayout4);
        int cat4background = R.drawable.fishbg;
        int cat4title = R.drawable.seawild;
        int cat4btnBg = R.drawable.btn4;
        String cat4Data = "animal";
        String UnlockLevel10 = "10";
        String UnlockLevel11 = "11";
        String UnlockLevel12 = "12";
        AlienMethod(customLayout4,cat4background,cat4title,cat4btnBg,cat4Data,UnlockLevel10,UnlockLevel11,UnlockLevel12);


        /////////////////////////////FLAG
        View customLayout5 = findViewById(R.id.customLayout5);
        int cat5background = R.drawable.fishbg;
        int cat5title = R.drawable.flagalphabet;
        int cat5btnBg = R.drawable.btn5;
        String cat5Data = "flag";
        String UnlockLevel13 = "13";
        String UnlockLevel14 = "14";
        String UnlockLevel15 = "15";
        AlienMethod(customLayout5,cat5background,cat5title,cat5btnBg,cat5Data,UnlockLevel13,UnlockLevel14,UnlockLevel15);


        //////////////////////////Fruit
        View customLayout6 = findViewById(R.id.customLayout6);
        int cat6background = R.drawable.foodbg;
        int cat6title = R.drawable.fruitesvegi;
        int cat6btnBg = R.drawable.btn6;
        String cat6Data = "fruit";
        String UnlockLevel16 = "16";
        String UnlockLevel17 = "17";
        String UnlockLevel18 = "18";
        AlienMethod(customLayout6,cat6background,cat6title,cat6btnBg,cat6Data,UnlockLevel16,UnlockLevel17,UnlockLevel18);


        ////////////////////////Games
        View customLayout7 = findViewById(R.id.customLayout7);
        int cat7background = R.drawable.gamesbg;
        int cat7title = R.drawable.appsgames;
        int cat7btnBg = R.drawable.btn7;
        String cat7Data = "game";
        String UnlockLevel19 = "19";
        String UnlockLevel20 = "20";
        String UnlockLevel21 = "21";
        AlienMethod(customLayout7,cat7background,cat7title,cat7btnBg,cat7Data,UnlockLevel19,UnlockLevel20,UnlockLevel21);


    }

    private void animation(ConstraintLayout easyBtn){
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(easyBtn, View.SCALE_X, 1f, 1.1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(easyBtn, View.SCALE_Y, 1f, 1.1f);
        scaleXAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds
        scaleYAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds

        // Create the zoom-out animation
        ObjectAnimator scaleXAnimatorReverse = ObjectAnimator.ofFloat(easyBtn, View.SCALE_X, 1.1f, 1f);
        ObjectAnimator scaleYAnimatorReverse = ObjectAnimator.ofFloat(easyBtn, View.SCALE_Y, 1.1f, 1f);
        scaleXAnimatorReverse.setDuration(1000); // Set the duration of the zoom-out animation in milliseconds
        scaleYAnimatorReverse.setDuration(1000); // Set the duration of the zoom-out animation in milliseconds

        // Create an AnimatorSet to combine the zoom-in and zoom-out animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator);
        animatorSet.play(scaleXAnimatorReverse).with(scaleYAnimatorReverse).after(scaleXAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start(); // Restart the animation when it ends
            }
        });

        // Start the animation
        animatorSet.start();
    }

    @Override
    protected void onResume() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("PictureMatchGamePreferences", Context.MODE_PRIVATE);
            allMethods2(sharedPreferences);
        }catch (Exception e){
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}