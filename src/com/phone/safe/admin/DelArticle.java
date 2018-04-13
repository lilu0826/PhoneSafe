package com.phone.safe.admin;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class DelArticle extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int flag;




	@Override
	public String execute() {
		if(JDBCTools.delArticle(id) == 1) {
			flag = 1;//删除成功
		}
		else {
			flag = 0;//删除失败
		}
		
		
		return Action.SUCCESS;
		
	}



	public void setId(int id) {
		this.id = id;
	}
	
	public int getFlag() {
		return flag;
	}

	
}
