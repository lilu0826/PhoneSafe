/*
 * 类说明:下载文件(图片)
 * 
 * 
 * */


package com.phone.safe.api.action;

import java.io.ByteArrayInputStream;

import java.io.InputStream;


import com.opensymphony.xwork2.Action;
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

		return Action.SUCCESS;
		
	}



	public void setToken(String token) {
		this.token = token;
	}


}
