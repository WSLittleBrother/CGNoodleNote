package com.example.a99351.cgnoodlenote.ui.personinfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseActivity;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;

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
        getSupportActionBar().setTitle("带你去旅行");
    }

    @Override
    protected void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "想要带你去旅行", Snackbar.LENGTH_LONG)
                        .setAction("跳转", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showCenterShortToast("哈哈，你上当啦！");
                            }
                        }).show();
            }
        });
    }
}
