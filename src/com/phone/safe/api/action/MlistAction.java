package com.phone.safe.api.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class MlistAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mlist;//用户安全手机号
    private String token;//用户的口令
    private int flag;//标志 
    private String msg;//提示信息 
	
	

	@Override
	public String execute() {
		if(token != null && mlist != null) {//两个都不为空，上传通讯录
			if(JDBCTools.setmlistFromToken(token, mlist) == 1) {
				mlist = null;
				flag = 2;
				msg = "备份通讯录成功！";
			}
			else {
				mlist = null;
				flag = 0;
				msg = "token错误，备份通讯录失败！";
			}

		}
		else if(token != null && mlist == null) {//获取通讯录
			mlist = JDBCTools.getmlistFromToken(token);
			if(mlist !=null){
			flag = 1;
			msg = "恢复通讯录成功";
			}
			else {
				flag = 0;
				msg = "token错误，恢复通讯录失败";
			}
		}
		else {
			flag = 0;
			msg = " 参数错误";
		}
		
		return Action.SUCCESS;
		
	}
	public String getMlist() {
		return mlist;
	}



	public void setMlist(String mlist) {
		this.mlist = mlist;
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