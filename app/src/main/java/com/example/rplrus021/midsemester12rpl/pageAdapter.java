package com.example.rplrus021.midsemester12rpl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageAdapter extends FragmentStatePagerAdapter {
    int tab;

    public pageAdapter(FragmentManager fm, int tab) {
        super(fm);
        this.tab = tab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                now_playing nowPlaying = new now_playing();
                return nowPlaying;
            case 1:
                upcoming upcoming = new upcoming();
                return upcoming;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tab;
    }
}
