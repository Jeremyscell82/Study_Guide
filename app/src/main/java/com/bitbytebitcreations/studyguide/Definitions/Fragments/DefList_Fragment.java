package com.bitbytebitcreations.studyguide.Definitions.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.bitbytebitcreations.studyguide.Utils.Recycler_Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVITY 3, DEFINITIONS ACTIVITY
 * FRAGMENT 0, HOME
 * Created by JeremysMac on 1/18/17.
 */

public class DefList_Fragment extends Fragment implements SearchView.OnQueryTextListener {

    private final String TAG = "DEFLIST_FRAGMENT";
    private String db_activity_name;
    List<String> defNames;
//    List<String> definitions;
    List<Long> rowIds;
    Recycler_Adapter adapter;
    RecyclerView recycler;
    DefinitionsActivity activity;

    public DefList_Fragment newInstance(){
        return new DefList_Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //SET UP TOOLBAR
        activity = (DefinitionsActivity) getActivity();
        activity.setToolbarTitle("Definitions");
        activity.toggleBackArrow(false); //THIS SHOULD NEVER REALLY BE NEEDED, BUT JUST IN CASE


        //SET UP RECYCLER VIEW
        recycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        int[] keys = {3,0};
        adapter = new Recycler_Adapter(getActivity(), keys);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);
        recycler.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        //CONTROL THE MENU
        setHasOptionsMenu(true);


        //LOAD DATA
        loadData();


        return view;
    }


    /* MENU*/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.menu_search));
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setToolbarTitle("");
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                activity.setToolbarTitle("Definitions");
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //NO LONGER GETS TRIGGERED
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "QUERY HAS BEEN MADE..." + newText);
        adapter.queryAdapter(true, newText);
        return false;
    }


    //LOAD DATA
    private void loadData(){
        DefinitionsActivity activity = (DefinitionsActivity) getActivity();
        if (db_activity_name == null)db_activity_name = activity.DB_ACTIVITY_NAME;
        //REFRESH DEFINITIONS FROM DB
        activity.loadDataFromDB();
        //GET DEFINITIONS
        defNames = activity.getDefNames();
        rowIds = activity.getRowIds();
        List<String> definitions = activity.getDefinitions();
        adapter.updateAdapter(defNames, definitions, rowIds);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "FRAGMENT HAS BEEN RESTORED");
    }



    /* PERSONAL ASYNC TASK FOR DEFINITIONS */
    private class LoadFromDB extends AsyncTask<Object, Object, Void> {


        @Override
        protected Void doInBackground(Object... objects) {
            List<Entry_Object> list = new ArrayList<>();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
