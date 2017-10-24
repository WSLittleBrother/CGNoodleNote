package com.example.a99351.cgnoodlenote.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 99351 on 2017/10/24.
 */
@DatabaseTable(tableName = "User")
public class User  implements Serializable{

    public User() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;
    @DatabaseField
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
