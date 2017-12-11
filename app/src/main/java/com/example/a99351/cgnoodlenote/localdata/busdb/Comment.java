package com.example.a99351.cgnoodlenote.localdata.busdb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 99351 on 2017/12/6.
 */
@DatabaseTable(tableName = "Comment")
public class Comment {

    @DatabaseField(generatedId = true, unique = true)
    private int id;
    @DatabaseField
    private String createdate;
    @DatabaseField
    private String realname;
    @DatabaseField
    private String username;
    @DatabaseField
    private String commentname;
    @DatabaseField
    private String type;
    @DatabaseField(width = 100)
    private String remake;//备注
    @DatabaseField
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentname() {
        return commentname;
    }

    public void setCommentname(String commentname) {
        this.commentname = commentname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
