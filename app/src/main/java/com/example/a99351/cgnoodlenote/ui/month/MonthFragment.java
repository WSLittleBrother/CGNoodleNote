package com.example.a99351.cgnoodlenote.ui.month;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseFragment;

/**
 * Created by 99351 on 2017/10/28.
 */

public class MonthFragment extends BaseFragment {
    private static MonthFragment instance;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_month;
    }

    @Override
    protected void initView() {
        instance = this;

    }

    public static MonthFragment getInstance(){
        if (instance == null){
            return new MonthFragment();
        }

        return instance;
    }
}
