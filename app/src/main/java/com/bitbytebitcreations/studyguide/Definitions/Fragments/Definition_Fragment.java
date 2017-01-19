package com.bitbytebitcreations.studyguide.Definitions.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class Definition_Fragment extends Fragment {

    private final String TAG = "DEFINITION_FRAG";
    EditText defNameField;
    EditText defContentField;
    String defName;
    String definition;
    boolean isInEditMode = false;
    Menu mMenu;


    public Definition_Fragment newInstance(){
        return new Definition_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);



        defNameField = (EditText) view.findViewById(R.id.defition_name);
        defContentField = (EditText) view.findViewById(R.id.definition_content);
        defNameField.setEnabled(false);
        defContentField.setEnabled(false);



        //SET UP TOOLBAR
        DefinitionsActivity activity = (DefinitionsActivity) getActivity();
        activity.setToolbarTitle("");
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                return true;
            }
        });

        //REMOVE FAB
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        //SET UP MENU CONTROL
        setHasOptionsMenu(true);

        //GET BUNDLE PASSED IN
        Bundle bundle = getArguments();
        if (bundle != null){
            if (bundle.getBoolean("newEntry")){
                //DELAY TO ALLOW FOR CREATION OF MENU
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toggleEditMode();
                    }
                }, 400);
            }
        }

        return view;
    }

    /* MENU*/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        mMenu = menu;
        menu.clear();
        //NORMAL MENU
        inflater.inflate(R.menu.menu_definition, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(TAG, "MENU PRESSED");
        toggleEditMode();
        return super.onOptionsItemSelected(item);
    }



    private void toggleEditMode(){
        if (!isInEditMode){
            isInEditMode = true;
            defNameField.setEnabled(true);
            defContentField.setEnabled(true);
            //ENABLE SAVE BUTTON
            if (mMenu != null){
                mMenu.close();
                mMenu.clear();
                getActivity().getMenuInflater().inflate(R.menu.menu_save, mMenu);
            }
        } else {
            defNameField.setEnabled(false);
            defContentField.setEnabled(false);
            //DISABLE SAVE BUTTON
            if (mMenu != null){
                mMenu.clear();
                getActivity().getMenuInflater().inflate(R.menu.menu_definition, mMenu);
            }
        }
    }




}
