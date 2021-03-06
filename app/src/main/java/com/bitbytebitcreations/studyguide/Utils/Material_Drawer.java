package com.bitbytebitcreations.studyguide.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.FlashCards.Flash_Activity;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.MainActivity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Settings.Settings_Activity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
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
                .withHeaderBackground(R.drawable.nav_drawer_background).withHeaderBackgroundScaleType(ImageView.ScaleType.FIT_XY)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(navItems[0]).withIcon(R.drawable.ic_action_home_nav), //HOME
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(navItems[1]).withIcon(R.drawable.ic_action_flashcard), //FLASH CARDS
                        new PrimaryDrawerItem().withName(navItems[2]).withIcon(R.drawable.ic_action_greatsites), //GREAT SITES
                        new PrimaryDrawerItem().withName(navItems[3]).withIcon(R.drawable.ic_action_definition), //DEFINITIONS
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(navItems[4]).withIcon(R.drawable.ic_action_settings) //SETTINGS
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
                        }, 300);

                        return false;
                    }
                })
                .withSelectedItem(-1)
                .withSliderBackgroundColor(mActivity.getResources().getColor(R.color.background))
                .build();
        return mDrawer;
    }

    private void activityLauncher(int position){
        Intent mIntent = null;
        switch (position){
            case 1: //HOME
                mIntent = new Intent(mActivity, MainActivity.class);
                break;
            case 3: //FLASH CARDS
                mIntent = new Intent(mActivity, Flash_Activity.class);
                break;
            case 4: //GREAT SITES
                mIntent = new Intent(mActivity, SitesActivity.class);
                break;
            case 5: //DEFINITIONS
                mIntent = new Intent(mActivity, DefinitionsActivity.class);
                break;
            case 7: //SETTINGS
                mIntent = new Intent(mActivity, Settings_Activity.class);
                mDrawer.setSelectionAtPosition(-1);
                mActivity.startActivity(mIntent);
                mIntent = null;
                break;
        }
        if (mIntent != null){
            mDrawer.setSelectionAtPosition(-1);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(mIntent);
            mActivity.finish();

        }

    }

}
