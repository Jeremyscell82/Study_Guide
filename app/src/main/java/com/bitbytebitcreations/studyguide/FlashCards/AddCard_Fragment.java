package com.bitbytebitcreations.studyguide.FlashCards;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.mikepenz.materialdrawer.Drawer;

import java.util.Date;

/**
 * Created by JeremysMac on 1/20/17.
 */

public class AddCard_Fragment extends Fragment {

    final String TAG = "ADDCARD_FRAGMENT";
    private  String db_activity_name;
    EditText defNameField;
    EditText defContentField;
    String question;
    String answer;
    long rowId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        //DECLARE ACTIVITY
        final Flash_Activity activity = (Flash_Activity) getActivity();

        //DECLARE ACTIVITY DB NAME
        db_activity_name = activity.DB_ACTIVITY_NAME;

        //DECLARE UI
        defNameField = (EditText) view.findViewById(R.id.defition_name);
        defNameField.setHint(getString(R.string.flashcard_name_hint));
        defContentField = (EditText) view.findViewById(R.id.definition_content);
        defContentField.setHint(getString(R.string.flashcard_content_hint));

        //SET UP TOOLBAR TITLE
        TextView toolbarTitle = (TextView) activity.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Flash Card Entry");
        //SET TOOLBAR BACK BUTTON
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                toggleKeyboard(false);
                return true;
            }
        });

        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            //EXISTING ENTRY
            question = bundle.getString("question");
            defNameField.setText(question);
            answer = bundle.getString("answer");
            defContentField.setText(answer);
            rowId = bundle.getLong("rowId");
        } else {
            //NEW CARD, REQUEST FOCUS
            toggleKeyboard(true);
            defNameField.requestFocus();
        }

        Log.i(TAG, "ROW ID ON CREATE: " + rowId);

        //SET MENU ITEMS
        setHasOptionsMenu(true);


        //REMOVE THE FAB
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.toggleFab(false);
            }
        }, 500);


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
                //CLOSE KEYBOARD
                toggleKeyboard(false);
                //SAVE DATA
                saveFlashCard();

                break;
            case R.id.action_share:
                break;
            case R.id.action_delete:
                if (rowId != -1){
                    //ITS NOT A NEW ENTRY
                    deleteFlashCard();
                } else {
                    Toast.makeText(getActivity(), "Can't delete when its not saved", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleKeyboard(boolean display){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (display){
            imm.showSoftInput(getActivity().getCurrentFocus(), WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }
    private void saveFlashCard(){
        Entry_Object object = createObject();
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        if (rowId >= 0){
            //EXISTING ENTRY
            controller.updateEntry(rowId, object);
        } else {
            //NEW ENTRY
            controller.addNewEntry(object);
        }
        //RELOAD DATA


        //POP BACK STACK
        getFragmentManager().popBackStack();
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

    private void deleteFlashCard(){
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.deleteEntry(rowId, question);
        Log.i(TAG, "ROW ID ON DELETE: " + rowId);

        getFragmentManager().popBackStack();
    }

}
