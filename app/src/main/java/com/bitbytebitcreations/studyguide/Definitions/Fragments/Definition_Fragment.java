package com.bitbytebitcreations.studyguide.Definitions.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
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


    public Definition_Fragment newInstance(){
        return new Definition_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        //GET BUNDLE PASSED IN
        Bundle bundle = getArguments();
        if (bundle != null){

        }

        defNameField = (EditText) view.findViewById(R.id.defition_name);
        defContentField = (EditText) view.findViewById(R.id.definition_content);
        toggleEditMode(); //DISABLE FIELDS



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
        setHasOptionsMenu(true);


        return view;
    }


    private void toggleEditMode(){
        if (isInEditMode){

        } else {
            defNameField.setEnabled(false);
            defContentField.setEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(TAG, "MENU PRESSED");
        return super.onOptionsItemSelected(item);
    }

}
