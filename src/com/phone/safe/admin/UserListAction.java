package com.phone.safe.admin;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;
import com.phone.safe.JavaBeans.User;

public class UserListAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> user;
	private String user_num;
	private int flag;




	@Override
	public String execute() {
			if(user_num == null) {
				this.user = JDBCTools.getALLUser();
			}
			else if(JDBCTools.delUser(this.user_num) == 1){
				this.flag = 1;
			}
			else {
				this.flag = 0;
			}
		
		
		
		
		return Action.SUCCESS;
	}




	public List<User> getUser() {
		return user;
	}









	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}




	public int getFlag() {
		return flag;
	}




	public void setFlag(int flag) {
		this.flag = flag;
	}



}