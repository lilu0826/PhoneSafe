package com.phone.safe.api.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class SetSafeNum extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String safe_num;//用户安全手机号
    private String token;//用户的口令
    private int flag;//标志 
    private String msg;//提示信息 
	
	

	@Override
	public String execute() {
		if(safe_num != null && safe_num.length() != 0 && JDBCTools.setSafeNumFromToken(token, safe_num) == 1) {
			flag = 1;
			msg ="保存安全手机号成功!";
		}
		else {
			flag = 0;
			msg ="保存安全手机号失败!";
		}
		
		return Action.SUCCESS;
		
	}



	public String getSafe_num() {
		return safe_num;
	}



	public void setSafe_num(String safe_num) {
		this.safe_num = safe_num;
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

}
