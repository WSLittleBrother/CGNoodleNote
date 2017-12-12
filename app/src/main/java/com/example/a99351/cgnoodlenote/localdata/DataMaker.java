package com.example.a99351.cgnoodlenote.localdata;

import android.content.Context;

import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.ui.day.CallBack;
import com.example.a99351.cgnoodlenote.utils.DateTimeUtils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 99351 on 2017/12/12.
 */

public class DataMaker {


    public static List<Product> getProductList(Context mContext){
        DBHelper dbhelper = null;
        List<Product> products = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<Product, Integer> userDao = dbhelper.getProductDao();
            products = userDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<DayCharge> getDayCharge(Context mContext,String today,String productid){
        DBHelper dbhelper = null;
        List<DayCharge> dayCharges = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<DayCharge, Integer> userDao = dbhelper.getDayChargeDao();
            dayCharges = userDao.queryBuilder()
            .where()
            .eq("createdate",today)
            .and()
            .eq("productid",productid).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayCharges;
    }

    /**
     * 获得当天的所有产品列表
     * @param mContext
     * @param today   时间
     * @return
     */
    public static List<DayCharge> getDayCharges(Context mContext,String today){
        DBHelper dbhelper = null;
        List<DayCharge> dayCharges = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<DayCharge, Integer> userDao = dbhelper.getDayChargeDao();
            dayCharges = userDao.queryBuilder()
                    .where()
                    .eq("createdate",today)
                    .query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayCharges;
    }

    /**
     * 这里是进行创建每天表的代码
     * @param mContext
     * @param products
     * @param callBack   之所以写这个回调就是怕查表时间太长了导致有问题
     * @throws Exception
     */
    public static void createOrUndateDayCharge(final Context mContext, final List<Product> products, final CallBack callBack){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    DBHelper dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
                    Dao<DayCharge, Integer> dayChargeDao = dbhelper.getDayChargeDao();
                    for (Product product : products){
                        DayCharge dayCharge = dayChargeDao.queryBuilder()
                                .where()
                                .eq("createdate",product.getCreateday())
                                .and()
                                .eq("id",product.getId())
                                .queryForFirst();

                        if (dayCharge == null){
                            dayCharge = new DayCharge();
                            dayCharge.setProductid(product.getId());
                            dayCharge.setProductimg(product.getImgurl());
                            dayCharge.setProductname(product.getProductname());
                            dayCharge.setCreatedate(DateTimeUtils.getCurrentDateTimeYMD());
                        }
                        dayChargeDao.createOrUpdate(dayCharge);
                    }
                    callBack.callback();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
