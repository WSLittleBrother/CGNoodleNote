package com.example.a99351.cgnoodlenote.ui.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseFragment;
import com.example.a99351.cgnoodlenote.ui.day.DayFragment;
import com.example.a99351.cgnoodlenote.ui.month.MonthFragment;
import com.example.a99351.cgnoodlenote.ui.week.WeekFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 流水
 * Created by 99351 on 2017/11/27.
 */

public class ChargeFragment extends BaseFragment {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tablayout)
    TabLayout tablayout;

    private String[]mTitles = {"日","周","月"};
    //Fragment的数组
    private List<Fragment> fragmentList;
    //Fragment声明
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;

    private FragmentPagerAdapter adapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_charge;
    }

    @Override
    protected void initView() {
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

        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
