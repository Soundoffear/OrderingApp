package com.ejoapps.m2d2_sub.orderingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ejoapps.m2d2_sub.orderingapp.fragments.CouponsFragment;
import com.ejoapps.m2d2_sub.orderingapp.fragments.FirstPageFragment;
import com.ejoapps.m2d2_sub.orderingapp.fragments.UserDataFragment;

public class MainScreenNavDrawer extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] itemsTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_nav_drawer);

        mDrawerLayout = findViewById(R.id.main_screen_drawer_layout);
        mDrawerList = findViewById(R.id.main_screen_list_view);

        itemsTitles = getResources().getStringArray(R.array.navigation_items);
        mTitle = mDrawerTitle = getTitle();

        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.nav_drawer_list_item, itemsTitles));
        mDrawerList.setOnItemClickListener(new NaviDrawerMainScreen());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.DRAWER_OPEN,
                R.string.DRAWER_CLOSE
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NaviDrawerMainScreen implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment newFragment = null;
        switch (position) {
            case 0:
                newFragment = new FirstPageFragment();
                break;
            case 1:
                // still needs current order status
                break;
            case 2:
                newFragment = new CouponsFragment();
                break;
            case 3:
                // still needs previous orders list
                break;
            case 4:
                newFragment = new UserDataFragment();
                break;
            default:
                newFragment = new FirstPageFragment();

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(newFragment != null) {
            fragmentManager.beginTransaction().replace(R.id.main_screen_primary_fragment, newFragment).commit();
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(itemsTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
