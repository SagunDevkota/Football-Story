package com.sd2.footballstory.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sd2.footballstory.Fragments.ListMatch;
import com.sd2.footballstory.R;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Animation animationImage,animationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        animationImage = AnimationUtils.loadAnimation(this,R.anim.image_animation);
        animationText = AnimationUtils.loadAnimation(this,R.anim.text_animation);

        imageView.setAnimation(animationImage);
        textView.setAnimation(animationText);

        new CountDownTimer(5000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreen.this, ListMatch.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}