package com.bitbytebitcreations.studyguide.Settings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Material_Drawer;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


/**
 * Created by JeremysMac on 1/20/17.
 */

public class Settings_Activity extends AppCompatActivity {

    private final String TAG = "SETTINGS";
    TextView toolbarTitle;
    Drawer drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");//REMOVE ACTIONBAR TITLE
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Settings");


        //SET UP NAV DRAWER
        drawer = new Material_Drawer().navDrawer(this, toolbar);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                onBackPressed();
                return true;
            }
        });

        //LAUNCH FRAGMENT BELOW
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Settings_Fragment fragment = new Settings_Fragment();
        ft.replace(R.id.main_container, fragment)
                .commit();

    }
/* ============================================= INNER FRAGMENT ============================================= */
    public static class Settings_Fragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_settings, container, false);

            return view;
        }
    }
}
