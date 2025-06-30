package com2021.falquonapps.mynewmatchinggame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Hard_Frag extends Fragment implements View.OnClickListener {

    private ImageView[] imageViews;
    private int[] imageIds;
    private int firstClickedImageId = -1;
    private int secondClickedImageId = -1;
    private boolean isProcessing = false;
    private CountDownTimer timer;
    private TextView timerTextView;
    private String data, UnlockLevel1;
    private ImageView mainbg;
    private CountDownTimer countDown;
    private TextView timerText;
    private ProgressBar progressBar;
    private String level;


    private int result = 0;
    private int pairComplete = 0;

    public Hard_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hard_, container, false);


        mainbg = view.findViewById(R.id.mainBgEasy);
        timerText = view.findViewById(R.id.timer);
        progressBar = view.findViewById(R.id.questionProgresstimer);

        Bundle bundle = getArguments();
        data = bundle.getString("data");
        UnlockLevel1 = bundle.getString("UnlockLevel3");


        if (data.compareTo("alien") == 0) {
            mainbg.setImageResource(R.drawable.monsterbg);
            level = "Level3";

        } else if (data.compareTo("angle") == 0) {
            mainbg.setImageResource(R.drawable.fairytailesbg);
            level = "Level6";

        } else if (data.compareTo("cartoon") == 0) {
            mainbg.setImageResource(R.drawable.icebg);
            level = "Level9";

        } else if (data.compareTo("animal") == 0) {
            mainbg.setImageResource(R.drawable.fishbg);
            level = "Level12";

        } else if (data.compareTo("flag") == 0) {
            mainbg.setImageResource(R.drawable.fishbg);
            level = "Level15";

        } else if (data.compareTo("fruit") == 0) {
            mainbg.setImageResource(R.drawable.foodbg);
            level = "Level18";

        } else if (data.compareTo("game") == 0) {
            mainbg.setImageResource(R.drawable.gamesbg);
            level = "Level21";

        }

        imageViews = new ImageView[16];
        imageViews[0] = view.findViewById(R.id.imageView1);
        imageViews[1] = view.findViewById(R.id.imageView2);
        imageViews[2] = view.findViewById(R.id.imageView3);
        imageViews[3] = view.findViewById(R.id.imageView4);
        imageViews[4] = view.findViewById(R.id.imageView5);
        imageViews[5] = view.findViewById(R.id.imageView6);
        imageViews[6] = view.findViewById(R.id.imageView7);
        imageViews[7] = view.findViewById(R.id.imageView8);
        imageViews[8] = view.findViewById(R.id.imageView9);
        imageViews[9] = view.findViewById(R.id.imageView10);
        imageViews[10] = view.findViewById(R.id.imageView11);
        imageViews[11] = view.findViewById(R.id.imageView12);
        imageViews[12] = view.findViewById(R.id.imageView13);
        imageViews[13] = view.findViewById(R.id.imageView14);
        imageViews[14] = view.findViewById(R.id.imageView15);
        imageViews[15] = view.findViewById(R.id.imageView16);


        // Set click listeners
        for (ImageView imageView : imageViews) {
            imageView.setOnClickListener(this);
        }

        timerTextView = view.findViewById(R.id.timerTextView);
        startTimer();


        imageIds = getRandomImageIds();
        setImages();

        progressBar.setProgress(100);
        timerText.setText("30");
        timerText.setTextColor(Color.parseColor("#4CAF50"));
        countDown = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String data = String.valueOf(millisUntilFinished / 1000);
                int intData = Integer.parseInt(data);
                progressBar.setProgress(intData * 3);
                if (millisUntilFinished > 20000) {
                    timerText.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                } else if (millisUntilFinished < 20000 && millisUntilFinished > 10000) {
                    timerText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                } else if (millisUntilFinished < 10000) {
                    timerText.setTextColor(ColorStateList.valueOf(Color.parseColor("#F44336")));
                }
                timerText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getContext(), ScoreActivity.class);
                intent.putExtra("data", data);
                intent.putExtra("time", timerText.getText().toString());
                intent.putExtra("pairComplete", String.valueOf(pairComplete));
                intent.putExtra("resScore", String.valueOf(result));
                intent.putExtra("level", level);
                intent.putExtra("UnlockLevel", UnlockLevel1);
                intent.putExtra("info", "Hard");
                startActivity(intent);
                getActivity().finish();
            }
        };


        return view;
    }

    private int[] getRandomImageIds() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
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
            imageViews[i].setImageResource(getImageResource(imageIds[i], data));
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
            result++;
            firstClickedImageId = clickedImageId;
        } else {
            // Second image clicked
            result++;
            secondClickedImageId = clickedImageId;

            // Check if the two clicked images are a match
            if (imageIds[firstClickedImageId] == imageIds[secondClickedImageId]) {
                // Matched
                // Perform actions when images are matched
                pairComplete++;
                imageViews[firstClickedImageId].setOnClickListener(null);
                imageViews[secondClickedImageId].setOnClickListener(null);
                if (pairComplete == 8) {
                    countDown.cancel();
                    Intent intent = new Intent(getContext(), ScoreActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("time", timerText.getText().toString());
                    intent.putExtra("pairComplete", String.valueOf(pairComplete));
                    intent.putExtra("resScore", String.valueOf(result));
                    intent.putExtra("level", level);
                    intent.putExtra("UnlockLevel", UnlockLevel1);
                    intent.putExtra("info", "Hard");
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    resetClickedImages();
                }
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
                imageView.setImageResource(getImageResource(imageIds[getImageId(imageView)], data));
            }
        });
        animator.start();
    }

    private int getImageResource(int imageId, String alienL) {
        // Return the resource ID based on the image ID
        if (alienL.compareTo("alien") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.alien_11;
                case 2:
                    return R.drawable.alien_12;
                case 3:
                    return R.drawable.alien_13;
                case 4:
                    return R.drawable.alien_14;
                case 5:
                    return R.drawable.alien_15;
                case 6:
                    return R.drawable.alien_16;
                case 7:
                    return R.drawable.alien_17;
                case 8:
                    return R.drawable.alien_18;
                default:
                    return R.drawable.aleanmain;
            }
        } else if (alienL.compareTo("angle") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.angel11;
                case 2:
                    return R.drawable.angel12;
                case 3:
                    return R.drawable.angel13;
                case 4:
                    return R.drawable.angel14;
                case 5:
                    return R.drawable.angel15;
                case 6:
                    return R.drawable.angel16;
                case 7:
                    return R.drawable.angel17;
                case 8:
                    return R.drawable.angel18;
                default:
                    return R.drawable.angelmain;
            }
        } else if (alienL.compareTo("cartoon") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.emoji1;
                case 2:
                    return R.drawable.emoji2;
                case 3:
                    return R.drawable.emoji3;
                case 4:
                    return R.drawable.emoji4;
                case 5:
                    return R.drawable.emoji5;
                case 6:
                    return R.drawable.emoji6;
                case 7:
                    return R.drawable.emoji7;
                case 8:
                    return R.drawable.emoji8;
                default:
                    return R.drawable.cartoonmain;
            }
        } else if (alienL.compareTo("animal") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.animal11;
                case 2:
                    return R.drawable.animal12;
                case 3:
                    return R.drawable.animal13;
                case 4:
                    return R.drawable.animal14;
                case 5:
                    return R.drawable.animal15;
                case 6:
                    return R.drawable.animal16;
                case 7:
                    return R.drawable.animal17;
                case 8:
                    return R.drawable.animal18;
                default:
                    return R.drawable.animalmain;
            }
        } else if (alienL.compareTo("flag") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.flag11;
                case 2:
                    return R.drawable.flag12;
                case 3:
                    return R.drawable.flag13;
                case 4:
                    return R.drawable.flag14;
                case 5:
                    return R.drawable.flag15;
                case 6:
                    return R.drawable.flag16;
                case 7:
                    return R.drawable.flag17;
                case 8:
                    return R.drawable.flag18;
                default:
                    return R.drawable.flagmain;
            }
        } else if (alienL.compareTo("fruit") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.fruit11;
                case 2:
                    return R.drawable.fruit12;
                case 3:
                    return R.drawable.fruit13;
                case 4:
                    return R.drawable.fruit14;
                case 5:
                    return R.drawable.fruit15;
                case 6:
                    return R.drawable.fruit16;
                case 7:
                    return R.drawable.fruit17;
                case 8:
                    return R.drawable.fruit18;
                default:
                    return R.drawable.fruitmain;
            }
        } else if (alienL.compareTo("game") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.app1;
                case 2:
                    return R.drawable.app2;
                case 3:
                    return R.drawable.app3;
                case 4:
                    return R.drawable.app4;
                case 5:
                    return R.drawable.app5;
                case 6:
                    return R.drawable.app6;
                case 7:
                    return R.drawable.app7;
                case 8:
                    return R.drawable.app8;
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
                    if (data.compareTo("alien") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.aleanmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.aleanmain);
                    } else if (data.compareTo("angle") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.angelmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.angelmain);
                    } else if (data.compareTo("cartoon") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.cartoonmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.cartoonmain);
                    } else if (data.compareTo("animal") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.animalmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.animalmain);
                    } else if (data.compareTo("flag") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.flagmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.flagmain);
                    } else if (data.compareTo("fruit") == 0) {
                        imageViews[firstClickedImageId].setImageResource(R.drawable.fruitmain);
                        imageViews[secondClickedImageId].setImageResource(R.drawable.fruitmain);
                    } else if (data.compareTo("game") == 0) {
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
                if (millisUntilFinished <= 2000) {
                    timerTextView.setText("GO");
                } else {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {
                // Timer expired
                timerTextView.setVisibility(View.INVISIBLE);
                // Turn back the images
                turnBackAllImages();
                countDown.start();

            }
        }.start();
    }

    private void turnBackAllImages() {
        for (ImageView imageView : imageViews) {
            if (data.compareTo("alien") == 0) {
                imageView.setImageResource(R.drawable.aleanmain);

            } else if (data.compareTo("angle") == 0) {
                imageView.setImageResource(R.drawable.angelmain);

            } else if (data.compareTo("cartoon") == 0) {
                imageView.setImageResource(R.drawable.cartoonmain);

            } else if (data.compareTo("animal") == 0) {
                imageView.setImageResource(R.drawable.animalmain);

            } else if (data.compareTo("flag") == 0) {
                imageView.setImageResource(R.drawable.flagmain);

            } else if (data.compareTo("fruit") == 0) {
                imageView.setImageResource(R.drawable.fruitmain);

            } else if (data.compareTo("game") == 0) {
                imageView.setImageResource(R.drawable.gamemain);

            }
        }
        resetClickedImages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (countDown != null) {
            countDown.cancel();
        }
    }

}