package me.zjc.swfu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class HomeViewPagerAdt extends FragmentStatePagerAdapter {

    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    public HomeViewPagerAdt(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
        super(fm);
        this.mTitleList = titleList;
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
