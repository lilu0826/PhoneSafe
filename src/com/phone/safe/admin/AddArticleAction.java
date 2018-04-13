package com.phone.safe.admin;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.phone.safe.JDBC.JDBCTools;
import com.phone.safe.JavaBeans.Article;

public class AddArticleAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Article art;
	private int flag;
	
	public String execute() {
		if(JDBCTools.addArticle(art) == 1) {
			setFlag(1);//成功
		}
		else {
			setFlag(0);//失败
		}
		
		
		return Action.SUCCESS;
		
	}

	public String edit() {
		
		if(JDBCTools.editArticle(art) == 1) {
			setFlag(1);//成功
		}
		else {
			setFlag(0);//失败
		}
		
		
		return Action.SUCCESS;
		
	}
	
	
	
	
	
	
	
	
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Article getArt() {
		return art;
	}

	public void setArt(Article art) {
		this.art = art;
	}
	

}
