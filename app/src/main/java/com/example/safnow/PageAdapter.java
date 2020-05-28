package com.example.safnow;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs = 3;
    private Context context;
    public PageAdapter(FragmentManager fm,Context context){
        super(fm);
        this.context=context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("marc","NumOfTabs" + position);
        switch (position) {
            case 0:
                return new SettingsActivity();
            case 1:
                return new MapsActivity();
            case 2:
                return new Contact();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return  context.getString(R.string.action_settings);
            case 1:
                return context.getString(R.string.title_activity_maps);
            case 2:
                return context.getString(R.string.contacts);
            default:
                return null;
        }
    }
}
