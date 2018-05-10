package com.phone.safe.api.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;


public class GetPosition extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> position;//用户位置
	private String position_time;//用户位置
    private String token;//用户的口令
    private int flag;//标志 
    private String msg;//提示信息 
	
	

	@Override
	public String execute() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.get("token") != null  && this.token == null) {
			this.token = (String) session.get("token");
		}

		String positionTemp = JDBCTools.getPositionFromToken(token);
		
		
		
		
		position_time = JDBCTools.getPositionTimeFromToken(token);
		
		
		if(positionTemp != null && positionTemp.length() != 0) {
			String[] temp = positionTemp.split(",");
			
			position = new HashMap<String,String>();
			
			position.put("X", temp[0]);
			position.put("Y",temp[1]);
			
			flag = 1;
			msg ="获取位置成功!";
		}
		else {
			flag = 0;
			msg ="获取位置失败!";
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



	public String getPosition_time() {
		return position_time;
	}











	public Map<String, String> getPosition() {
		return position;
	}







}