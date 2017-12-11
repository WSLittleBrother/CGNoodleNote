package com.example.a99351.cgnoodlenote.ui.loginandregister.contract;

import com.example.a99351.cgnoodlenote.base.BasePresenter;
import com.example.a99351.cgnoodlenote.base.BaseView;

import java.util.Map;

/**
 * Created by 99351 on 2017/12/2.
 */

public interface LoginContract {

    interface View extends BaseView{
        void getRegisterCodeSuccess(String info);

        void registerSuccess(String model);

        void loginByPassword(String model);

        void loadFail(String msg);
        void showLoading();
        void hideLoading();
    }


    abstract class Presenter extends BasePresenter<View>{
        public abstract void getRegisterCode();
        public abstract void register(Map<String, String> map);
        public abstract void loginByPassword(Map<String, String> map);
        public abstract void forgetPassword(Map<String, String> map);
        public abstract void getActivation(String activation);
    }

}
