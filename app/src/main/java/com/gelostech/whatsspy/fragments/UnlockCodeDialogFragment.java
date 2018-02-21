package com.gelostech.whatsspy.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gelostech.whatsspy.R;

/**
 * Created by tirgei on 10/15/17.
 */

public class UnlockCodeDialogFragment extends Dialog implements View.OnClickListener {
    private Activity activity;
    private String code;
    private SharedPreferences sharedPreferences;

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9,b0, bC, bOK;
    private EditText editText;

    public UnlockCodeDialogFragment(Activity activity){
        super(activity);

        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unlock_code_dialog);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        b0 = findViewById(R.id.codeZero);
        b1 = findViewById(R.id.codeOne);
        b2 = findViewById(R.id.codeTwo);
        b3 = findViewById(R.id.codeThree);
        b4 = findViewById(R.id.codeFour);
        b5 = findViewById(R.id.codeFive);
        b6 = findViewById(R.id.codeSix);
        b7 = findViewById(R.id.codeSeven);
        b8 = findViewById(R.id.codeEight);
        b9 = findViewById(R.id.codeNine);
        bC = findViewById(R.id.codeClear);
        bOK = findViewById(R.id.codeEnter);
        editText = findViewById(R.id.code_edittext);

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        bC.setOnClickListener(this);
        bOK.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.codeZero:
                editText.setText(editText.getText() + "0");
                break;

            case R.id.codeOne:
                editText.setText(editText.getText() + "1");
                break;

            case R.id.codeTwo:
                editText.setText(editText.getText() + "2");
                break;

            case R.id.codeThree:
                editText.setText(editText.getText() + "3");
                break;

            case R.id.codeFour:
                editText.setText(editText.getText() + "4");
                break;

            case R.id.codeFive:
                editText.setText(editText.getText() + "5");
                break;

            case R.id.codeSix:
                editText.setText(editText.getText() + "6");
                break;

            case R.id.codeSeven:
                editText.setText(editText.getText() + "7");
                break;

            case R.id.codeEight:
                editText.setText(editText.getText() + "8");
                break;

            case R.id.codeNine:
                editText.setText(editText.getText() + "9");
                break;

            case R.id.codeClear:
                if(editText.getText().length() > 0) {
                    CharSequence currentText = editText.getText();
                    editText.setText(currentText.subSequence(0, currentText.length()-1));
                }
                break;

            case R.id.codeEnter:
                if(editText.getText().length() >= 4) {
                    //Toast.makeText(activity, "Password entered", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor prefEdit = sharedPreferences.edit();
                    prefEdit.putString("unlock_code", editText.getText().toString());
                    prefEdit.commit();
                    dismiss();

                    Toast.makeText(activity, "Unlock code saved!", Toast.LENGTH_SHORT).show();

                } else {
                    editText.setError("Code should be atleast 4 digits!");
                }
                break;


        }
    }

}
