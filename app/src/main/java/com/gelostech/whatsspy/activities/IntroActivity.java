package com.gelostech.whatsspy.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.gelostech.whatsspy.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Link to Whatsapp", null, "Open Whatsapp Web on other device",
                null, R.drawable.one, ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.primaryText), ContextCompat.getColor(this, R.color.primaryText)));

        addSlide(AppIntroFragment.newInstance("Link to Whatsapp", null, "Scan QR Code shown on the app",
                null, R.drawable.two, ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.primaryText), ContextCompat.getColor(this, R.color.primaryText)));

        addSlide(AppIntroFragment.newInstance("Disguise app", null, "You can disguise the app from Settings",
                null, R.drawable.three, ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.primaryText), ContextCompat.getColor(this, R.color.primaryText)));

        showSkipButton(false);
        setFadeAnimation();

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(IntroActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first_launch", false);
        editor.commit();

        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_settings, R.anim.exit_main);
        finish();


    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
