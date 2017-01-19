package com.bitbytebitcreations.studyguide.GreatSites;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.bitbytebitcreations.studyguide.GreatSites.Fragments.Categories_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.Fragments.SitesList_Fragment;
import com.bitbytebitcreations.studyguide.GreatSites.Fragments.WebView_Fragment;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * ACTIVITY 2, GREAT SITES
 *
 * Created by JeremysMac on 1/17/17.
 */

public class SitesActivity extends AppCompatActivity {

    private final String TAG = "SITES_ACTIVITY";
    public final String DB_ACTIVITY_NAME = "sites";
    public String cat = "category"; //USED FOR FRAGMENTS AND DATABASE NAME
    public String sites = "sites";
    public String web = "webview";
    Toolbar toolbar;
    Drawer drawer;
    ArrayList<Entry_Object> masterList;
    DB_Controller db;
    List<String> siteUrls;
    ProgressDialog progressDialog;



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

        //INITIALIZE DB
        masterList = new ArrayList<>();
        db = new DB_Controller();

        //LAUNCH FRAGMENT
        fragController(cat, null);

        //GET DATA FROM DB
        loadSitesFromDB();


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
        getMenuInflater().inflate(R.menu.main_sites, menu);
        return true;
    }



    //FRAGMENT CONTROLLER
    public void fragController(String fragName, String itemSelected){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
            switch (fragName){
                case "category":
                    Categories_Fragment catFrag = new Categories_Fragment().newInstance();
                        ft.replace(R.id.main_container, catFrag)
                                .commit();
                    break;
                case "sites":
                    SitesList_Fragment siteFrag = new SitesList_Fragment().newInstance();
                    bundle.putString("item", itemSelected);
                    siteFrag.setArguments(bundle);
                    ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right)
                            .replace(R.id.main_container, siteFrag)
                            .addToBackStack("")
                            .commit();
                    break;
                case "webview":
                    WebView_Fragment webFrag = new WebView_Fragment().newInstance();
                    bundle.putString("item", itemSelected);
                    webFrag.setArguments(bundle);
                    ft.setCustomAnimations(R.animator.fade_in, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.fade_out).replace(R.id.main_container, webFrag)
                            .addToBackStack("")
                            .commit();
                    break;
            }
    }


    /* RECYCLER VIEW ON CLICK LISTENER FOR ALL FRAGMENTS */
    public void recyclerOnClick(int key, String itemSelected){
        //DETERMINE WHICH FRAGMENT BY KEY
        //0 = HOME, 1 = SITES
        switch (key){
            case 0: //IF ON CATEGORY
                //LAUNCH SITES FRAGMENT
                fragController(sites, itemSelected);
                break;
            case 1: //IF ON SITES
                //LAUNCH WEBVIEW FRAGMENT
                Log.i(TAG, "LAUNCH WEBVIEW....");
                fragController(web, itemSelected);
                break;
        }
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
    public void loadSitesFromDB(){
        /*LOAD ALL DATA FOR THIS ACTIVITY*/
        displayProgressBar(true);
        //INIT DB
        db.DB_OPEN(this);
        masterList = db.getActivityData(DB_ACTIVITY_NAME);

        Log.i(TAG, "MASTER LIST SIZE: " + masterList.size());
        displayProgressBar(false);
    }

    public void addNewEntryToDB(Entry_Object entry){
        /* ADD NEW ENTRY TO DB */


        //COMPLETED TOAST
        toastIt("Item saved");
    }

    public void updateEntryInDB(){
        /* UPDATE EXISTING ENTRY IN DB */

    }

    private void toastIt(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /* ========================== FILTER ALGORITHMS ========================== */

    public List<String> getCategories(){
        if (masterList.size() > 0){
            //USE HASHSET TO PREVENT DUPLICATES
            HashSet list = new HashSet();
            for (Entry_Object object : masterList){
                list.add(object.entryCategory);
            }
            //CONVERT BACK TO LIST<STRING>
            List<String> catList = new ArrayList<>();
            catList.addAll(list);
            return catList;
        }
        return null;
    }

    public List<String> getSites(String category){
        if (masterList.size() > 0){
            List<String> list = new ArrayList<>();
            siteUrls = new ArrayList<>();
            for (Entry_Object object : masterList){
                //GET ONES THAT MATCH CATEGORY
                if (object.entryCategory.equals(category)){
                    //ENSURE THERE IS CONTENT...ENSURE ITS NOT THE CATEGORY NAME ROW
                    if (!object.entryName.equals("") && object.entryContent.startsWith("http")){
                        list.add(object.entryName);
                        siteUrls.add(object.entryContent);
                    }
                }
            }
            return list;
        }
        return null;
    }

    public List<String> getURLs(){
        return siteUrls;
    }
}
