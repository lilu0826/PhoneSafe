package com.phone.safe.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ContrlIntercepter extends AbstractInterceptor{  
    private static final long serialVersionUID = 2422100326160658352L;  
  
    @Override  
    public String intercept(ActionInvocation invocation) throws Exception {  
        // TODO Auto-generated method stub  
        //获取了用户所要访问的路径,即在struts.xml中设置的action的name  
        //String url = invocation.getProxy().getActionName();  
        HttpSession session = ServletActionContext.getRequest().getSession();  
        //获取用户输入的用户名  
        String username = (String) session.getAttribute("username");  
        //System.out.println(username);  
        if(username != null && username.startsWith("admin")){  
            return invocation.invoke();  
        }else{  
            return "error";  
        }  
          
    }  
  
}  
