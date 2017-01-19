package com.bitbytebitcreations.studyguide.Definitions.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Recycler_Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVITY 3, DEFINITIONS ACTIVITY
 * FRAGMENT 0, HOME
 * Created by JeremysMac on 1/18/17.
 */

public class DefList_Fragment extends Fragment{

    private final String TAG = "DEFLIST_FRAGMENT";
    private String db_activity_name;
    List<String> defList;
    Recycler_Adapter adapter;

    public DefList_Fragment newInstance(){
        return new DefList_Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //SET UP TOOLBAR
        DefinitionsActivity activity = (DefinitionsActivity) getActivity();
        activity.setToolbarTitle("Definitions");
        activity.toggleBackArrow(false); //THIS SHOULD NEVER REALLY BE NEEDED, BUT JUST IN CASE


        //SET UP RECYCLER VIEW
        RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        int[] keys = {3,0};
        adapter = new Recycler_Adapter(getActivity(), keys);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(llm);
        myRecycler.setAdapter(adapter);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(TAG, "SEARCH BUTTON PRESSED");
        return super.onOptionsItemSelected(item);
    }

    //LOAD DATA
    private void loadData(){
        DefinitionsActivity activity = (DefinitionsActivity) getActivity();
        db_activity_name = activity.DB_ACTIVITY_NAME;
        defList = new ArrayList<>();
        defList.add("Test");
        adapter.updateAdapter(defList, null);
    }


}
