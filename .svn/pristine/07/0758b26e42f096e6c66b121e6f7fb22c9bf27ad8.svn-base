package com.phone.safe.api.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class SetPosition extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String position;//用户安全手机号
    private String token;//用户的口令
    private int flag;//标志 
    private String msg;//提示信息 
	
	

	@Override
	public String execute() {
		//System.out.println(position.length());
		if((position != null && position.length() != 0) && JDBCTools.setPositionFromToken(token, position) == 1) {
			flag = 1;
			msg ="保存位置成功!";
		}
		else {
			flag = 0;
			msg ="保存位置失败!";
		}
		
		return Action.SUCCESS;
		
	}







	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public int getFlag() {
		return flag;
	}


	public String getMsg() {
		return msg;
	}







	public String getPosition() {
		return position;
	}







	public void setPosition(String position) {
		this.position = position;
	}








}
