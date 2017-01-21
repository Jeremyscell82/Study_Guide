package com.bitbytebitcreations.studyguide.Definitions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bitbytebitcreations.studyguide.Definitions.Fragments.DefList_Fragment;
import com.bitbytebitcreations.studyguide.Definitions.Fragments.Definition_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class DefinitionsActivity extends AppCompatActivity {

    private final String TAG = "DEF_ACTIVITY";
    public final String DB_ACTIVITY_NAME = "definitions";
    Toolbar toolbar;
    TextView toolbarTitle;
    Drawer drawer;
    FloatingActionButton mFab;
    ProgressDialog progressDialog;
    ArrayList<Entry_Object> masterList;
    DB_Controller db;
    List<String> defNames;
    List<String> definitions;
    List<Long> rowIds;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Definitions");

        //SET UP TOOLBAR
        drawer = new Material_Drawer().navDrawer(this, toolbar);

        //LOAD UP HOME FRAGMENT
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DefList_Fragment fragment = new DefList_Fragment().newInstance();
        ft.replace(R.id.main_container, fragment)
                .commit();

        //ENABLE FLOATING ACTION BUTTON
        mFab = (FloatingActionButton) findViewById(R.id.fab);
//        mFab.setVisibility(View.VISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ADD NEW DEFINITION DIALOG
                fragController(true, null, null, -1);
            }
        });

        //INITIALIZE DB
        masterList = new ArrayList<>();
        db = new DB_Controller();

        //LOAD DEFINITIONS FROM DB
//        loadDefinitionsFromDB();

    }

    /*SET TOOLBAR TITLE*/
//    public void setToolbarTitle(String title){
//        toolbar.setTitle(title);
//    }

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
    public void recyclerOnClick(boolean isItNew, int position){
        //GET THE PROPER DATASET AND SEND TO FRAG CONTROLLER

        //LOAD DEFINITIONS FRAGMENT
        fragController(isItNew, defNames.get(position), definitions.get(position), rowIds.get(position));

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();
//        if (key == 1){
//            bundle.putBoolean("newEntry", true);
//        } else {
//
//        }
//        Definition_Fragment fragment = new Definition_Fragment().newInstance();
//        fragment.setArguments(bundle);
//        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right)
//                .replace(R.id.main_container, fragment)
//                .addToBackStack("")
//                .commit();
    }

    private void fragController(boolean editMode, String title, String content, long rowId){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putBoolean("editMode", editMode);
        bundle.putString("defName", title);
        bundle.putString("definition", content);
        bundle.putLong("rowId", rowId);
        bundle.putString("activity_name", DB_ACTIVITY_NAME);
        Definition_Fragment fragment = new Definition_Fragment().newInstance();
        fragment.setArguments(bundle);
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.main_container, fragment)
                .addToBackStack("")
                .commit();
    }

    public void shareLink(String name, String content){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check this site out called " + name);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(sharingIntent, "How would you like to send the site?"));
    }

    /* ========================== DB CALLS ========================== */
    private void displayProgressBar(boolean displayIt){
        if (displayIt){
            //SET UP PROGRESS DIALOG
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else {
            if (progressDialog != null){
                progressDialog.dismiss();
            }
        }
    }

    public void loadDefinitionsFromDB(){
        displayProgressBar(true);
        //INITIALIZE DATABASE CONNECTION
        db.DB_OPEN(this);
        masterList = db.getActivityData(DB_ACTIVITY_NAME);
        Log.i(TAG, "MASTER LIST SIZE: " + masterList.size());
        displayProgressBar(false);
    }

    public List<String> getDefNames(){
        if (masterList.size() > 0){
            defNames = new ArrayList<>();
            definitions = new ArrayList<>();
            rowIds = new ArrayList<>();
            for (Entry_Object object : masterList){
                defNames.add(object.entryName);
                definitions.add(object.entryContent);
                rowIds.add(object.rowID);
            }
            return defNames;
        }
        return null;
    }

    public List<String> getDefinitions(){
        return definitions;
    }

    public List<Long> getRowIds(){
        return rowIds;
    }
}
