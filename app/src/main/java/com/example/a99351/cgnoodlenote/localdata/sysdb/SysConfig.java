package com.example.a99351.cgnoodlenote.localdata.sysdb;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "SysConfig")
public class SysConfig implements Serializable {

	public SysConfig() {
	}

	@DatabaseField(id = true, unique = true)
	private int id;
	@DatabaseField(width = 100)
	private String title;
	@DatabaseField
	private boolean isLogin;
	@DatabaseField(width = 20)
	private String lastusername;
	@DatabaseField(width = 20)
	private String lastpassword;
	@DatabaseField(width = 100)
	@SerializedName("mdmurl")
	private String baseurl;
	@DatabaseField
	private String activation;

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		activation ="激活码是我的名字，请输入我的名字！";
		this.activation = activation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getLastusername() {
		return lastusername;
	}

	public void setLastusername(String lastusername) {
		this.lastusername = lastusername;
	}

	public String getLastpassword() {
		return lastpassword;
	}

	public void setLastpassword(String lastpassword) {
		this.lastpassword = lastpassword;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
}
