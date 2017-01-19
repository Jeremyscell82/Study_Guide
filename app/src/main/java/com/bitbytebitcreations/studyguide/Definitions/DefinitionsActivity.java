package com.bitbytebitcreations.studyguide.Definitions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
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
                recyclerOnClick(1, null);
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

    /* SET UP MENU */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    /* RECYCLER VIEW ON CLICK LISTENER FOR ALL FRAGMENTS */
    public void recyclerOnClick(int key, String itemSelected){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if (key == 1){
            bundle.putBoolean("newEntry", true);
        }
        Definition_Fragment fragment = new Definition_Fragment().newInstance();
        fragment.setArguments(bundle);
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.main_container, fragment)
                .addToBackStack("")
                .commit();
    }


}
