package com.example.a99351.cgnoodlenote.ui.loginandregister;

import android.content.Context;

import com.example.a99351.cgnoodlenote.AppConstant;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.model.SysConfigModel;
import com.example.a99351.cgnoodlenote.model.UserModel;
import com.example.a99351.cgnoodlenote.utils.SDCardUtils;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 99351 on 2017/12/5.
 */

public class LoginUtil {


    public static boolean startLogin(Context mContext, Map<String ,String>map){
        DBHelper dbhelper = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.SYS_DBHELPER);
            Dao<User, Integer> userDao = dbhelper.getUserDAO();
            List<User> user = userDao.queryForAll();
            for (User item : user){
                if (item.getUsername().equals(map.get("username"))){
                    if (!item.getPassword().equals(map.get("password"))){
                        ToastUtil.showShortToast("密码不正确");
                        return false;
                    }else{
                        UserModel.setUser(item);
                       return true;
                    }
                }else{
                    ToastUtil.showShortToast("无效的用户名，请先注册");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveUserInfo(User user, Context mContext) {
        DBHelper dbhelper;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.SYS_DBHELPER);
            Dao<User, Integer> userDao = dbhelper.getUserDAO();
            User u = userDao.queryBuilder().where()
                    .eq("username", user.getUsername())
                    .queryForFirst();
            if (u != null) {
                user.setId(u.getId());
                userDao.update(user);
            } else {
                userDao.create(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /***
     * 初始化用户文件夹
     ***/
    public static boolean initUserFileAndFolder() {
        if (SDCardUtils.isSDCardEnable()) {
            //用户个人的专用文件夹,用于存放附件
            String personUrl = AppConstant.APP_SDCARD_PATH + UserModel.getUser().getUsername() + "/";
            File destDir = new File(personUrl
                    + "comment/crop");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
        }
        return true;

    }


    /***
     * 保存登陆状态
     ***/
    public static void saveLoginState(Context context,String username,String password) {
        DBHelper dbhelper = null;
        try {
            dbhelper = new DBHelper(context,
                    DBHelperType.SYS_DBHELPER);
            Dao<SysConfig, Integer> sysConfigDao = dbhelper.getSysConfigDAO();

            SysConfig sysconfig = SysConfigModel.getSysConfig();
            sysconfig.setLastusername(username);
            sysconfig.setLastpassword(password);
            //当用户登录的时候默认下次是自动登录
            sysconfig.setLogin(true);
            sysConfigDao.update(sysconfig);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
