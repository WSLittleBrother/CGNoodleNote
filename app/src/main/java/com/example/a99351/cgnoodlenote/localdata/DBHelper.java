package com.example.a99351.cgnoodlenote.localdata;

import android.content.Context;
import android.database.SQLException;

import com.example.a99351.cgnoodlenote.localdata.busdb.Comment;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.log.L;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by 99351 on 2017/10/24.
 */

public class DBHelper {
    private static final String tag = "DBHelper";
    /**
     * 系统静态库
     */
    private Dao<User, Integer> userDAO = null;
    private Dao<SysConfig, Integer> sysConfigDAO = null;


    /**
     * 用户业务表
     */
    private Dao<Comment, Integer> commentDAO = null;
    private Dao<Product, Integer> productDAO = null;
    private Dao<DayCharge, Integer> dayChargesDao = null;

    OrmLiteSqliteOpenHelper dbhelper = null;
    DBHelperType dbhelpertype;

    public DBHelper(Context context, DBHelperType dbhelpertype)
            throws java.sql.SQLException {

        this.dbhelpertype = dbhelpertype;

        switch (dbhelpertype) {
            case BUS_DBHELPER://业务相关数据库，每个人的数据库都不一样
                L.v(tag, "initHelper BUS_DBHELPER");
                if (dbhelper != null && dbhelper.isOpen())
                    dbhelper.close();

                dbhelper = BusOpenHelperManager.getHelper(context,
                        BusinessBasicDAO.class);
                break;
            case SYS_DBHELPER://静态数据库----所有人都相同
                L.v(tag, "initHelper SYS_DBHELPER");
                if (null != dbhelper && dbhelper.isOpen())
                    dbhelper.close();
                dbhelper = SysOpenHelperManager.getHelper(context,
                        StaticBasicDAO.class);
                break;
        }
    }

    public ConnectionSource getConnectionSource() {
        return dbhelper.getConnectionSource();
    }

    @Override
    protected void finalize() throws Throwable {
        switch (dbhelpertype) {
            case BUS_DBHELPER:
                BusOpenHelperManager.releaseHelper();
                break;
            case SYS_DBHELPER:
                SysOpenHelperManager.releaseHelper();
                break;
        }
        super.finalize();
    }

    /**
     * 静态表——————————————————start——————————————————————
     * @return
     */
    public Dao<SysConfig, Integer> getSysConfigDAO() {
        try {
            sysConfigDAO = dbhelper.getDao(SysConfig.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return sysConfigDAO;
    }


    public Dao<User, Integer> getUserDAO() {
        try {
            userDAO = dbhelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return userDAO;
    }

    /**
     * 静态表——————————————————end——————————————————————
     * @return
     */

    //********************************************* 分割线 *******************************************

    /**
     * 业务表——————————————————start——————————————————————
     * @return
     */
    public Dao<Comment, Integer> getCommentDao() {
        try {
            commentDAO = dbhelper.getDao(Comment.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return commentDAO;
    }

    public Dao<Product, Integer> getProductDao() {
        try {
            productDAO = dbhelper.getDao(Product.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return productDAO;
    }
    public Dao<DayCharge, Integer> getDayChargeDao() {
        try {
            dayChargesDao = dbhelper.getDao(DayCharge.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return dayChargesDao;
    }


    /**
     * 业务表——————————————————end——————————————————————
     * @return
     */

}
