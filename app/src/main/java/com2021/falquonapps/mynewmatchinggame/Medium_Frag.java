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


public class Medium_Frag extends Fragment implements View.OnClickListener {

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


    public Medium_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medium_, container, false);

        mainbg = view.findViewById(R.id.mainBgEasy);
        timerText = view.findViewById(R.id.timer);
        progressBar = view.findViewById(R.id.questionProgresstimer);

        Bundle bundle = getArguments();
        data = bundle.getString("data");
        UnlockLevel1 = bundle.getString("UnlockLevel2");


        if (data.compareTo("alien") == 0) {
            mainbg.setImageResource(R.drawable.monsterbg);
            level = "Level2";

        } else if (data.compareTo("angle") == 0) {
            mainbg.setImageResource(R.drawable.fairytailesbg);
            level = "Level5";

        } else if (data.compareTo("cartoon") == 0) {
            mainbg.setImageResource(R.drawable.icebg);
            level = "Level8";

        } else if (data.compareTo("animal") == 0) {
            mainbg.setImageResource(R.drawable.fishbg);
            level = "Level11";

        } else if (data.compareTo("flag") == 0) {
            mainbg.setImageResource(R.drawable.fishbg);
            level = "Level14";

        } else if (data.compareTo("fruit") == 0) {
            mainbg.setImageResource(R.drawable.foodbg);
            level = "Level17";

        } else if (data.compareTo("game") == 0) {
            mainbg.setImageResource(R.drawable.gamesbg);
            level = "Level20";

        }

        imageViews = new ImageView[12];
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


        // Set click listeners
        for (ImageView imageView : imageViews) {
            imageView.setOnClickListener(this);
        }

        timerTextView = view.findViewById(R.id.timerTextView);
        startTimer();


        imageIds = getRandomImageIds();
        setImages();


        progressBar.setProgress(100);
        timerText.setText("20");
        timerText.setTextColor(Color.parseColor("#4CAF50"));
        countDown = new CountDownTimer(21000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String data = String.valueOf(millisUntilFinished / 1000);
                int intData = Integer.parseInt(data);
                progressBar.setProgress(intData * 5);
                if (millisUntilFinished > 14000) {
                    timerText.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                } else if (millisUntilFinished < 14000 && millisUntilFinished > 7000) {
                    timerText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
                } else if (millisUntilFinished < 7000) {
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
                intent.putExtra("info", "Medium");
                startActivity(intent);
                getActivity().finish();
            }
        };


        return view;
    }

    private int[] getRandomImageIds() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
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
                if (pairComplete == 6) {
                    countDown.cancel();
                    Intent intent = new Intent(getContext(), ScoreActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("time", timerText.getText().toString());
                    intent.putExtra("pairComplete", String.valueOf(pairComplete));
                    intent.putExtra("resScore", String.valueOf(result));
                    intent.putExtra("level", level);
                    intent.putExtra("UnlockLevel", UnlockLevel1);
                    intent.putExtra("info", "Medium");
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
                    return R.drawable.alien_5;
                case 2:
                    return R.drawable.alien_6;
                case 3:
                    return R.drawable.alien_7;
                case 4:
                    return R.drawable.alien_8;
                case 5:
                    return R.drawable.alien_9;
                case 6:
                    return R.drawable.alien_10;
                default:
                    return R.drawable.aleanmain;
            }
        } else if (alienL.compareTo("angle") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.angel5;
                case 2:
                    return R.drawable.angel6;
                case 3:
                    return R.drawable.angle7;
                case 4:
                    return R.drawable.angel8;
                case 5:
                    return R.drawable.angel9;
                case 6:
                    return R.drawable.angel10;
                default:
                    return R.drawable.angelmain;
            }
        } else if (alienL.compareTo("cartoon") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.cartoon5;
                case 2:
                    return R.drawable.cartoon6;
                case 3:
                    return R.drawable.cartoon7;
                case 4:
                    return R.drawable.cartoon8;
                case 5:
                    return R.drawable.cartoon9;
                case 6:
                    return R.drawable.cartoon10;
                default:
                    return R.drawable.cartoonmain;
            }
        } else if (alienL.compareTo("animal") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.animal5;
                case 2:
                    return R.drawable.animal6;
                case 3:
                    return R.drawable.animal7;
                case 4:
                    return R.drawable.animal8;
                case 5:
                    return R.drawable.animal9;
                case 6:
                    return R.drawable.animal10;
                default:
                    return R.drawable.animalmain;
            }
        } else if (alienL.compareTo("flag") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.flag5;
                case 2:
                    return R.drawable.flag6;
                case 3:
                    return R.drawable.flag7;
                case 4:
                    return R.drawable.flag8;
                case 5:
                    return R.drawable.flag9;
                case 6:
                    return R.drawable.flag10;
                default:
                    return R.drawable.flagmain;
            }
        } else if (alienL.compareTo("fruit") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.fruit5;
                case 2:
                    return R.drawable.fruit6;
                case 3:
                    return R.drawable.fruit7;
                case 4:
                    return R.drawable.fruit8;
                case 5:
                    return R.drawable.fruit9;
                case 6:
                    return R.drawable.fruit10;
                default:
                    return R.drawable.fruitmain;
            }
        } else if (alienL.compareTo("game") == 0) {
            switch (imageId) {
                case 1:
                    return R.drawable.game5;
                case 2:
                    return R.drawable.game6;
                case 3:
                    return R.drawable.game7;
                case 4:
                    return R.drawable.game8;
                case 5:
                    return R.drawable.game9;
                case 6:
                    return R.drawable.game10;
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
        timer = new CountDownTimer(9000, 1000) {
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
