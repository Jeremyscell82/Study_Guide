package com.bitbytebitcreations.studyguide.GreatSites;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.bitbytebitcreations.studyguide.GreatSites.Fragments.Category_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.Fragments.SitesList_Fragment;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.bitbytebitcreations.studyguide.R;

/**
 * ACTIVITY 2, GREAT SITES
 *
 * Created by JeremysMac on 1/17/17.
 */

public class SitesActivity extends AppCompatActivity {

    private final String TAG = "SITES_ACTIVITY";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");//REMOVE ACTIONBAR TITLE
        toolbar.setTitle("Select Category");

        //SET UP NAV DRAWER
        new Material_Drawer().navDrawer(this, toolbar);

        //LAUNCH FRAGMENT
        fragController(0);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //FRAGMENT CONTROLLER
    private void fragController(int position){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (position){
                case 0:
                    Category_Fragment catFrag = new Category_Fragment().newInstance();
                    ft.replace(R.id.main_container, catFrag)
                            .commit();
                    break;
                case 1:
                    SitesList_Fragment siteFrag = new SitesList_Fragment().newInstance();
                    ft.replace(R.id.main_container, siteFrag)
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

                fragController(1);
                break;
            case 1: //SITES
                //LAUNCH WEBVIEW FRAGMENT
                Log.i(TAG, "LAUNCH WEBVIEW....");
                break;
        }
    }



}
