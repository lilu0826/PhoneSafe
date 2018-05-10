package com.phone.safe.admin;

import java.util.List;

import org.apache.struts2.ServletActionContext;

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
	private List<Article> articleList;
	
	
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
	
	public String getArticle() {
		 this.articleList = JDBCTools.getALLArticle();
		String ip = ServletActionContext.getRequest().getServerName();
		//System.out.println(ip);
		for (Article article : articleList) {
			
			article.setUrl("http://"+ip+":8080/PhoneSafe/showArticle?tid="+article.getId());
		}
		


		
		
		return Action.SUCCESS;
	}
	
	
	
	
	
	
	
	
	public List<Article> getArticleList() {
		return articleList;
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
