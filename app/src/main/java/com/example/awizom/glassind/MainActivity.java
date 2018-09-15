package com.example.awizom.glassind;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private  static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=findViewById(R.id.imageView);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),TrackOrderActivity.class);
                startActivity(intent);
                finish();finish();
            }
        },SPLASH_TIME_OUT);
       /* Thread thread=new Thread()
        {

            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent=new Intent(getApplicationContext(),TrackOrderActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
          */
    }
    //Siddharth
}
