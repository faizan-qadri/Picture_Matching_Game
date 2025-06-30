package com2021.falquonapps.mynewmatchinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FragmentContainer extends AppCompatActivity {

    private ImageView  backBtn;

    private AdView mAdView;
    private int bannerAdClickCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);


        backBtn = findViewById(R.id.backBtn);


        backBtn.setOnClickListener(v -> {
            onBackPressed();
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


        // Get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        String data = intent.getStringExtra("container");
        String check = intent.getStringExtra("data");
        String UnlockLevel1 = intent.getStringExtra("UnlockLevel1");
        String UnlockLevel2 = intent.getStringExtra("UnlockLevel2");
        String UnlockLevel3 = intent.getStringExtra("UnlockLevel3");



        // Create an instance of your fragment
        Medium_Frag medium = new Medium_Frag();
        Easy_Frag easy = new Easy_Frag();
        Hard_Frag hard = new Hard_Frag();

        Bundle bundle = new Bundle();
        bundle.putString("data", check);
        bundle.putString("UnlockLevel1", UnlockLevel1);
        bundle.putString("UnlockLevel2", UnlockLevel2);
        bundle.putString("UnlockLevel3", UnlockLevel3);



        // Begin the fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            if (data.compareTo("easy")==0){
                fragmentTransaction.add(R.id.fragmentContainer, easy);
                easy.setArguments(bundle);
            }else if (data.compareTo("medium")==0){
                fragmentTransaction.add(R.id.fragmentContainer, medium);
                medium.setArguments(bundle);
            }else if (data.compareTo("hard")==0){
                fragmentTransaction.add(R.id.fragmentContainer, hard);
                hard.setArguments(bundle);
            }

        // Add the fragment to the container


        // Commit the transaction
        fragmentTransaction.commit();


    }


}