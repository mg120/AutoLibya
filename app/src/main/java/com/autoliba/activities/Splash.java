package com.autoliba.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.model.UserSignUpModel;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class Splash extends AppCompatActivity {

    ImageView imageView;
    NetworkTester networkTester = new NetworkTester(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_img);
        imageView.startAnimation(outToRightAnimation());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!networkTester.isNetworkAvailable())
                    Toasty.error(Splash.this, getString(R.string.error_connection), 1500).show();

                SharedPreferences prefs = getSharedPreferences(LogIn.MY_PREFS_NAME, MODE_PRIVATE);
                String name = prefs.getString("userName", "");
                String user_data_obj = prefs.getString("user_data", "");

                // Retrive Gson Object from Shared Prefernces ....
                Gson gson = new Gson();
                UserSignUpModel.Message user_data = gson.fromJson(user_data_obj, UserSignUpModel.Message.class);

                if (name != null && !name.trim().isEmpty()) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    intent.putExtra("user_data", user_data);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, LogIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3700);
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.2f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -0.0f);
        outtoRight.setDuration(2500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }
}
