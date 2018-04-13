package com.phone.safe.api.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class GetPosition extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String position;//用户位置
	private long position_time;//用户位置
    private String token;//用户的口令
    private int flag;//标志 
    private String msg;//提示信息 
	
	

	@Override
	public String execute() {
		position = JDBCTools.getPositionFromToken(token);
		position_time = JDBCTools.getPositionTimeFromToken(token);
		
		
		if(position != null && position.length() != 0) {
			flag = 1;
			msg ="获取位置成功!";
		}
		else {
			flag = 0;
			msg ="获取位置失败!";
		}
		
		return Action.SUCCESS;
		
	}



	public String getPosition() {
		return position;
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



	public long getPosition_time() {
		return position_time;
	}






}