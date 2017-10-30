package com.example.a99351.cgnoodlenote.ui.day;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseFragment;

/**
 * Created by 99351 on 2017/10/28.
 */

public class DayFragment extends BaseFragment {
    private static DayFragment instance;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    protected void initView() {
        instance = this;

    }

    public static DayFragment getInstance(){
        if (instance == null){
            return new DayFragment();
        }

        return instance;
    }


}
