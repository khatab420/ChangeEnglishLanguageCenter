package com.khataab.changeenglishlanguagecenter;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


public class OnBoarding_Activity extends AppCompatActivity {

    Button button_next;
    ViewPager2 viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onbording);
        button_next =findViewById(R.id.next);
        viewPager = findViewById(R.id.viewpager);





        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);


        if (isFirstRun) {
            // This is the first run, show the welcome screen
            // Delayed navigation to the main activity
            OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(this);
            viewPager.setAdapter(adapter);


            button_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to the next page
                    int nextItem = viewPager.getCurrentItem() + 1;
                    if (nextItem < adapter.getItemCount()) {
                        viewPager.setCurrentItem(nextItem);
                        button_next.setVisibility(View.VISIBLE);
                    } else {
                        button_next.setVisibility(View.INVISIBLE);
                    }
                }
            });

        } else {
            // Not the first run, directly navigate to the main activity
            Intent mainIntent = new Intent(OnBoarding_Activity.this,Splash_Activity.class);
            startActivity(mainIntent);
            finish(); // Close the welcome screen

        }
    }
}