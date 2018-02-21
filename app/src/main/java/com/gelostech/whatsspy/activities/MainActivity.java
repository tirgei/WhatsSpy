package com.gelostech.whatsspy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.gelostech.whatsspy.R;
import com.gelostech.whatsspy.fragments.HelpDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {
    //private XWalkView webview;
    private WebView webView;
    private Boolean doubleBackToExit = false;
    private final String USER_AGENT_STRING = "Mozilla/5.0 (X11;U;Linux i686;en-US;rv:1.9.0.4)Gecko/20100101 Firefox/45.0";
    private Toolbar toolbar;
    private final String WHATSAPP = "https://web.whatsapp.com";
    private static final String TAG = "MainActivity";
    private AdView adView;
    private InterstitialAd interstitialAd;
    private HelpDialog helpDialog;
    private TextView connectionError;

    private class MyWebViewClient extends WebViewClient{
        private MyWebViewClient(){
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url){
            if(Uri.parse(url).getHost().contains(".whatsapp.com")){
                return false;
            }

            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("Main Activity", error.toString());
            webView.setVisibility(View.GONE);
            connectionError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean showHelp = sharedPreferences.getBoolean("show_help", true);

        helpDialog = new HelpDialog(this);
        if(showHelp)
            helpDialog.show();

        adView = findViewById(R.id.main_banner);
        final AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adView.loadAd(adRequest);
            }
        });

        connectionError = findViewById(R.id.connection_error);

        webView = findViewById(R.id.chrome_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setUserAgentString(USER_AGENT_STRING);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.loadUrl(WHATSAPP);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //webView.loadUrl(WHATSAPP);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                } else {
                    Log.d(TAG, "Interstitial ad was not loaded");
                }
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != 4 || !webView.canGoBack()){
            return super.onKeyDown(keyCode, event);
        }

        onBackPressed();

        return true;
    }

    @Override
    public void onBackPressed() {
        if(this.doubleBackToExit){
            super.onBackPressed();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(interstitialAd.isLoaded()){
                        interstitialAd.show();
                    } else {
                        Log.d(TAG, "Interstitial ad was not loaded");
                    }
                }
            }, 500);

        } else {
            this.doubleBackToExit = true;
            Toast.makeText(this, "Tap BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.doubleBackToExit = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spy_activity_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:

                webView.loadUrl(WHATSAPP);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!webView.isShown()){
                            webView.setVisibility(View.VISIBLE);
                            connectionError.setVisibility(View.GONE);
                        }
                    }
                }, 2000);
                break;

            case R.id.menu_settings:
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_settings, R.anim.exit_main);
                break;

            default:
                break;

        }

        return true;
    }
}
