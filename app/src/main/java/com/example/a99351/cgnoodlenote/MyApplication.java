package com.example.a99351.cgnoodlenote;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by 99351 on 2017/10/24.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
    }

    public static MyApplication getAppContext() {
        if (instance ==null){
            instance = new MyApplication();
        }
      return instance;
    }
}
