package me.zjc.swfu.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.netWork.client.PublicNotifyClient;
import me.zjc.swfu.netWork.response.NotifyDetail;
import me.zjc.swfu.presenter.MainPresenter;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IMainView;
import me.zjc.swfu.widget.Fragment.ActiveFragment;
import me.zjc.swfu.widget.Fragment.HomeFragment;
import me.zjc.swfu.widget.Fragment.InfoQueryFragment;
import me.zjc.swfu.widget.Fragment.PublicInfoFragment;
import me.zjc.swfu.widget.Fragment.TeachEvaluateFragment;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int START_MYCENTER_REQUEST_CODE = 123;

    private DrawerLayout drawer;
    private NavigationView navView;
    private TabLayout tabLayout;
    private LinearLayout navHeader;
    private MainPresenter presenter;
    private ProgressBar mProgressBar;
    private FragmentTransaction transaction;

    private HomeFragment homeFragment;
    private ActiveFragment activeFragment;
    private InfoQueryFragment infoQueryFragment;
    private PublicInfoFragment publicInfoFragment;
    private TeachEvaluateFragment teachEvaluateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigationView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = (LinearLayout) navView.getHeaderView(0);
        mProgressBar = (ProgressBar) findViewById(R.id.nomal_progressBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    protected void setEvent() {
    }

    @Override
    protected void initData() {
        presenter = new MainPresenter(this);
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
    }

    @Override
    protected void initView() {
        presenter.initNavView();//设置抽屉导航的头部
        setTitle(getResString(R.string.main_activity_lab));
        if (homeFragment == null) {
            homeFragment = new HomeFragment(tabLayout);
        }
        transaction.add(R.id.fragment_container, homeFragment, HomeFragment.TAG)
                .commit();
        tabLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化右侧导航栏
     */
    private void initNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            exitSystem();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            presenter.logout();
            return true;
        } else if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment(tabLayout);
            }
            transaction.replace(R.id.fragment_container, homeFragment, HomeFragment.TAG);
            tabLayout.setVisibility(View.VISIBLE);
            setTitle(getResString(R.string.draw_item_home));
        } else if (id == R.id.nav_active) {
            if (activeFragment == null) {
                activeFragment = new ActiveFragment();
            }
            transaction.replace(R.id.fragment_container, activeFragment, ActiveFragment.TAG);
            tabLayout.setVisibility(View.GONE);
            setTitle(getResString(R.string.draw_item_active));
        } else if (id == R.id.nav_informationQur) {
            if (infoQueryFragment == null) {
                infoQueryFragment = new InfoQueryFragment();
            }
            transaction.replace(R.id.fragment_container, infoQueryFragment, InfoQueryFragment.TAG);
            tabLayout.setVisibility(View.GONE);
            setTitle(getResString(R.string.draw_item_informationQur));
        } else if (id == R.id.nav_publicInfo) {
            if (publicInfoFragment == null) {
                publicInfoFragment = new PublicInfoFragment();
            }
            transaction.replace(R.id.fragment_container, publicInfoFragment, PublicInfoFragment.TAG);
            tabLayout.setVisibility(View.GONE);
            setTitle(getResString(R.string.draw_item_publicInfo));
        } else if (id == R.id.nav_teachEvaluate) {
            if (teachEvaluateFragment == null) {
                teachEvaluateFragment = new TeachEvaluateFragment();
            }
            transaction.replace(R.id.fragment_container, teachEvaluateFragment, TeachEvaluateFragment.TAG);
            tabLayout.setVisibility(View.GONE);
            setTitle(getResString(R.string.draw_item_teachEvaluate));
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void initNavView(String headerUrl, String name, String dec) {
        LogUtil.e(TAG, "头像链接：" + headerUrl);
        CircleImageView ivHeader = (CircleImageView) navHeader.findViewById(R.id.iv_header);
        TextView tvName = (TextView) navHeader.findViewById(R.id.tv_userName);
        TextView tvDec = (TextView) navHeader.findViewById(R.id.tv_userDec);
        if (headerUrl == null || "".equals(headerUrl)) {
            ivHeader.setImageResource(R.mipmap.header_default);
        } else {
            Picasso.with(this)
                    .load(headerUrl)
                    .error(R.mipmap.header_default)
                    .into(ivHeader);
        }
        tvName.setText(name);
        tvDec.setText(dec);
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyCenterActivity.class);
                startActivityForResult(intent, START_MYCENTER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_MYCENTER_REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.initNavView();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawer.closeDrawer(navView);
    }

    private void test() {
        PublicNotifyClient client = PublicNotifyClient.getInstance();

        client.queryNotifyDetail("x394UUUY")
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NotifyDetail>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("test", "completed !");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("test", "error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(NotifyDetail notifyDetail) {
                        LogUtil.e("test", notifyDetail.toString());
                    }
                });
    }
}
