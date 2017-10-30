package com.example.a99351.cgnoodlenote.ui.week;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseFragment;

/**
 * Created by 99351 on 2017/10/28.
 */

public class WeekFragment extends BaseFragment {
    private static WeekFragment instance;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_week;
    }

    @Override
    protected void initView() {
        instance = this;

    }

    public static WeekFragment getInstance(){
        if (instance == null){
            return new WeekFragment();
        }

        return instance;
    }
}
