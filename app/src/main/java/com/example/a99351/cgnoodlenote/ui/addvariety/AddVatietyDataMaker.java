package com.example.a99351.cgnoodlenote.ui.addvariety;

import android.content.Context;

import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by 99351 on 2017/12/9.
 */

public class AddVatietyDataMaker {
    private Context mContext;
    private DBHelper bushelper;
    public static final int ADD_ID = 1;

    public AddVatietyDataMaker(Context mContext) {
        this.mContext = mContext;
    }


    public void addVatietyPrduct(Product product){
        try {
            bushelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<Product, Integer> userDao = bushelper.getProductDao();
            userDao.createOrUpdate(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void savePrductImgUrl(Product product){
        try {
            bushelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<Product, Integer> userDao = bushelper.getProductDao();
            userDao.createOrUpdate(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
