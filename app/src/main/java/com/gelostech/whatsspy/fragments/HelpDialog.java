package com.gelostech.whatsspy.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gelostech.whatsspy.R;

/**
 * Created by tirgei on 10/19/17.
 */

public class HelpDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private Button close;
    private String helpMessage;
    private TextView textView;
    private boolean showHelp;

    public HelpDialog(Activity activity){
        super(activity);

        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.help_dialog);

        helpMessage = "\t\t\t How to set up account" +
                "\n\n" +
                "1. Open WhatsApp account on the device you want to spy on or view chats from\n\n" +
                "2. Go to Menu -> WhatsApp Web\n\n" +
                "3. Scan the QR Code on the main screen of WhatsSpy using the other phone's WhatsApp web\n\n" +
                "4. Wait for WhatsSpy to sync with WhatsApp account\n\n" +
                "5. Enjoy!!!";

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        showHelp = sharedPreferences.getBoolean("show_help", true);

        close = findViewById(R.id.help_close_button);
        textView = findViewById(R.id.help_text_area);

        textView.setText(helpMessage);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.help_close_button:
                dismiss();
                if(showHelp){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("show_help", false);
                    editor.commit();
                }
                break;
        }
    }
}
