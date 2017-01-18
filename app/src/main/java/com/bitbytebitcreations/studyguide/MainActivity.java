package com.bitbytebitcreations.studyguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN-ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");//REMOVE ACTIONBAR TITLE
        toolbar.setTitle("HI There!!");

        //SET UP NAV DRAWER
        new Material_Drawer().navDrawer(this, toolbar);
    }




}
