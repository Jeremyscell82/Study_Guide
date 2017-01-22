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
    private long catID;
    //FOR UPDATING ITEM
    private long rowId;
    private String url;
    private String db_activity_name;
    List<String> siteList;
    List<String> urlList;
    List<Long> rowIds;



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
            category = bundle.getString("catName");
            catID = bundle.getLong("catId");
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
        loadData();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add:
                addSiteDialog(false, 0,getString(R.string.dialog_addsite_title), "", getString(R.string.add), getString(R.string.cancel));
                break;
            case R.id.menu_edit:
                updateCategoryDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //LOAD DATA
    private void loadData(){
        SitesActivity activity = (SitesActivity) getActivity();
        activity.loadSitesFromDB();
        //GET LIST OF SITES
        siteList = activity.getSites(catID);
        //GET LIST OF URLS
        urlList = activity.getURLs();
        //GET LIST OF ROW IDS
        rowIds = activity.getRowIds();
        //GET NAME OF ACTIVITY
        if (db_activity_name == null)db_activity_name = activity.DB_ACTIVITY_NAME;
        //UPDATE THE ADAPTER
        adapter.updateAdapter(siteList, urlList, rowIds);
    }

    /* DIALOGS */
    public void addSiteDialog(final boolean update, final int round, String title, String input, final String posButton, String negButton){
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input("",input, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input != null && input.length() > 0){
                            if (round == 0){
                                if (update){/* EXISTING ENTRY */
                                    //UPDATE TEMP SITENAME
                                    siteName = input.toString();
                                    //UPDATE DB
                                    updateSite();
                                    //ASK ABOUT THE URL
                                    addSiteDialog(true, 1, getString(R.string.dialog_update_title3), url, posButton, getString(R.string.dialog_update_negbutton));
                                } else {/*NEW ENTRY*/
                                    //UPDATE TEMP SITENAME
                                    siteName = input.toString();
                                    //NOW REQUEST THE URL
                                    addSiteDialog(false, 1, getString(R.string.dialog_addsite_title2), "https://", getString(R.string.add), getString(R.string.cancel));
                                }
                            } else {
                                if (update){/* EXISTING ENTRY */
                                    //UPDATE TEMP URL
                                    url = input.toString();
                                    updateSite();
                                    Toast.makeText(getActivity(), "Site has been updated", Toast.LENGTH_LONG).show();
                                } else {/*NEW ENTRY*/
                                    //URL HAS BEEN ENTERED, CHECK IF ITS A PROPER FORMAT
                                    if (URLUtil.isValidUrl(input.toString())) {
                                        //UPDATE TEMP URL
                                        url = input.toString();
                                        //SAVE DATA TO DB
                                        saveSite();
                                        Toast.makeText(getActivity(), "Site has been saved", Toast.LENGTH_LONG).show();
                                    } else {
                                        //URL WAS NOT VALID
                                        Toast.makeText(getActivity(), getString(R.string.toast_invalid_url), Toast.LENGTH_LONG).show();
                                        //RELOAD ALERT DIALOG
                                        addSiteDialog(false, 1, getString(R.string.dialog_addsite_title2), input.toString(), getString(R.string.add), getString(R.string.cancel));
                                    }
                                }
                            }
                        }
                    }
                })
                .positiveText(posButton)
                .negativeText(negButton)
                .cancelable(false)
                .show();
    }

    //DISPLAY DIALOG LIST OF CATEGORIES TO UPDATE
    private void updateCategoryDialog(){
        new MaterialDialog.Builder(getActivity())
                .title("List it")
                .items(siteList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        //SET NEW TITLE
                        String title = getString(R.string.dialog_update_title)+" " + text.toString() +" "+getString(R.string.dialog_update_title2);
                        //DECLARE TEMP VARIABLES
                        siteName = siteList.get(position);
                        url = urlList.get(position);
                        rowId = rowIds.get(position);
                        //DISPLAY ADD DIALOG TO UPDATE SELECTED SITE
                        addSiteDialog(true, 0, title, text.toString(), getString(R.string.dialog_update_posbutton), getString(R.string.cancel));
                    }
                })
                .show();
    }


    /* DB CALLS */
    //SAVE NEW ENTRY TO DB
    private void saveSite(){
        Entry_Object object = createObject();
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.addNewEntry(object); //AUTO CLOSES DB
        //UPDATE CURRENT ADAPTER
        loadData();
    }

    private void updateSite(){
        Entry_Object object = createObject();
        //ADD TO DB
        DB_Controller controller = new DB_Controller();
        controller.DB_OPEN(getActivity());
        controller.updateEntry(rowId, object);
        loadData();
    }

    private Entry_Object createObject(){
        Entry_Object object = new Entry_Object();
        object.setEntryDate(new Date());
        object.setEntryActivity(db_activity_name);
        object.setCatID(catID);
        object.setEntryName(siteName);
        object.setEntryContent(url);
        return object;
    }



}
