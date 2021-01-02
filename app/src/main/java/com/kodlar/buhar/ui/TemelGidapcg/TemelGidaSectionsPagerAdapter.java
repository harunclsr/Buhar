package com.kodlar.buhar.ui.TemelGidapcg;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.manavpckg.Meyveler;
import com.kodlar.buhar.ui.manavpckg.Sebzeler;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TemelGidaSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_TemelGida_MakarnaBakliyat, R.string.tab_TemelGida_Yag,R.string.tab_TemelGida_TuzSekerBaharat};
    private final Context mContext;

    public TemelGidaSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= null;
        switch (position){

            case 0:
                fragment = new MakarnaBakliyat();
                break;
            case 1:
                fragment= new Yag();
                break;
            case 2:
                fragment= new TuzSekerBaharat();
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