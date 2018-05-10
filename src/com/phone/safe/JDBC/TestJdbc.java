package com.phone.safe.JDBC;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TestJdbc {

	public static void main(String []arg) {
		String str ="截止今日17时09分52秒，个人流量剩余：【国内通用】993.88MB，【省内通用】500.00MB，【闲时】639.47MB。 \n" + 
				"如需查询流量使用进度，请点击 ：http://dx.10086.cn/sccxll\n" + 
				"\n" + 
				"【四川移动掌上营业厅】每月500M流量红包免费领，流量办理8折起。点击下载：http://dx.10086.cn/sccdtx【中国移动】";
		Pattern p = Pattern.compile("【省内通用】(.*?)MB");
		Matcher m = p.matcher(str);
		if(m.find()) {
			System.out.println("省内通用:"+m.group(1));
		}
	}
}


	
	
	
	
	
	
	
	

