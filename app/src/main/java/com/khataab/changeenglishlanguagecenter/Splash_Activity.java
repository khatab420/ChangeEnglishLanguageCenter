package com.khataab.changeenglishlanguagecenter;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Activity extends AppCompatActivity {

    Animation sideUP,bottomUp;
    ImageView img;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img=findViewById(R.id.img_splash);
        tv=findViewById(R.id.txt_powered);

        sideUP= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomUp=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        img.setAnimation(sideUP);
        tv.setAnimation(bottomUp);


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        startActivity(new Intent(Splash_Activity.this,MainActivity.class));
                        finish();
                    }
                }


        ).start();
    }
}