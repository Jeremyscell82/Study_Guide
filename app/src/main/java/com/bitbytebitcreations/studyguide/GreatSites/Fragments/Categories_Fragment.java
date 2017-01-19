package com.bitbytebitcreations.studyguide.GreatSites.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
    List<String> catList;
    Recycler_Adapter adapter;

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

        loadData(false);

        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add:
                addCategoryDialog("");
                break;
            case R.id.menu_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(boolean refresh){
        SitesActivity activity = (SitesActivity) getActivity();
        db_activity_name = activity.DB_ACTIVITY_NAME;
        if (refresh)activity.loadSitesFromDB();
        //GET CAT LIST
        catList = activity.getCategories();
        adapter.updateAdapter(catList);
    }


    public void addCategoryDialog(String input){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_addcat_title)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE)
                .input("",input, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input != null && input.length() > 0){
                            //IF NOT NULL, ADD TO SQLITE DB
                            saveCategory(input.toString());
                        }
                    }
                })
                .positiveText(R.string.add)
                .negativeText(R.string.cancel)
                .cancelable(false)
                .show();
    }

    private void saveCategory(String catName){
        Entry_Object object = new Entry_Object();
        object.setEntryDate(new Date());
        object.setEntryActivity(db_activity_name);
        object.setEntryCategory(catName);
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.addNewEntry(object); //AUTO CLOSES DB


        SitesActivity activity = (SitesActivity) getActivity();
        //REFRESH LIST
        activity.loadSitesFromDB();
        //UPDATE RECYCLERVIEW
        adapter.updateAdapter(activity.getCategories());
        //LAUNCH SITES FRAGMENT INTO NEWLY CREATED CATEGORY
        activity.fragController(activity.sites, catName);


    }

}
