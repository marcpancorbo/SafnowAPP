package com.example.safnow;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs = 3;

    public PageAdapter(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("marc","NumOfTabs" + position);
        switch (position) {
            case 0:
                return  new Contact();
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
                return  "Settings";
            case 1:
                return "Map";
            case 2:
                return "Contacts";
            default:
                return null;
        }
    }
}
