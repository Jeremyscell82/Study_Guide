package com.bitbytebitcreations.studyguide.GreatSites.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Recycler_Adapter;

import java.util.Date;
import java.util.List;

/**
 * ACTIVITY 2, SITES ACTIVITY
 * FRAGMENT 0, CATEGORY AKA HOME SCREEN FOR SITES ACTIVITY
 *
 * Created by JeremysMac on 1/17/17.
 */

public class Categories_Fragment extends Fragment {

    private final String TAG = "CATEGORY";
    private String db_activity_name;
    Recycler_Adapter adapter;
    List<String> catNames;
    List<Long> catIds;
    //TEMP SOLUTIONS
    long rowID;

    public Categories_Fragment newInstance(){
        return new Categories_Fragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //SET UP TOOLBAR
        SitesActivity activity = (SitesActivity) getActivity();
        activity.setToolbarTitle("Categories");
        activity.toggleBackArrow(false); //THIS SHOULD NEVER REALLY BE NEEDED, BUT JUST IN CASE

        //SET UP RECYCLER VIEW
        RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        int[] keys = {2,0};
        adapter = new Recycler_Adapter(getActivity(), keys);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(llm);
        myRecycler.setAdapter(adapter);

        setHasOptionsMenu(true);

        loadData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add:
                addCategoryDialog(false, getString(R.string.dialog_addcat_title), "", getString(R.string.add));
                break;
            case R.id.menu_edit:
                Log.i(TAG, "I HAVE BEEN PRESSED");
                updateCategoryDialog();
                break;
            case R.id.menu_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        SitesActivity activity = (SitesActivity) getActivity();
        if (db_activity_name == null)db_activity_name = activity.DB_ACTIVITY_NAME;
        activity.loadSitesFromDB();
        //GET CAT LIST
        catNames = activity.getCategories();
        catIds = activity.getRowIds();

        adapter.updateAdapter(catNames, catIds);
    }

    /* DIALOGS */
    //DISPLAY DIALOG FOR ADD/UPDATE CATEGORY
    public void addCategoryDialog(final boolean update, String title, String input, String posButton){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input("",input, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input != null && input.length() > 0){
                            //IF NOT NULL, ADD TO SQLITE DB
                            if (update){
                                //UPDATE CURRENT CATEGORY
                                updateCategory(input.toString());
                            } else {
                                saveCategory(input.toString());
                            }
                        }
                    }
                })
                .positiveText(posButton)
                .negativeText(R.string.cancel)
                .cancelable(false)
                .show();
    }

    //DISPLAY DIALOG LIST OF CATEGORIES TO UPDATE
    private void updateCategoryDialog(){
        new MaterialDialog.Builder(getActivity())
                .title("List it")
                .items(catNames)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Log.i(TAG, "I WAS PRESSED: " + catNames.get(position));
                        String title = getString(R.string.dialog_update_title)+" " + text.toString() +" "+getString(R.string.dialog_update_title2);
                        rowID = catIds.get(position);
                        addCategoryDialog(true, title, "", getString(R.string.dialog_update_posbutton));
                    }
                })
                .show();
    }






    /* DB CALLS */
    //SAVE NEW CATEGORY TO DB
    private void saveCategory(String catName){
        Entry_Object object = createObject(catName);
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.addNewEntry(object); //AUTO CLOSES DB

        //UPDATE LOCAL DATA
        loadData();

        //NOW LAUNCH SITES LIST FRAGMENT INTO NEWLY CREATED CATEGORY
        SitesActivity activity = (SitesActivity) getActivity();
        activity.fragController(activity.sites, catName, catIds.get(catIds.size()-1)); //ENSURE YOU OPEN A SITES FRAGMENT

    }
    //UPDATE EXISTING CATEGORY TO DB
    private void updateCategory(String catName){
        Entry_Object object = createObject(catName);
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.updateEntry(rowID, object);
        loadData();
    }

    //CREATE OBJECT
    private Entry_Object createObject(String catName){
        Entry_Object object = new Entry_Object();
        object.setEntryDate(new Date());
        object.setEntryActivity(db_activity_name);
        object.setCatID(-1); //-1 FOR ALL CATEGORIES
        object.setEntryName(catName); //CATEGORY KEY, HELPS DIFFERENTIATE FROM USER SITES
        return object;
    }

//    /* UPDATE UI */
//    //UPDATE ADAPTER AND UI
//    private void updateAdapter(){
//        SitesActivity activity = (SitesActivity) getActivity();
//        //REFRESH LIST
//        activity.loadFlashCardsFromDB();
//        //UPDATE LOCAL VARIABLES AND RECYCLER VIEW
//        catNames = activity.getCategories();
//        catIds = activity.getRowIds();
//        adapter.updateAdapter(catNames, catIds);
//    }



}
