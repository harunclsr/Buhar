package com.kodlar.buhar.ui.atistirmalikpcg;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.iceceklerpcg.Gazliicecek;
import com.kodlar.buhar.ui.iceceklerpcg.Gazsizicecek;
import com.kodlar.buhar.ui.iceceklerpcg.Su;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AtistirmalikSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_atistimalik_Cikolata, R.string.tab_atistimalik_Cips,R.string.tab_atistimalik_Kuruyemis};
    private final Context mContext;

    public AtistirmalikSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= null;
        switch (position){
            case 0:
                fragment = new Cikolata();
                break;
            case 1:
                fragment= new Cips();
                break;

            case 2:
                fragment = new Kuruyemis();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return 3;
    }
}