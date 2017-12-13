package com.example.a99351.cgnoodlenote.ui.day.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.DataMaker;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.ui.day.contract.DayContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99351 on 2017/12/12.
 */

public class DayPresenter extends DayContract.Presenter {
    @Override
    public void onStart() {

    }

    /**
     *
     * @param context   上下文对象
     * @param dayCharges  这里传过来的是已经呈现出来的商品列表
     * @return
     */
    @Override
    public void getChoosedProduct(Context context, List<DayCharge> dayCharges) {
        //总的产品列表
        List<Product> sumProductList = DataMaker.getProductList(mContext);
        //
        final List<Product> needProductList =new ArrayList<>();
        final List<Product> showProductList =DataMaker.getProductList(mContext);
        for (DayCharge dayCharge:dayCharges){
            for (int i=0;i<sumProductList.size();i++){
                if (dayCharge.getProductid()==sumProductList.get(i).getId()){
                    int productid = sumProductList.get(i).getId();
                    for (int j=0;j<showProductList.size();j++){
                        if (showProductList.get(j).getId()==productid){
                            showProductList.remove(j);
                        }
                    }
                }
            }
        }

        final List<Boolean> checkedList = new ArrayList<>();
        //这里只不过是为了给checked列表进行初始化值，全部设置为false
        for (Product i :showProductList){
            checkedList.add(false);
        }
        String[] str = new String[showProductList.size()];
        for (int i = 0;i<showProductList.size();i++){
            str[i] = showProductList.get(i).getProductname();
        }
//        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_add_variety,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择将要购买的物品").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i= 0;i<showProductList.size();i++){
                    if (checkedList.get(i)){
                        needProductList.add(showProductList.get(i));
                    }
                }
                mView.reFreshRecyclerView(needProductList);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).setMultiChoiceItems(str, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    checkedList.set(which,true);
                }else{
                    checkedList.set(which,false);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
