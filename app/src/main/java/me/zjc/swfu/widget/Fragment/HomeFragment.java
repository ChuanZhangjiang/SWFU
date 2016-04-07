package me.zjc.swfu.widget.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.HomeViewPagerAdt;
import me.zjc.swfu.base.BaseFragment;

/**
 * Created by ChuanZhangjiang on 2016/1/17.
 */
public class HomeFragment extends BaseFragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private List<Fragment> pagerList;
    private List<String> titleList;

    private ViewPager mHomeViewPager;
    private TabLayout mTabLayout;

    public HomeFragment(TabLayout tabLayout) {
        this.mTabLayout = tabLayout;
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void findView(View rootView) {
        mHomeViewPager = (ViewPager) rootView.findViewById(R.id.viewPager_home);
    }

    @Override
    protected void initData() {
        pagerList = new ArrayList<>();
        titleList = new ArrayList<>();

        PublicNotifyFragment publicNotifyFragment = new PublicNotifyFragment();
        CourseTableFragment courseTableFragment = new CourseTableFragment();
        pagerList.add(publicNotifyFragment);
        pagerList.add(courseTableFragment);
        titleList.add("公告");
        titleList.add("课表");
    }

    @Override
    protected void initView() {
        if (mHomeViewPager.getAdapter() == null) {
            HomeViewPagerAdt adapter = new HomeViewPagerAdt(getFragmentManager(), titleList, pagerList);
            mHomeViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mHomeViewPager);
        } else {
            mHomeViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void setEvent() {

    }
}
