package com2021.falquonapps.mynewmatchinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ScoreActivity extends AppCompatActivity {

    private ImageView mainbg, backBtn;
    private CardView cardBg;
    private ImageView star1, star2, star3;
    private TextView timeTxt, pair, score, bestScore,lastBest, newScore;

    private int data = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        String time = intent.getStringExtra("time");
        String pairComplete = intent.getStringExtra("pairComplete");
        String result = intent.getStringExtra("resScore");
        String level = intent.getStringExtra("level");
        String UnlockLevel1Data = intent.getStringExtra("UnlockLevel");
        String info = intent.getStringExtra("info");
        int addVal = Integer.parseInt(UnlockLevel1Data);
        addVal++;



        mainbg = findViewById(R.id.scoreBg);
        backBtn = findViewById(R.id.backBtn);
        cardBg = findViewById(R.id.scoreCard);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        timeTxt = findViewById(R.id.scoreTime);
        pair = findViewById(R.id.scorePairCom);
        score = findViewById(R.id.scoreScore);
        bestScore = findViewById(R.id.scoreBestScore);
        lastBest = findViewById(R.id.scoreLastBestScore);
        newScore = findViewById(R.id.newScoreAchieve);


        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });


        SharedPreferences sharedPreferences = getSharedPreferences("PictureMatchGamePreferences", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt(level, 0); // Get the high score

        int add1, add2, add3;
        add1 = Integer.parseInt(time);
        add2 = Integer.parseInt(pairComplete);
        add3 = Integer.parseInt(result);

        if (info.compareTo("Easy")==0){
            Easy(sharedPreferences,highScore,add1,add2,add3, level, addVal, pairComplete, result);
        }else if (info.compareTo("Medium")==0){
            Medium(sharedPreferences,highScore,add1,add2,add3, level, addVal, pairComplete, result);
        }else if (info.compareTo("Hard")==0){
            Hard(sharedPreferences,highScore,add1,add2,add3, level, addVal, pairComplete, result);
        }


        if (data.compareTo("alien")==0){
            mainbg.setImageResource(R.drawable.monsterbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#913347"));
        }else if (data.compareTo("angle")==0){
            mainbg.setImageResource(R.drawable.fairytailesbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#F6BBC9"));

        }else if (data.compareTo("cartoon")==0){
            mainbg.setImageResource(R.drawable.icebg);
            cardBg.setCardBackgroundColor(Color.parseColor("#9CBFD8"));

        }else if (data.compareTo("animal")==0){
            mainbg.setImageResource(R.drawable.fishbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#74B3D7"));

        }else if (data.compareTo("flag")==0){
            mainbg.setImageResource(R.drawable.fishbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#74B3D7"));

        }else if (data.compareTo("fruit")==0){
            mainbg.setImageResource(R.drawable.foodbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#B5E3E0"));

        }else if (data.compareTo("game")==0) {
            mainbg.setImageResource(R.drawable.gamesbg);
            cardBg.setCardBackgroundColor(Color.parseColor("#222222"));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void Easy(SharedPreferences sharedPreferences, int highScore, int add1, int add2, int add3, String level, int unlockValue, String pairComplete, String result){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (highScore==0){
            editor.putInt(level, add3);
            editor.apply();
        }else {
            if (add3==8&&add2==4){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (highScore>8&&add3<=8&&add2==4){
                newScore.setAlpha(1f);
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3<highScore&&add3>=8&&add2==4){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3==8&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }else if (highScore>8&&add3<=8&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }
        }
        ////////////For Easy
        if (add2>=4&&add3>=8){
            int UnlockLevel1 = sharedPreferences.getInt("UnlockLevel"+unlockValue, 0);
            if (UnlockLevel1==0){
                editor.putInt("UnlockLevel"+unlockValue, 1);
                editor.apply();
            }
        }

        int remain;
        remain = 10-add1;

        timeTxt.setText("Total Time Use : "+remain+" sec");
        pair.setText("Total Pair Complete : "+pairComplete);
        bestScore.setText("Best Score For This is 8");
        score.setText("Your Score : "+result);
        lastBest.setText("Your Last Best Score : "+highScore);

        if (remain<=10&&add2==4&&add3==8){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else if(remain>4&&add2>1&&add3>4){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else {
            star1.setBackgroundResource(R.drawable.gray_star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }
        if (remain<=5&&add2==4&&add3==8){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.star);
        }



    }

    private void Medium(SharedPreferences sharedPreferences, int highScore, int add1, int add2, int add3, String level, int unlockValue, String pairComplete, String result){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (highScore==0){
            editor.putInt(level, add3);
            editor.apply();
        }else {
            if (add3==12&&add2==6){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (highScore>12&&add3<=12&&add2==6){
                newScore.setAlpha(1f);
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3<highScore&&add3>=12&&add2==6){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3==12&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }else if (highScore>12&&add3<=12&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }
        }
        ////////////For Easy
        if (add2>=6&&add3>=12){
            int UnlockLevel1 = sharedPreferences.getInt("UnlockLevel"+unlockValue, 0);
            if (UnlockLevel1==0){
                editor.putInt("UnlockLevel"+unlockValue, 1);
                editor.apply();
            }
        }

        int remain;
        remain = 20-add1;

        timeTxt.setText("Total Time Use : "+remain+" sec");
        pair.setText("Total Pair Complete : "+pairComplete);
        bestScore.setText("Best Score For This is 12");
        score.setText("Your Score : "+result);
        lastBest.setText("Your Last Best Score : "+highScore);

        if (remain<=20&&add2==6&&add3==12){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else if(remain>6&&add2>1&&add3>6){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else {
            star1.setBackgroundResource(R.drawable.gray_star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }
        if (remain<=8&&add2==6&&add3==12){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.star);
        }



    }

    private void Hard(SharedPreferences sharedPreferences, int highScore, int add1, int add2, int add3, String level, int unlockValue, String pairComplete, String result){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (highScore==0){
            editor.putInt(level, add3);
            editor.apply();
        }else {
            if (add3==16&&add2==8){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (highScore>16&&add3<=16&&add2==8){
                newScore.setAlpha(1f);
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3<highScore&&add3>=16&&add2==8){
                editor.putInt(level, add3);
                editor.apply();
                score.setTextColor(Color.parseColor("#8BC34A"));
            }else if (add3==16&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }else if (highScore>16&&add3<=16&&add2==0){
                score.setTextColor(Color.parseColor("#F1736A"));
            }
        }
        ////////////For Easy
        if (add2>=8&&add3>=16){
            int UnlockLevel1 = sharedPreferences.getInt("UnlockLevel"+unlockValue, 0);
            if (UnlockLevel1==0){
                editor.putInt("UnlockLevel"+unlockValue, 1);
                editor.apply();
            }
        }

        int remain;
        remain = 30-add1;

        timeTxt.setText("Total Time Use : "+remain+" sec");
        pair.setText("Total Pair Complete : "+pairComplete);
        bestScore.setText("Best Score For This is 16");
        score.setText("Your Score : "+result);
        lastBest.setText("Your Last Best Score : "+highScore);

        if (remain<=30&&add2==8&&add3==16){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else if(remain>8&&add2>1&&add3>8){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }else {
            star1.setBackgroundResource(R.drawable.gray_star);
            star2.setBackgroundResource(R.drawable.gray_star);
            star3.setBackgroundResource(R.drawable.gray_star);
        }
        if (remain<=10&&add2==8&&add3==16){
            star1.setBackgroundResource(R.drawable.star);
            star2.setBackgroundResource(R.drawable.star);
            star3.setBackgroundResource(R.drawable.star);
        }



    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}