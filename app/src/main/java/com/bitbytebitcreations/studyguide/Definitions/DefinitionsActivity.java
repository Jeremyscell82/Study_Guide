package com.bitbytebitcreations.studyguide.Definitions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bitbytebitcreations.studyguide.Definitions.Fragments.DefList_Fragment;
import com.bitbytebitcreations.studyguide.Definitions.Fragments.Definition_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class DefinitionsActivity extends AppCompatActivity {

    private final String TAG = "DEF_ACTIVITY";
    public final String DB_ACTIVITY_NAME = "definitions";
    Toolbar toolbar;
    Drawer drawer;
    FloatingActionButton mFab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setToolbarTitle("Learning time");

        //SET UP TOOLBAR
        drawer = new Material_Drawer().navDrawer(this, toolbar);

        //LOAD UP HOME FRAGMENT
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DefList_Fragment fragment = new DefList_Fragment().newInstance();
        ft.replace(R.id.main_container, fragment)
                .commit();

        //ENABLE FLOATING ACTION BUTTON
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ADD NEW DEFINITION DIALOG

            }
        });


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

    /* RECYCLER VIEW ON CLICK LISTENER FOR ALL FRAGMENTS */
    public void recyclerOnClick(int key, String itemSelected){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Definition_Fragment fragment = new Definition_Fragment().newInstance();
        ft.replace(R.id.main_container, fragment)
                .commit();
    }
}
