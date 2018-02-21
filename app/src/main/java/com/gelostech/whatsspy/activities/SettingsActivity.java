package com.gelostech.whatsspy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.gelostech.whatsspy.R;
import com.gelostech.whatsspy.fragments.PreferenceFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class SettingsActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad));
        this.interstitialAd.loadAd(new AdRequest.Builder().build());

        this.interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        getFragmentManager().beginTransaction().add(R.id.preferences_holder, new PreferenceFragment()).commit();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_settings, R.anim.enter_main);
        finish();
        if (this.interstitialAd.isLoaded())
        {
            this.interstitialAd.show();
            return;
        } else {
            Log.d("SettingsActivity", "Interstitial ad not loaded");
        }
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.exit_settings, R.anim.enter_main);
                finish();

                if(this.interstitialAd.isLoaded()){
                    this.interstitialAd.show();
                } else {
                    Log.d("SettingsActivity", "Interstitial ad loaded");
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
