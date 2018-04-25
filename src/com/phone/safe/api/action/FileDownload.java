/*
 * 类说明:下载文件(图片)
 * 
 * 
 * */


package com.phone.safe.api.action;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;

public class FileDownload extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String token;
	
	
	public InputStream getPicture() {
		InputStream picture = JDBCTools.getBlob(token);
		if(picture != null) {
			return picture;
		}
		else {
				InputStream temp =  new ByteArrayInputStream("".getBytes());
				return temp;
		}
	}
	
	
	@Override
	public String execute() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.get("token") != null  && this.token == null) {
			this.token = (String) session.get("token");
		}
		return Action.SUCCESS;
		
	}



	public void setToken(String token) {
		this.token = token;
	}


}
