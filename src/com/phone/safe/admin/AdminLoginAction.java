package com.phone.safe.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;
import com.phone.safe.api.action.loginAction;

public class AdminLoginAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	
	@Override
	public String execute() {
		
		if(JDBCTools.adminLogin(this.username,this.password) == 1) {
			ActionContext ctx = ActionContext.getContext();
			ctx.getSession().put("username", this.username);//设置成功
			
			
			return ActionSupport.SUCCESS;
			
		}
		else {
			return ActionSupport.ERROR;
		}
		
		
		
		
		
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
		this.password =  loginAction.getMD5(password);
	}
	
	
}
