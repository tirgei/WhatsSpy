package com.gelostech.whatsspy.fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gelostech.whatsspy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment{
    private SwitchPreference disguiseApp;
    private Preference unlockCode, rateApp, shareApp, termsOfUse, help;
    private final String AppLink = "https://play.google.com/store/apps/details?id=";
    private UnlockCodeDialogFragment dialog;
    private HelpDialog helpDialog;
    private SharedPreferences sharedPreferences;
    private View view;

    public PreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view = inflater.inflate(R.layout.fragment_preference, container, false);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String code = sharedPreferences.getString("unlock_code", null);

        disguiseApp = (SwitchPreference) findPreference("disguise_app");
        unlockCode = findPreference("calc_code");
        rateApp = findPreference("rate_app");
        shareApp = findPreference("share_link");
        termsOfUse = findPreference("terms_of_use");
        help = findPreference("help");
        dialog = new UnlockCodeDialogFragment(getActivity());
        helpDialog = new HelpDialog(getActivity());

        rateApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse(AppLink + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(AppLink + getActivity().getPackageName())));
                }

                return true;
            }
        });

        shareApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.app_name));
                String message = getResources().getString(R.string.invite_body) + "\n\n" + AppLink + getActivity().getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Send link to..."));

                return true;
            }
        });

        unlockCode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dialog.show();
                return true;
            }
        });

        disguiseApp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean switched = ((SwitchPreference) preference).isChecked();

                //Toast.makeText(getActivity(), "" + switched, Toast.LENGTH_SHORT).show();

                if(!switched && code == null){
                    UnlockCodeDialogFragment dialogFragment = new UnlockCodeDialogFragment(getActivity());
                    dialogFragment.show();
                }

                return true;
            }
        });

        termsOfUse.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final String url = "https://us-central1-pollthat-f5dbb.cloudfunctions.net/terms?index=1";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;
            }
        });

        help.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                helpDialog.show();
                return true;
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
