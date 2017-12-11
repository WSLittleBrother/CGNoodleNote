package com.example.a99351.cgnoodlenote.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a99351.cgnoodlenote.AppConstant;
import com.example.a99351.cgnoodlenote.localdata.busdb.Comment;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.log.L;
import com.example.a99351.cgnoodlenote.model.UserModel;
import com.example.a99351.cgnoodlenote.utils.SDCardUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

public class BusinessBasicDAO extends OrmLiteSqliteOpenHelper {
	private static final String tag = "BusinessBasicDAO";
	public static String BUSDATABASE_NAME = "project.db";
	private String DATABASE_PATH = AppConstant.APP_SDCARD_PATH + UserModel.getUser().getUsername() + "/" + BUSDATABASE_NAME;

	private static final int DATABASE_VERSION = 3;

	private void initDtaBasePath(Context context) {
		if (!SDCardUtils.isSDCardEnable()) {
			DATABASE_PATH = context.getFilesDir().getAbsolutePath() + BUSDATABASE_NAME;
		}
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
	}

	public BusinessBasicDAO(Context context) {
		super(context, BUSDATABASE_NAME, null, DATABASE_VERSION);
		initDtaBasePath(context);
		L.d(tag, "数据库路径" + DATABASE_PATH);
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
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connSource) {
		try {
			TableUtils.createTable(connSource, Comment.class);
			TableUtils.createTable(connSource, Product.class);

			arg0.setVersion(DATABASE_VERSION);
		} catch (SQLException e) {
			L.d(tag, "创建表失败" + e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connSource, int oldVersion, int newVersion) {
		if (oldVersion != newVersion) {
			try {
				if (oldVersion <2){
					String sql = "ALTER TABLE Product ADD COLUMN remake text";
					db.execSQL(sql);
				}
				if (oldVersion < 3) {
					String sql1 = "ALTER TABLE Product ADD COLUMN imgurl text";
					db.execSQL(sql1);
				}
				db.setVersion(newVersion);
			}catch (Exception e){
			e.printStackTrace();}
		}
	}
}
