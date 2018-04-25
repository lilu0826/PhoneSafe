package com.phone.safe.user;

import java.util.ArrayList;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class UserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int flag;
	private String safeNum;
	private String verify;
	
	
	
	public String GetVerify() {
		if(JDBCTools.isSafeNumExist(safeNum) == 1) {
			//用户存在
			this.flag = 1;
			SmsSingleSender sender;
			try {
				sender = new   SmsSingleSender(1400077358, "b8c62c53bfe3fa41713f539bf015f468");
				ArrayList<String> params = new ArrayList<String>();
			    String verify = String.valueOf((int)((Math.random()*9+1)*100000));
			    params.add(verify);
			    params.add("10");
			    System.out.println(verify);
			    //SmsSingleSenderResult result = sender.sendWithParam("86", this.SafeNum, 98760, params, "", "", "");
			    
			    
			    
			    //if(result.result == 0) {
			    if(true) {
			    	//短信发送成功
			    	ActionContext ctx = ActionContext.getContext();
			    	ctx.getSession().put("Verify", verify);
			    	ctx.getSession().put("safeNum", this.safeNum);
			    	this.flag = 1;
			    }
			    else {
			    	this.flag = -1;//短信发送失败
			    }
			    
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    
			
		}
		else {
			this.flag = 0;//安全手机号不存在
		}
		
		
		
		
		return ActionSupport.SUCCESS;
		
		
	}

	public String Login() {
		
		ActionContext ctx = ActionContext.getContext();
    	String tempVerify = (String) ctx.getSession().get("Verify");
    	String safenum = (String) ctx.getSession().get("safeNum");
    	
    	if(this.verify.equals(tempVerify) && this.safeNum.equals(safenum)) {
    		this.flag = 1;//登录成功
    		ctx.getSession().put("isLogin",true);
    		ctx.getSession().put("LoginNum",safenum);
    		ctx.getSession().put("token", JDBCTools.getTokenFromSafeNum(safenum));
    	}
    	else {
    		this.flag = 0;//登录失败
    	}
    	
    	
		
		return ActionSupport.SUCCESS;
		
	}
	




	public String getSafeNum() {
		return safeNum;
	}





	public void setSafeNum(String SafeNum) {
		safeNum = SafeNum;
	}




	public int getFlag() {
		return flag;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}





}
