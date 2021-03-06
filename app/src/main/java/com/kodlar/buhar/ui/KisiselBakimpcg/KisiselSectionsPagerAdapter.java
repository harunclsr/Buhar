package com.kodlar.buhar.ui.KisiselBakimpcg;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.Sepetimpcg.Sepet;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class KisiselSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_kisiselbakım_Agiz, R.string.tab_kisiselbakım_Cilt,R.string.tab_kisiselbakım_Sac};
    private final Context mContext;

    public KisiselSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CiltBakim();
                break;
            case 1:
                fragment = new SacBakim();
                break;

            case 2:
                fragment = new AgizBakim();
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
        // Show 2 total pages.
        return 3;
    }
}