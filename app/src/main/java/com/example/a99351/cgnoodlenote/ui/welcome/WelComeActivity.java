package com.example.a99351.cgnoodlenote.ui.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.a99351.cgnoodlenote.AppConstant;
import com.example.a99351.cgnoodlenote.MainActivity;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.log.L;
import com.example.a99351.cgnoodlenote.model.SysConfigModel;
import com.example.a99351.cgnoodlenote.model.UserModel;
import com.example.a99351.cgnoodlenote.ui.loginandregister.LoginAndRegisterActivity;
import com.example.a99351.cgnoodlenote.ui.loginandregister.LoginUtil;
import com.example.a99351.cgnoodlenote.utils.SDCardUtils;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 99351 on 2017/11/28.
 */

public class WelComeActivity extends AppCompatActivity {
private View rootView;
//是否有权限
    private boolean hasPermissionsResult = false;
//这里控制的是是否直接进入到主界面
private boolean isLogin =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_welcome, null);
        setContentView(rootView);
        verifyStoragePermissions(WelComeActivity.this);
        initView();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    private void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }else{
                hasPermissionsResult = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        initApp();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (isLogin){
                        Map<String ,String> map = new HashMap<>();
                        map.put("username",SysConfigModel.getSysConfig().getLastusername());
                        map.put("password",SysConfigModel.getSysConfig().getLastpassword());
                        if (LoginUtil.startLogin(WelComeActivity.this,map)){
                            Intent intent = new Intent(WelComeActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(WelComeActivity.this,LoginAndRegisterActivity.class);
                            startActivity(intent);
                        }

                    }else{
                        Intent intent = new Intent(WelComeActivity.this,LoginAndRegisterActivity.class);
                        startActivity(intent);
                    }
                    WelComeActivity.this.finish();
                }
                break;
        }
    }

    private void initView() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcome_alpha);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (hasPermissionsResult){
                    try {
                        initApp();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (isLogin){
                        Map<String ,String> map = new HashMap<>();
                        map.put("username",SysConfigModel.getSysConfig().getLastusername());
                        map.put("password",SysConfigModel.getSysConfig().getLastpassword());
                        if (LoginUtil.startLogin(WelComeActivity.this,map)){
                            Intent intent = new Intent(WelComeActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(WelComeActivity.this,LoginAndRegisterActivity.class);
                            startActivity(intent);
                        }

                    }else{
                        Intent intent = new Intent(WelComeActivity.this,LoginAndRegisterActivity.class);
                        startActivity(intent);
                    }
                    WelComeActivity.this.finish();
                }else{
                    ToastUtil.showbottomLongToast("动态获取权限失败，请联系管理员");
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation);
    }

    /**
     * 这里是建立静态数据库的部分
     */
    static SysConfig sysConfig;
    public void initApp() throws SQLException {
        initFileFolder();

        DBHelper dbhelper = new DBHelper(getApplicationContext(), DBHelperType.SYS_DBHELPER);
        Dao<SysConfig, Integer> sysConfiDao = dbhelper.getSysConfigDAO();
        sysConfig = sysConfiDao.queryBuilder().queryForFirst();

        if (sysConfig == null){
            sysConfig = new SysConfig();
            sysConfig.setId(new Random().nextInt());
        }else{
            //项目默认的是在登录成功后就设置为当前用户自动登录
            isLogin = sysConfig.isLogin();
        }
            sysConfig.setId(sysConfig.getId());
            sysConfig.setActivation("");
            sysConfiDao.createOrUpdate(sysConfig);
            SysConfigModel.setSysConfig(sysConfig);
    }

    private void initFileFolder() {
        if (SDCardUtils.isSDCardEnable()) {
            File dir = new File(AppConstant.APP_SDCARD_PATH);
            L.i("TTTT","AppConstant.APP_SDCARD_PATH:"+AppConstant.APP_SDCARD_PATH);
            if (!dir.exists()) {
                L.i("TTTT","wwwwwww："+dir.exists());
                dir.mkdirs();
                L.i("TTTT","aaaaaaa："+dir.exists());
            }
        }
    }
}
