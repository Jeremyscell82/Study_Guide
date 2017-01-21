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
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
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
    private long rowId = -1;
    boolean isInLandscape;
    FrameLayout container;

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
        isInLandscape = false;
//        container = (FrameLayout) getActivity().findViewById(R.id.main_container);
        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            siteName = bundle.getString("siteName");
            siteUrl = bundle.getString("siteUrl");
            rowId = bundle.getLong("rowId");
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
        inflater.inflate(R.menu.menu_webview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                webView.reload();
                break;
            case R.id.action_share:
                SitesActivity activity = (SitesActivity) getActivity();
                activity.shareLink(siteName, siteUrl);
                break;
            case R.id.action_delete:
                if (rowId != -1){
                    deleteSite();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteSite(){
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.deleteEntry(rowId, siteName);
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.destroy();
    }
}
