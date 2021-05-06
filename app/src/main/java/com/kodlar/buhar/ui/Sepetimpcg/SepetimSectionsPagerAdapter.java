package com.kodlar.buhar.ui.Sepetimpcg;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.KisiselBakimpcg.AgizBakim;
import com.kodlar.buhar.ui.KisiselBakimpcg.CiltBakim;
import com.kodlar.buhar.ui.KisiselBakimpcg.SacBakim;

public class SepetimSectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_sepet};
    private final Context mContext;

    public SepetimSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Sepet();
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
        return 1;
    }
}