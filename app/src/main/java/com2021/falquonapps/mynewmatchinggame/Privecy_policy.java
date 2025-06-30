package com2021.falquonapps.mynewmatchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Privecy_policy extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privecy_policy);

        textView = findViewById(R.id.privacyText1);

        String s = "Privacy Policy\n" +
                "\n" +
                "Effective date: 2021-04-04\n" +
                "\n" +
                "Frame App (\"us,\" \"we,\" or \"our\") operates the Brain Game - Picture Match mobile application (hereinafter referred to as the \"App\").\n" +
                "\n" +
                "This page informs you of our policies regarding the collection, use, and disclosure of personal data when you use our App and the choices you have associated with that data.\n" +
                "\n" +
                "Information Collection and Use\n" +
                "We do not collect any personally identifiable information or personal data when you use our App.\n" +
                "\n" +
                "Usage Data\n" +
                "We may collect certain usage data for analytical purposes and to improve the App's functionality. This usage data is collected in an anonymous and aggregated form, and it does not personally identify you.\n" +
                "\n" +
                "Google AdMob Ads\n" +
                "Our App may display advertisements served by Google AdMob. AdMob is provided by Google and may collect and use certain data for personalized advertising purposes. This data collection and usage are governed by Google's privacy policy. You can review Google's privacy policy at the following link: [Insert link to Google's privacy policy].\n" +
                "\n" +
                "Use of Data\n" +
                "We use the collected data for various purposes, including but not limited to:\n" +
                "\n" +
                "To provide and maintain our App\n" +
                "To notify you about changes to our App\n" +
                "To improve the App's functionality and user experience\n" +
                "To gather analysis or valuable information to understand how users interact with the App\n" +
                "To monitor the usage of the App\n" +
                "To detect, prevent, and address technical issues\n" +
                "Disclosure of Data\n" +
                "We do not disclose any personal data as we do not collect any personally identifiable information through the App.\n" +
                "\n" +
                "Security of Data\n" +
                "The security of your data is important to us. We have implemented appropriate measures to protect the App and the usage data collected from unauthorized access or disclosure.\n" +
                "\n" +
                "Links to Other Sites\n" +
                "Our App does not contain links to other sites. It operates as a standalone application.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                "\n" +
                "Contact Us\n" +
                "If you have any questions about this Privacy Policy, please contact us at frame.apps444@gmail.com.";

        textView.setText(s);




    }
}