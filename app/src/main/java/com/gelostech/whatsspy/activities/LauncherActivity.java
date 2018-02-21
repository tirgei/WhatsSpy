package com.gelostech.whatsspy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gelostech.whatsspy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isDisguised = sharedPreferences.getBoolean("disguise_app", false);
        boolean isFirstLaunch = sharedPreferences.getBoolean("first_launch", true);
        final String id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference df = db.getReference("users");
        final String date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        if(isFirstLaunch){
            df.child(id).setValue(date);

            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();


        } else if(!isFirstLaunch){
            if(isDisguised){
                Intent intent = new Intent(this, CalcActivity.class);
                startActivity(intent);
                finish();
            } else if(!isDisguised){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
