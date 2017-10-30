package com.example.a99351.cgnoodlenote.ui.personinfo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by 99351 on 2017/10/28.
 */

public class PersonInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info_activity;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("羊蝎子面馆");
    }

    @Override
    protected void initView() {

    }
}
