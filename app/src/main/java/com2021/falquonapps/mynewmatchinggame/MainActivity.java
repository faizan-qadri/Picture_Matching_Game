package com2021.falquonapps.mynewmatchinggame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private LinearLayout share, about, privecy ,rate;
    Dialog epicDialog;
    private ImageView playBtn, moreoptn, volume;
    private BackgroundMusicPlayer backgroundMusicPlayer;

    private static final int APP_UPDATE_REQUEST_CODE = 101;
    private AppUpdateManager appUpdateManager;

    private AdView mAdView;
    private int bannerAdClickCount = 0;

    private ImageView animLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("PictureMatchGamePreferences", Context.MODE_PRIVATE);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                bannerAdClickCount++;
                // Check if the banner ad click count is
                if (bannerAdClickCount >= 5) {
                    // Disable the banner ad clicks
                    mAdView.setClickable(false);
                }
            }
        });






        // Create an instance of AppUpdateManager
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Check for app update
        checkForAppUpdate();


        animLogo = findViewById(R.id.animLogo);
        setAnimLogo();


        for (int i = 1; i <= 21; i++){
            boolean sharedPrefExists = sharedPreferences.contains("Level"+i);
            if (sharedPrefExists){
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Level"+i, 0);
                editor.apply();
            }
        }

        for (int i = 1; i <= 21; i++){
            boolean sharedPrefExists = sharedPreferences.contains("UnlockLevel"+i);
            if (sharedPrefExists){
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("UnlockLevel"+i, 0);
                editor.apply();
            }
        }

        boolean sharedPrefExists = sharedPreferences.contains("UnlockLevel1");
        if (sharedPrefExists){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("UnlockLevel1",1);
            editor.apply();
        }

        volume = findViewById(R.id.volumeBtn);
        backgroundMusicPlayer = new BackgroundMusicPlayer(this);
        updateMusicButtonImage();
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMusic();
            }
        });


        epicDialog = new Dialog(this);


        moreoptn = findViewById(R.id.moreOption);
        playBtn = findViewById(R.id.playBtn);



        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(playBtn, View.SCALE_X, 1f, 1.2f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(playBtn, View.SCALE_Y, 1f, 1.2f);
        scaleXAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds
        scaleYAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds

        // Create the zoom-out animation
        ObjectAnimator scaleXAnimatorReverse = ObjectAnimator.ofFloat(playBtn, View.SCALE_X, 1.2f, 1f);
        ObjectAnimator scaleYAnimatorReverse = ObjectAnimator.ofFloat(playBtn, View.SCALE_Y, 1.2f, 1f);
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

        moreOption();
        volumeOptrion();



        moreoptn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MoreOptionPopUp();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Category_Activity.class);
                    startActivities(new Intent[]{intent});


                }
        });
    }


    private void setAnimLogo() {
        AnimationDrawable animationDrawable = (AnimationDrawable) animLogo.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }


    private void toggleMusic() {
        boolean isMusicEnabled = backgroundMusicPlayer.isMusicEnabled();
        backgroundMusicPlayer.setMusicEnabled(!isMusicEnabled);
        updateMusicButtonImage();
    }

    private void updateMusicButtonImage() {
        boolean isMusicEnabled = backgroundMusicPlayer.isMusicEnabled();
        int imageResource = isMusicEnabled ? R.drawable.volumeopen : R.drawable.volumeclose;
        volume.setImageResource(imageResource);
    }

    @Override
    protected void onResume() {
        backgroundMusicPlayer.resume();
        super.onResume();

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    completeUpdate();
                }
            }
        });
    }

    private void checkForAppUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Check if there is an update available
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // Request the update
                    showUpdateDialog(appUpdateInfo);
                }
            }
        });
    }

    private void showUpdateDialog(AppUpdateInfo appUpdateInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Available");
        builder.setMessage("A new version of the app is available. Do you want to update now?");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Request the update
                requestUpdate(appUpdateInfo);
            }
        });
        builder.setNegativeButton("Later", null);
        builder.setCancelable(false);
        builder.show();
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            // Request the update
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, APP_UPDATE_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void completeUpdate() {
        // Finish the update process
        appUpdateManager.completeUpdate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, ""+requestCode, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("Update Failed")
                        .setMessage("The update request failed or was canceled. Do you want to try again?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Request the update again
                                checkForAppUpdate();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();      }
        }
    }

    @Override
    protected void onPause() {
        backgroundMusicPlayer.pause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        backgroundMusicPlayer.stop();
        super.onDestroy();
    }
    private void volumeOptrion(){
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(volume, View.SCALE_X, 1f, 0.8f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(volume, View.SCALE_Y, 1f, 0.8f);
        scaleXAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds
        scaleYAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds

        // Create the zoom-out animation
        ObjectAnimator scaleXAnimatorReverse = ObjectAnimator.ofFloat(volume, View.SCALE_X, 0.8f, 1f);
        ObjectAnimator scaleYAnimatorReverse = ObjectAnimator.ofFloat(volume, View.SCALE_Y, 0.8f, 1f);
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


    private void moreOption(){
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(moreoptn, View.SCALE_X, 1f, 0.8f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(moreoptn, View.SCALE_Y, 1f, 0.8f);
        scaleXAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds
        scaleYAnimator.setDuration(1000); // Set the duration of the zoom-in animation in milliseconds

        // Create the zoom-out animation
        ObjectAnimator scaleXAnimatorReverse = ObjectAnimator.ofFloat(moreoptn, View.SCALE_X, 0.8f, 1f);
        ObjectAnimator scaleYAnimatorReverse = ObjectAnimator.ofFloat(moreoptn, View.SCALE_Y, 0.8f, 1f);
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

    public void MoreOptionPopUp() {
        epicDialog.setContentView(R.layout.menupopup);

        share = epicDialog.findViewById(R.id.share);
        about = epicDialog.findViewById(R.id.about);
        privecy = epicDialog.findViewById(R.id.privecy);
        rate = epicDialog.findViewById(R.id.rateus);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Brain Game - Picture Match");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "BRAIN GAME - PICTURE MATCH - Hey, I am playing this game. Believe me its really very cool game....go and download now. Click here for download http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    String shareTitle = "Share App";
                    Intent chooserIntent = Intent.createChooser(shareIntent, shareTitle);
                    if (shareIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooserIntent);
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this,About.class);
                startActivities(new Intent[]{categoryIntent});
            }
        });
        privecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this,Privecy_policy.class);
                startActivities(new Intent[]{categoryIntent});
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}