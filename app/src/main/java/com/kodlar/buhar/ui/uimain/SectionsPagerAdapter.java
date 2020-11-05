package com.kodlar.buhar.ui.uimain;

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
import com.kodlar.buhar.ui.iceceklerpcg.icecekler2;
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    icecekler2 i2 = new icecekler2();
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
      Fragment fragment= null;
      switch (position){
          case 0:
              fragment = new Su();
              break;
          case 1:
              fragment= new Gazliicecek();
                break;

              case 2:
              fragment=new Gazsizicecek();
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