package com.example.a99351.cgnoodlenote.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间日期操作工具类
 * Created by sunshujie on 2017/6/16.
 */

public class DateTimeUtils {
    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateTime(String timeStyle) {
        return new SimpleDateFormat(timeStyle).format(Calendar.getInstance().getTime());
    }
}
