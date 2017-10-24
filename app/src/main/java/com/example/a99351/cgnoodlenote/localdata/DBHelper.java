package com.example.a99351.cgnoodlenote.localdata;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.example.a99351.cgnoodlenote.log.L;
import com.example.a99351.cgnoodlenote.model.User;
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

    public Dao<User, Integer> getLoginLogDAO() {
        try {
            userDAO = dbhelper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return userDAO;
    }
}
