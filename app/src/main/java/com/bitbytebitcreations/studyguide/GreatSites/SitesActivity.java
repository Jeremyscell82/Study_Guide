package com.bitbytebitcreations.studyguide.GreatSites;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bitbytebitcreations.studyguide.GreatSites.Fragments.Category_Fragment;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.bitbytebitcreations.studyguide.R;

/**
 * Created by JeremysMac on 1/17/17.
 */

public class SitesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");//REMOVE ACTIONBAR TITLE
        toolbar.setTitle("Select Category");

        //SET UP NAV DRAWER
        new Material_Drawer().navDrawer(this, toolbar);

        //LAUNCH FRAGMENT
        fragController(0);


    }

    //FRAGMENT CONTROLLER
    private void fragController(int position){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (position){
                case 0:
                    Category_Fragment fragment = new Category_Fragment().newIntance();
                    ft.replace(R.id.main_container, fragment)
                            .commit();
                    break;
                case 1:
                    break;
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add:
                break;
            case R.id.menu_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
