package com.example.a99351.cgnoodlenote;

import com.example.a99351.cgnoodlenote.utils.SDCardUtils;

/**
 * Created by 99351 on 2017/10/24.
 */

public class AppConstant {
    public final static  String baseApi = "";
    public static final String APP_SDCARD_PATH = SDCardUtils.getSDCardPath() + "ws/myapp/";


    /**
     * 请求码
     */
    //拍照
    public static final int OPEN_PHOTO = 1000;
    //拍照
    public static final int OPEN_PHOTO_ALBUM = 1001;

}
