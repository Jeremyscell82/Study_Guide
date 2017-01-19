package com.bitbytebitcreations.studyguide.Definitions.Fragments;

import android.app.Fragment;
import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.mikepenz.materialdrawer.Drawer;

import java.util.Date;
import java.util.List;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class Definition_Fragment extends Fragment {

    private final String TAG = "DEFINITION_FRAG";
    private String db_activity_name;
    EditText defNameField;
    EditText defContentField;
    String defName;
    String definition;
    long rowId;
    boolean isInEditMode;
    Menu mMenu;


    public Definition_Fragment newInstance(){
        return new Definition_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        //SET INITIAL VALUES
        isInEditMode = false;


        defNameField = (EditText) view.findViewById(R.id.defition_name);
        defContentField = (EditText) view.findViewById(R.id.definition_content);
        defNameField.setEnabled(false);
        defContentField.setEnabled(false);



        //SET UP TOOLBAR
        DefinitionsActivity activity = (DefinitionsActivity) getActivity();
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
            //SET VALUES
            defName = bundle.getString("defName");
            definition = bundle.getString("definition");
            rowId = bundle.getLong("rowId");
            db_activity_name = bundle.getString("activity_name");
            if (bundle.getBoolean("editMode")){
                //DELAY TO ALLOW FOR CREATION OF MENU
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toggleEditMode(true);
                    }
                }, 400);
            } else {
                setUI();
            }
        }

        return view;
    }

    //SET UI
    private void setUI(){
        defNameField.setText(defName);
        defContentField.setText(definition);
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
        switch (id){
            case R.id.menu_save:
                //SEND NEW ENTRY TO DB
                saveDefinition();
                //DISABLE EDITMODE
                toggleEditMode(false);
                break;
            case R.id.menu_edit:
                toggleEditMode(false);
                break;
            case R.id.menu_flash:
                break;
            case R.id.menu_share:
                break;
            case R.id.menu_delete:
                break;
        }
        Log.i(TAG, "MENU PRESSED");
        return super.onOptionsItemSelected(item);
    }



    private void toggleEditMode(boolean withFocus){
        if (!isInEditMode){
            isInEditMode = true;
            defNameField.setEnabled(true);
            defContentField.setEnabled(true);
            if (withFocus){
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(getActivity().getCurrentFocus(), WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                defNameField.requestFocus();
            }
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

    private void saveDefinition(){
        Entry_Object object = createObject();
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.addNewEntry(object);

        //
    }

    private Entry_Object createObject(){
        Entry_Object object = new Entry_Object();
        if (rowId >= 0)object.setRowID(rowId);
        object.setEntryDate(new Date());
        object.setEntryActivity(db_activity_name);
        object.setCatID(-1);
        object.setEntryName(defNameField.getText().toString());
        object.setEntryContent(defContentField.getText().toString());
        return object;
    }



}
