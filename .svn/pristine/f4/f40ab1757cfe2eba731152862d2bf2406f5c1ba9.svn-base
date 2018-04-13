package com.phone.safe.api.action;

import java.util.ArrayList;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class GetVerifyAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463747709918247302L;
	
	private String user_num;
	private int flag;
	private String msg;

	@Override
	public String execute() {
		try {
		    SmsSingleSender sender = new   SmsSingleSender(1400077358, "b8c62c53bfe3fa41713f539bf015f468"); 
		    ArrayList<String> params = new ArrayList<String>();
		    String verify = String.valueOf((int)((Math.random()*9+1)*100000));
		    params.add(verify);
		    params.add("10");
		    SmsSingleSenderResult   result = sender.sendWithParam("86", this.user_num, 98760, params, "", "", "");
		    System.out.println(result.result);
		    //短信下发成功
		    if(result.result == 0) {
		    	if( JDBCTools.setverify(user_num, verify) == 1) {
		    		this.setFlag(1);//获取成功
		    		this.msg = "验证码下发成功!";
		    	}
		    	
		    }
		    else {
	    		this.setFlag(0);//获取失败
	    		this.msg = "验证码获取失败,请稍后再试!";
		    }
		   
		    
		} 
		catch (Exception e) {
		//e.printStackTrace();
    		this.setFlag(0);//获取失败
    		this.msg = "验证码获取失败,请稍后再试!";
		}
		
		return Action.SUCCESS;
		
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
}
