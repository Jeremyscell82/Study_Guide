package com.bitbytebitcreations.studyguide.FlashCards;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

/**
 * Created by JeremysMac on 1/19/17.
 */

public class Flash_Activity extends AppCompatActivity {

    private final String TAG = "FLASH_ACTIVITY";
    public final String DB_ACTIVITY_NAME = "flash";
    public ArrayList<Entry_Object> masterList;
    DB_Controller db;
    Drawer drawer;
    int flashCardCount;
    FloatingActionButton mFab;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitle("");

        //SET UP NAVIGATION DRAWER
        drawer = new Material_Drawer().navDrawer(this, toolbar);

        //INITIALIZE DB
        masterList = new ArrayList<>();
        db = new DB_Controller();

        //SET UP FAB
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);
        toggleFab(false);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab(false);
                //LAUNCH NEW FLASH CARD FRAGMENT
                launchAddCardFrag(false, null, null, -1);
            }
        });

        //PULL FROM DATA BASE
        loadFlashCardsFromDB();

        //SHUFFLE LIST

        //SET UP VARIABLES
        flashCardCount = 0;

        //LOAD FRAGMENT
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (masterList.size() > 0){
            //LOAD FLASH CARDS
            Flash_Fragment fragment = new Flash_Fragment().newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("question", masterList.get(flashCardCount).entryName); //GET FIRST FROM LIST
            bundle.putString("answer", masterList.get(flashCardCount).entryContent); //GET FIRST FROM LIST
            fragment.setArguments(bundle);
            ft.add(R.id.main_container, fragment)
                    .commit();
        } else {
            //NOTHING FOUND, THIS MUST BE YOUR FIRST TIME
            firstTimeAlert();
        }

    }

    /* SET UP MENU */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_config:
                launchConfigFrag();
                break;
            case R.id.action_timer:
                break;
            case R.id.action_save:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //TOGGLE BACK ARROW
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

    public void toggleFab(boolean display){
        if (display){
            mFab.show();
        } else {
            mFab.hide();
        }
    }

    //FIRST TIME ALERT DIALOG
    private void firstTimeAlert(){
        new MaterialDialog.Builder(this)
                .title("No flash cards found")
                .content("Let's add one to get the feel for it, just press on the + button in the lower right.")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        launchConfigFrag();
                    }
                })
                .positiveText("Sure")
                .cancelable(false)
                .show();
    }

    //LOAD NEXT FLASH CARD
    public void loadNextFlashCard(){
        flashCardCount++;
        if (flashCardCount <= masterList.size()){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Flash_Fragment flash_fragment = new Flash_Fragment().newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("question", "next question+ " + flashCardCount);
            bundle.putString("answer", "next answer");
            flash_fragment.setArguments(bundle);
            ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                    .replace(R.id.main_container, flash_fragment)
                    .commit();
        } else {
            new MaterialDialog.Builder(this)
                    .title("That's all you wrote")
                    .content("Repetition is the mother of learning.\nYou can add more by pressing the gear icon.\nRun it again?")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            flashCardCount = 0;
                            loadNextFlashCard();
                        }
                    }).positiveText("Sure")
                    .cancelable(false)
                    .show();

        }
    }



    private void launchConfigFrag(){
        ConfigList_Fragment fragment = new ConfigList_Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_bottom, R.animator.fade_out, R.animator.fade_in, R.animator.slide_out_right)
                .replace(R.id.main_container, fragment)
                .addToBackStack("")
                .commit();

    }

    public void launchAddCardFrag(boolean existingCard, String question, String answer, long rowId){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AddCard_Fragment fragment1 = new AddCard_Fragment();
        if (existingCard){
            Bundle bundle = new Bundle();
            bundle.putString("question", question);
            bundle.putString("answer", answer);
            bundle.putLong("rowId", rowId);
            fragment1.setArguments(bundle);
        }
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.main_container, fragment1)
                .addToBackStack("")
                .commit();
    }

    /* RECYCLERVIEW ON CLICK LISTENER */
    public void recyclerOnClick(Entry_Object flashCard){
        toggleFab(false);
        launchAddCardFrag(true, flashCard.entryName, flashCard.entryContent, flashCard.rowID);
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
    public void loadFlashCardsFromDB(){
        /*LOAD ALL DATA FOR THIS ACTIVITY*/
        displayProgressBar(true);
        //INIT DB
        db.DB_OPEN(this);
        masterList = db.getActivityData(DB_ACTIVITY_NAME);

        Log.i(TAG, "MASTER LIST SIZE: " + masterList.size());
        displayProgressBar(false);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFab.setVisibility(View.GONE);
    }
}
