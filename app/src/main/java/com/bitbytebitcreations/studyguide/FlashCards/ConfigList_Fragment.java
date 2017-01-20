package com.bitbytebitcreations.studyguide.FlashCards;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.FlashCards.FlashUtils.FlashConfig_Adapter;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;
import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

/**
 * Created by JeremysMac on 1/20/17.
 */

public class ConfigList_Fragment extends Fragment {

    private final String TAG = "CONFIG_FRAG";
    List<Entry_Object> masterList;
    Flash_Activity activity;
    FloatingActionButton fab;
    boolean alertShown = false;
    FlashConfig_Adapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //SET VARIABLES
        activity = (Flash_Activity) getActivity();

        //SET TOOLABR TITLE
        TextView toolbarTitle = (TextView) activity.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Definitions");

        //CONFIGURE TOOLBAR
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                return true;
            }
        });

        //SET MENU
        setHasOptionsMenu(true);

        //SET UP RECYCLER VIEW
        RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        int[] keys = {3,0};
        adapter = new FlashConfig_Adapter();
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(llm);
        myRecycler.setAdapter(adapter);

        //CONFIGURE FLOATING ACTION BUTTON, ONCE EVERYTHING IS SETTLED IN
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.toggleFab(true);
            }
        }, 500);



        Log.i(TAG, "fab has been set up");

        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            if (bundle.getBoolean("getMaster")){
                Log.i(TAG, "WE HAVE DATA");
//                masterList = activity.masterList;
                loadData();
            }
        }

        loadData();


        return view;
    }



    /* MENU*/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_flash_config, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_timer:
                Log.i(TAG, "TIMER CONFIG PRESSED");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        activity.loadFlashCardsFromDB();
        //GET MASTERLIST
        masterList = activity.masterList;
        //UPDATE RECYCELRVIEW
        adapter.updateAdapter(getActivity(), masterList);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.toggleBackArrow(false);
        activity.toggleFab(false);
    }
}
