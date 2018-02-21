package com.gelostech.whatsspy.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gelostech.whatsspy.R;
import com.github.paolorotolo.appintro.ISlidePolicy;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisclaimerFragment extends Fragment implements ISlidePolicy{
    private WebView webView;
    private final String LINK = "https://us-central1-pollthat-f5dbb.cloudfunctions.net/terms?index=1";

    public DisclaimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disclaimer, container, false);

        webView = view.findViewById(R.id.disclaimer_webivew);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(LINK);

        return view;
    }

    @Override
    public boolean isPolicyRespected() {
        return false;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Toast.makeText(getActivity(), "Please agree to the Terms and Conditions.", Toast.LENGTH_LONG).show();
    }
}
