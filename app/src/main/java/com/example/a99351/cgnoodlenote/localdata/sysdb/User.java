package com.example.a99351.cgnoodlenote.localdata.sysdb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "User")
public class User implements Serializable {
	public User() {
	}

	@DatabaseField(generatedId = true, unique = true)
	private int id;
	@DatabaseField(width = 20)
	private String nickname;
	@DatabaseField(width = 50)
	private String password;
	@DatabaseField(width = 20)
	private String username;
	@DatabaseField
	private boolean gander;
	@DatabaseField
	private int age;
	@DatabaseField
	private String hobby;
	@DatabaseField
	private String imgurl;
	@DatabaseField
	private String motto;//座右铭

	@DatabaseField
	private String phonenumber;
	@DatabaseField
	private String info;
	@DatabaseField
	private String email;
	@DatabaseField
	private String realname;

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isGander() {
		return gander;
	}

	public void setGander(boolean gander) {
		this.gander = gander;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}
}
