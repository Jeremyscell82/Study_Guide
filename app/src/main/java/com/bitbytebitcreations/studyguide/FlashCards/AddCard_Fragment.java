package com.bitbytebitcreations.studyguide.FlashCards;

import android.app.Fragment;
import android.os.Bundle;
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

import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

/**
 * Created by JeremysMac on 1/20/17.
 */

public class AddCard_Fragment extends Fragment {

    final String TAG = "ADDCARD_FRAGMENT";
    EditText defNameField;
    EditText defContentField;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        //DECLARE UI
        defNameField = (EditText) view.findViewById(R.id.defition_name);
        defContentField = (EditText) view.findViewById(R.id.definition_content);

        //SET TOOLBAR BACK BUTTON
        Flash_Activity activity = (Flash_Activity) getActivity();
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                return true;
            }
        });

        //CONFIGURE FLOATING ACTION BUTTON
//        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        fab.setVisibility(View.GONE);

        //SET MENU ITEMS
        setHasOptionsMenu(true);


        return view;
    }

    /* MENU*/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_flash_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                //SAVE DATA
                //POP BACK STACK
                getFragmentManager().popBackStack();
                break;
            case R.id.action_share:
                break;
            case R.id.action_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
