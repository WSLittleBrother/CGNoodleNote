package com.example.a99351.cgnoodlenote.ui.day.contract;

import android.content.Context;

import com.example.a99351.cgnoodlenote.base.BasePresenter;
import com.example.a99351.cgnoodlenote.base.BaseView;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;

import java.util.List;

/**
 * Created by 99351 on 2017/12/12.
 */

public interface DayContract {

    interface View extends BaseView{

        void reFreshRecyclerView(List<Product> products);
    }

    abstract class Presenter extends BasePresenter<View>{

       public abstract void getChoosedProduct(Context context,List<DayCharge> dayCharges);
    }
}
