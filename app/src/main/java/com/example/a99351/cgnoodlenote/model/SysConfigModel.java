package com.example.a99351.cgnoodlenote.model;


import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;

/**
 * @Description 应用静态配置模型
 */
public class SysConfigModel {
    static SysConfig sysConfig;
    static int screenDp;
    public static SysConfig getSysConfig() {
        return sysConfig;
    }

    public static void setSysConfig(SysConfig sysConfig) {
        SysConfigModel.sysConfig = sysConfig;
    }

    public static int getScreenDp() {
        return screenDp;
    }

    public static void setScreenDp(int screenDp) {
        SysConfigModel.screenDp = screenDp;
    }
}
