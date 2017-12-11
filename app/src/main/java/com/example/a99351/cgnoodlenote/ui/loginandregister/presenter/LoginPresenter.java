package com.example.a99351.cgnoodlenote.ui.loginandregister.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.a99351.cgnoodlenote.MainActivity;
import com.example.a99351.cgnoodlenote.dialog.NormalAlertDialog;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.model.UserModel;
import com.example.a99351.cgnoodlenote.ui.loginandregister.LoginUtil;
import com.example.a99351.cgnoodlenote.ui.loginandregister.contract.LoginContract;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 99351 on 2017/12/2.
 */

public class LoginPresenter extends LoginContract.Presenter {
    private Handler handler = new Handler();
    private String activation ="";
    @Override
    public void getRegisterCode() {
        DBHelper dbhelper = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.SYS_DBHELPER);
            Dao<SysConfig, Integer> sysConfiDao = dbhelper.getSysConfigDAO();
            SysConfig sysConfig = sysConfiDao.queryBuilder().queryForFirst();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("开始激活").setMessage(sysConfig.getActivation()).setPositiveButton("确认",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void register(final Map<String, String> map) {
        if (!activation.equals("王双")){
            ToastUtil.showShortToast("无效的激活码");

            return;
        }
        mView.showLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setUsername(map.get("username"));
                user.setPassword(map.get("password"));
                LoginUtil.saveUserInfo(user, mContext);
                UserModel.setUser(user);
                mView.hideLoading();
                mView.registerSuccess("注册成功,请登录");
            }
        },2000);
    }

    @Override
    public void loginByPassword(final Map<String, String> map) {
        mView.showLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (LoginUtil.startLogin(mContext,map)){
                    activation = "";
                    mView.loginByPassword("登录");
                }
                mView.hideLoading();
            }
        },2000);

    }

    @Override
    public void forgetPassword(Map<String, String> map) {
        if (!activation.equals("王双")){
            ToastUtil.showShortToast("无效的激活码");

            return;
        }
    }

    @Override
    public void getActivation(String activation) {
        this.activation = activation;
    }


    @Override
    public void onStart() {

    }
}
