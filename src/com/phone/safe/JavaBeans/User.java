package com.phone.safe.JavaBeans;


public class User {
	/*数据库:用户表:{手机号(user_num),密码(user_psw),
	 * 安全手机号(user_safe_num),位置(user_positiion)=(经度,纬度),
	 * (时间(time)),图片(picture)}
	文章表:{标题(title),简介(abstract),内容(content)}
	*/
	private String user_num;			//登录手机号
	private String user_psw;			//登录密码
	private String user_safe_num;	//安全手机号
	private String user_positiion;	//位置(经度,纬度)
	private String time;				//位置上传时间
	private String[] picture;		//用户图片链接3张
	private String token;				//位置上传时间
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public String getUser_psw() {
		return user_psw;
	}
	public void setUser_psw(String user_psw) {
		this.user_psw = user_psw;
	}
	public String getUser_safe_num() {
		return user_safe_num;
	}
	public void setUser_safe_num(String user_safe_num) {
		this.user_safe_num = user_safe_num;
	}
	public String getUser_positiion() {
		return user_positiion;
	}
	public void setUser_positiion(String user_positiion) {
		this.user_positiion = user_positiion;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String[] getPicture() {
		return picture;
	}
	public void setPicture(String[] picture) {
		this.picture = picture;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	 
}
