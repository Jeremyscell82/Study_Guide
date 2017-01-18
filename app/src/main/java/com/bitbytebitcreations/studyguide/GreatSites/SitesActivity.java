package com.bitbytebitcreations.studyguide.GreatSites;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;

import com.bitbytebitcreations.studyguide.GreatSites.Fragments.Category_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.Fragments.SitesList_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.Fragments.WebView_Fragment;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

/**
 * ACTIVITY 2, GREAT SITES
 *
 * Created by JeremysMac on 1/17/17.
 */

public class SitesActivity extends AppCompatActivity {

    private final String TAG = "SITES_ACTIVITY";
    String cat = "Category";
    String sites = "Sites";
    String web = "Webview";
    Toolbar toolbar;
    Drawer drawer;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");//REMOVE ACTIONBAR TITLE
        setToolbarTitle("Welcome!!");

        //SET UP NAV DRAWER
        drawer = new Material_Drawer().navDrawer(this, toolbar);

        //LAUNCH FRAGMENT
        fragController(true, cat);


    }

    /*SET TOOLBAR TITLE*/
    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }
    public Drawer toggleBackArrow(boolean display){
        if (display){
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return drawer;
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    //FRAGMENT CONTROLLER
    public void fragController(boolean initLaunch, String fragName){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (fragName){
                case "Category":
                    Category_Fragment catFrag = new Category_Fragment().newInstance();
                    if (initLaunch){
                        ft.replace(R.id.main_container, catFrag)
                                .commit();
                    } else {
                        ft.replace(R.id.main_container, catFrag)
                                .addToBackStack("")
                                .commit();
                    }
                    break;
                case "Sites":
                    SitesList_Fragment siteFrag = new SitesList_Fragment().newInstance();
                    ft.replace(R.id.main_container, siteFrag)
                            .addToBackStack("")
                            .commit();
                    break;
                case "Webview":
                    WebView_Fragment webFrag = new WebView_Fragment().newInstance();
                    ft.replace(R.id.main_container, webFrag)
                            .addToBackStack("")
                            .commit();
                    break;
            }
    }


    //RECYCLERVIEW ON CLICK LISTENER
    public void recyclerOnClick(int key, int position){
        //DETERMINE WHICH FRAGMENT BY KEY
        //0 = HOME, 1 = SITES
        switch (key){
            case 0: //CATEGORY
                //LAUNCH SITES FRAGMENT
                fragController(false, sites);
                break;
            case 1: //SITES
                //LAUNCH WEBVIEW FRAGMENT
                Log.i(TAG, "LAUNCH WEBVIEW....");
                fragController(false, web);
                break;
        }
    }



}
