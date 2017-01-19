package com.bitbytebitcreations.studyguide.GreatSites.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

/**
 * Created by JeremysMac on 1/17/17.
 */

public class WebView_Fragment extends Fragment {

    ProgressBar progressBar;
    WebView webView;
    private String TAG = "WEBVIEW";
    private String siteUrl;
    private String siteName;


    public WebView_Fragment newInstance(){
        return new WebView_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        //SET UP UI
        webView = (WebView) view.findViewById(R.id.webview);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar); //ALREADY VISIBLE

        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            siteName = bundle.getString("siteName");
            siteUrl = bundle.getString("siteUrl");
        }


        //SET UP TOOLBAR
        final SitesActivity activity = (SitesActivity) getActivity();
        activity.setToolbarTitle(siteName);
        //TOGGLE BACK ARROW
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                Log.i(TAG, "NAVIGATION BACK ARROW PRESSED");
                activity.getFragmentManager().popBackStack();
                return true;
            }
        });



        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(siteUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        //SET MENU ITEMS, REFRESH
        setHasOptionsMenu(true);

        return view;
    }

    /* MENU*/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        webView.reload();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
