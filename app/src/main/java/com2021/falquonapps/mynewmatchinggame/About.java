package com2021.falquonapps.mynewmatchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = findViewById(R.id.aboutText1);

        String title = "Brain Game - Picture Match\n";
        String description = "Challenge your brain with this exciting picture matching game! Test your memory and concentration skills across three different difficulty levels: Easy, Medium, and Hard.\n\nFeatures:\n- Easy Level: Start with the Easy level to get acquainted with the game mechanics. Match pairs of pictures with fewer cards to remember and shorter time limits, making it perfect for beginners.\n- Medium Level: Progress to the Medium level for a more challenging experience. Increase the number of cards to match, test your memory retention, and improve your concentration skills.\n- Hard Level: Ready for a real brain workout? Take on the Hard level, where you'll face a larger grid of cards and a limited amount of time to match all pairs. Sharpen your memory and focus as you tackle this ultimate challenge.\n- Multiple Game Modes: Enjoy various game modes within each difficulty level to keep the gameplay exciting. Choose from Classic, Time Trial, and Endless modes, each offering a unique twist to the picture matching experience.\n- Stunning Graphics: Immerse yourself in a visually appealing game environment with vibrant colors and captivating images. The attractive graphics make the gameplay enjoyable and visually stimulating, regardless of the difficulty level.\n- Power-Ups and Boosters: Discover special power-ups and boosters that can help you in difficult moments. Use them strategically to improve your chances of completing levels and achieving high scores.\n- Offline Play: Enjoy the game anytime, anywhere, even without an internet connection. Play offline and keep challenging your brain wherever you go.";

        textView.setText(title+description);

    }
}