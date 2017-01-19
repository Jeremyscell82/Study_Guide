package com.bitbytebitcreations.studyguide.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.MainActivity;
import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by JeremysMac on 1/17/17.
 */

public class Material_Drawer {

    Activity mActivity;
    Drawer mDrawer;
    int mPosition;


    public Drawer navDrawer(Activity activity, Toolbar toolbar){
        String[] navItems = activity.getResources().getStringArray(R.array.nav_drawer_items);
        mActivity = activity;
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.primary)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(navItems[0]), //HOME
                        new PrimaryDrawerItem().withName(navItems[1]), //FLASH CARDS
                        new PrimaryDrawerItem().withName(navItems[2]), //GREAT SITES
                        new PrimaryDrawerItem().withName(navItems[3]), //DEFINITIONS
                        new PrimaryDrawerItem().withName(navItems[4]) //ABOUT
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mDrawer.closeDrawer();
                        mPosition = position;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activityLauncher(mPosition);
                            }
                        }, 400);

                        return false;
                    }
                })
                .withSelectedItem(-1)
                .build();
        return mDrawer;
    }

    private void activityLauncher(int position){
        Intent mIntent = null;
        switch (position){
            case 1: //HOME
                mIntent = new Intent(mActivity, MainActivity.class);
                break;
            case 2: //FLASH CARDS
                break;
            case 3: //GREAT SITES
                mIntent = new Intent(mActivity, SitesActivity.class);
                break;
            case 4: //DEFINITIONS
                break;
            case 5: //ABOUT
                break;
        }
        if (mIntent != null){
            mDrawer.setSelectionAtPosition(-1);
            mActivity.startActivity(mIntent);
        }

    }

}
