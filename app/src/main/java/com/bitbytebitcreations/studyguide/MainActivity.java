package com.bitbytebitcreations.studyguide;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

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

        Toast.makeText(this, "Not much to see on this activity\nOpen the nav drawer and see the other activities", Toast.LENGTH_LONG).show();
        launchPlaceholder();
    }


    private void launchPlaceholder(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Placeholder_Fragment fragment = new Placeholder_Fragment();
        ft.add(R.id.main_container, fragment)
                .commit();
    }


}
