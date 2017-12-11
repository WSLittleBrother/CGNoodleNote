package com.example.a99351.cgnoodlenote.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a99351.cgnoodlenote.AppConstant;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.log.L;
import com.example.a99351.cgnoodlenote.utils.SDCardUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by 99351 on 2017/10/24.
 */

public class StaticBasicDAO extends OrmLiteSqliteOpenHelper {
    private static final String tag = "StaticBasicDAO";
    public static final String SYSDATABASE_NAME = "config.db";
    private static String DATABASE_PATH = AppConstant.APP_SDCARD_PATH + SYSDATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    private void initDtaBasePath(Context context) {
        if (!SDCardUtils.isSDCardEnable()) {
            DATABASE_PATH = context.getFilesDir().getAbsolutePath()+SYSDATABASE_NAME;
            L.d(tag, "系统数据库路径" + DATABASE_PATH);
        }
    }

    public StaticBasicDAO(Context context) {
        super(context, SYSDATABASE_NAME, null, DATABASE_VERSION);
        initDtaBasePath(context);
        try {
            File f = new File(DATABASE_PATH);
            SQLiteDatabase db = null;
                if (!f.exists()) {
                db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
                onCreate(db);
            } else {
                db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
            }
            onUpgrade(db, db.getVersion(), DATABASE_VERSION);
            db.close();
        } catch (Exception e) {
        }
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        L.d(tag, "创建数据表LoginLog");
        try {
            TableUtils.createTable(connectionSource, SysConfig.class);
            TableUtils.createTable(connectionSource, User.class);


            database.setVersion(DATABASE_VERSION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
