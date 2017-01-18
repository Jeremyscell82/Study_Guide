package com.bitbytebitcreations.studyguide.GreatSites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bitbytebitcreations.studyguide.Material_Drawer;
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
