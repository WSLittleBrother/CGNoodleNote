package com.example.a99351.cgnoodlenote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a99351.cgnoodlenote.base.ActivityManager;
import com.example.a99351.cgnoodlenote.base.BaseActivity;
import com.example.a99351.cgnoodlenote.ui.addvariety.AddVarietyActivity;
import com.example.a99351.cgnoodlenote.ui.home.view.BalanceFragment;
import com.example.a99351.cgnoodlenote.ui.home.view.ChargeFragment;
import com.example.a99351.cgnoodlenote.ui.home.view.OrderFragment;
import com.example.a99351.cgnoodlenote.ui.personinfo.PersonInfoActivity;
import com.example.a99351.cgnoodlenote.utils.PhotoUtilsPPP;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.rb_charge)
    RadioButton rbCharge;
    @Bind(R.id.rb_order)
    RadioButton rbOrder;
    @Bind(R.id.rb_balance)
    RadioButton rbBalance;
    @Bind(R.id.rg_home)
    RadioGroup rgHome;
    @Bind(R.id.fl_body)
    FrameLayout flBody;
    //Toolbar
    private Toolbar toolbar;

    private ChargeFragment chargeFragment;
    private OrderFragment orderFragment;
    private BalanceFragment balanceFragment;

    //当前的Fragment
    private int currentFragmentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_charge:
                        switchFragment(0);
                        break;

                    case R.id.rb_order:
                        switchFragment(1);
                        break;

                    case R.id.rb_balance:
                        switchFragment(2);
                        break;
                }
            }
        });
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
            PhotoUtilsPPP.takePhoto(MainActivity.this, AppConstant.OPEN_PHOTO);
        } else if (id == R.id.nav_gallery) {
            PhotoUtilsPPP.openPhotoAlbum(MainActivity.this, AppConstant.OPEN_PHOTO_ALBUM);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_variety) {
                startActivity(AddVarietyActivity.class);
        } else if (id == R.id.nav_eye) {

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
        toolbar.setTitle("每天都是新的");
        setSupportActionBar(toolbar);
//        toolbar.setSubtitle("每一天都是新的");
    }

    @Override
    protected void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "是否添加产品?", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
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
    }

    //初始化Fragment
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentFragmentPosition = 0;
        if (savedInstanceState != null) {
            chargeFragment = (ChargeFragment) getSupportFragmentManager().findFragmentByTag("chargeFragment");
            orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentByTag("orderFragment");
            balanceFragment = (BalanceFragment) getSupportFragmentManager().findFragmentByTag("balanceFragment");
            currentFragmentPosition = savedInstanceState.getInt("HOME_CURRENT_TAB_POSITION");
        } else {
            chargeFragment = new ChargeFragment();
            orderFragment = new OrderFragment();
            balanceFragment = new BalanceFragment();
            transaction.add(R.id.fl_body,chargeFragment,"chargeFragment" );
            transaction.add(R.id.fl_body,orderFragment,"orderFragment" );
            transaction.add(R.id.fl_body,balanceFragment,"balanceFragment" );
        }
        transaction.commit();
        switchFragment(currentFragmentPosition);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 切换
     */
    private void switchFragment(int position) {
        currentFragmentPosition =position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(orderFragment);
                transaction.hide(balanceFragment);
                transaction.show(chargeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(chargeFragment);
                transaction.hide(balanceFragment);
                transaction.show(orderFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(orderFragment);
                transaction.hide(chargeFragment);
                transaction.show(balanceFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 双击返回键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                ActivityManager.getActivityMar().exitApp(MainActivity.this);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
