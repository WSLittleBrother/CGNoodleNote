package com.example.a99351.cgnoodlenote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.a99351.cgnoodlenote.base.BaseActivity;
import com.example.a99351.cgnoodlenote.ui.day.DayFragment;
import com.example.a99351.cgnoodlenote.ui.month.MonthFragment;
import com.example.a99351.cgnoodlenote.ui.personinfo.PersonInfoActivity;
import com.example.a99351.cgnoodlenote.ui.week.WeekFragment;
import com.example.a99351.cgnoodlenote.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private String[]mTitles = {"日","周","月"};
    //Toolbar
    private Toolbar toolbar;
    //当前的Fragment
    private int currentFragmentPosition;
    //Fragment的数组
    private List<Fragment> fragmentList;
    //Fragment声明
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;


    private FragmentPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            PhotoUtils.takePhoto(MainActivity.this,AppConstant.OPEN_PHOTO);
        } else if (id == R.id.nav_gallery) {
            PhotoUtils.openPhotoAlbum(MainActivity.this,AppConstant.OPEN_PHOTO_ALBUM);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("费用记录");
//        getSupportActionBar().setSubtitle("每一天都是新的");
    }

    @Override
    protected void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "明天的生意很好", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        ImageView headImage = (ImageView) headerView.findViewById(R.id.imageView);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(PersonInfoActivity.class);
            }
        });

        fragmentList = new ArrayList<>();
        //初始化TabLayout
        for (String title:mTitles){
            tablayout.addTab(tablayout.newTab().setText(title));
        }

        fragmentList.add(DayFragment.getInstance());
        fragmentList.add(WeekFragment.getInstance());
        fragmentList.add(MonthFragment.getInstance());

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        };

        //ViewPager的adapter
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

    }


    //初始化Fragment
    private void initFragment() {
        currentFragmentPosition = 0;

    }

    //Fragment的转换方法
    private void switchFragment(int position) {
        viewpager.setCurrentItem(position);
//        switch (position) {
//            case 0:
//                viewpager.setCurrentItem(0);
//                break;
//            case 1:
//                viewpager.setCurrentItem(1);
//                break;
//            case 2:
//                viewpager.setCurrentItem(2);
//                break;
//        }

    }


}
