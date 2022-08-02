package com.example.majorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    TextView name,name2;
    ImageView flag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        name=findViewById(R.id.text);
        name2=findViewById(R.id.as);
        flag=findViewById(R.id.imgflag);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        name.setAnimation(animation);
        name2.setAnimation(animation);
        flag.setAnimation(animation);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashScreen.this,UserLogin.class));
                finish();
                //handler.postDelayed(this,100);
            }
        }, 3700);

    }
}