package com.sragen.sedekahku.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sragen.sedekahku.fragment.InfaqFragment;
import com.sragen.sedekahku.fragment.LelangFragment;
import com.sragen.sedekahku.fragment.MakananFragment;

/**
 * Created by abdalla on 2/18/18.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MakananFragment();
            case 1:
                return new InfaqFragment();
            case 2:
                return new LelangFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}