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
import android.webkit.URLUtil;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.DB_Controller;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Recycler_Adapter;
import com.mikepenz.materialdrawer.Drawer;

import java.util.Date;
import java.util.List;

/**
 * FRAGMENT 1, SITES
 * Created by JeremysMac on 1/17/17.
 */

public class SitesList_Fragment extends Fragment {

    private final String TAG = "SITES";
    Recycler_Adapter adapter;
    private String siteName;
    private String category;
    private String db_activity_name;
    List<String> siteList;
    List<String> urlList;
    boolean isInEditMode = false;



    public SitesList_Fragment newInstance(){
        return new SitesList_Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //GET BUNDLE PASSED IN
        Bundle bundle = getArguments();
        if (bundle != null){
            String item = bundle.getString("item");
            category = item;
        }

        //SET UP TOOLBAR
        SitesActivity activity = (SitesActivity) getActivity();
        activity.setToolbarTitle(category);
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                return true;
            }
        });

        //SET UP RECYCLER VIEW
        RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        int[] keys = {2,1};
        adapter = new Recycler_Adapter(getActivity(), keys);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(llm);
        myRecycler.setAdapter(adapter);

        setHasOptionsMenu(true);

        //LOAD DATA
        loadAdapter();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add:
                addSiteDialog(0,getString(R.string.dialog_addsite_title), "");
                break;
            case R.id.menu_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //LOAD DATA
    private void loadAdapter(){
        SitesActivity activity = (SitesActivity) getActivity();
        //GET LIST OF SITES
        siteList = activity.getSites(category);
        //GET LIST OF URLS
        urlList = activity.getURLs();
        //GET NAME OF ACTIVITY
        if (db_activity_name == null)db_activity_name = activity.DB_ACTIVITY_NAME;
        //UPDATE THE ADAPTER
        adapter.updateAdapter(siteList);
    }


    public void addSiteDialog(final int round, String title, String input){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE)
                .input("",input, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input != null && input.length() > 0){
                            if (round == 0){
                                //SITE NAME HAS BEEN ENTERED
                                siteName = input.toString();
                                addSiteDialog(1, getString(R.string.dialog_addsite_title2), "");
                            } else {
                                //URL HAS BEEN ENTERED, CHECK IF ITS A PROPER FORMAT
                                if (URLUtil.isValidUrl(input.toString())){
                                    Log.i(TAG, "URL HAS BEEN COLLECTED "+siteName+" url: " + input.toString());
                                    saveSite(input.toString());
                                } else {
                                    //URL WAS NOT VALID
                                    Toast.makeText(getActivity(), getString(R.string.toast_invalid_url), Toast.LENGTH_LONG).show();
                                    addSiteDialog(1, getString(R.string.dialog_addsite_title2), input.toString());
                                }
                            }

                        }
                    }
                })
                .positiveText(R.string.add)
                .negativeText(R.string.cancel)
                .cancelable(false)
                .show();
    }

    //SAVE ENTRY
    private void saveSite(String url){
        Entry_Object object = new Entry_Object();
        object.setEntryDate(new Date());
        object.setEntryActivity(db_activity_name);
        object.setEntryCategory(category);
        object.setEntryName(siteName);
        object.setEntryContent(url);
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.addNewEntry(object); //AUTO CLOSES DB
        //UPDATE DATA
        SitesActivity activity = (SitesActivity) getActivity();
        activity.loadSitesFromDB();
        //UPDATE CURRENT ADAPTER
        loadAdapter();
    }
}
