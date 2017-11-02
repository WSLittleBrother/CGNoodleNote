package com.example.a99351.cgnoodlenote.model;

//import com.sunwayworld.monit.common.dao.staticdb.User;

/**
 * Created by Think on 2016/4/22.
 */
public class UserModel {
    static User user;

    public static User getUser() {
        if (user == null) return new User();
        return user;
    }

    public static void setUser(User usr) {
        user = usr;
    }
}
