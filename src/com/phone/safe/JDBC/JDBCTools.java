package com.phone.safe.JDBC;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.phone.safe.JavaBeans.Article;
import com.phone.safe.JavaBeans.User;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class JDBCTools {
	//获取数据库连接
	private static Connection getConn() {
		
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://cfxiaobao.top:3306/phonesafe?useSSL=false";
	    String username = "root";
	    String password = "root";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	//检查手机号是否存在,存在就修改用户验证码,不存在就创建一天记录1成功0失败
	public static int setverify(String num,String verfy) {
		
		int result = 0;//返回结果
	    int Code = 0;//状态码
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select count(*) from user_table where user_num=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, num);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	Code = rs.getInt(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    if(Code == 0) {//新添加用户
	    	Connection conn = getConn();
	        String sql = "insert into user_table(user_num,user_psw) values(?,?)";
	        PreparedStatement pstmt;
	        try {
	            pstmt = (PreparedStatement) conn.prepareStatement(sql);
	            pstmt.setString(1, num);
	            pstmt.setString(2, verfy);
	            result = pstmt.executeUpdate();
	            pstmt.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    else {//已有功能修改新的验证码
	    	Connection conn = getConn();
	        String sql = "update user_table set user_psw=? where user_num=?";
	        PreparedStatement pstmt;
	        try {
	            pstmt = (PreparedStatement) conn.prepareStatement(sql);
	            pstmt.setString(1, verfy);
	            pstmt.setString(2, num);
	            result = pstmt.executeUpdate();
	            pstmt.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
}	
	
	//用手机号和验证码登录1成功,0失败
	public static int login(String num,String verfy) {
		int result = 0;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select count(*) from user_table where user_num=? and user_psw=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, num);//设置对位标志符号
	        pstmt.setString(2, verfy);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getInt(1);//1成功,0失败
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	//清空用户验证码并插入token
	public static int inserToken(String num,String token) {
		int result=0;
		Connection conn = getConn();
        String sql = "update user_table set user_token=?,user_psw='' where user_num=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, token);
            pstmt.setString(2, num);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
}
	//获取所有文章数据
	public static List<Article> getALLArticle(){
		List<Article> result = new ArrayList<Article>();//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select * from article_table";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	Article article = new Article();
	        	article.setId(rs.getInt(1));
	        	article.setTitle(rs.getString(2));
	        	
	        	article.setContent(rs.getString(3));
	        	
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
	        	
	    
	        	article.setDate(sdf.format(new Date(rs.getTimestamp(4).getTime())));
	        	
	        	
	        	
	        	result.add(article);
	        	
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	//查询是否存在
	public static int isSafeNumExist(String user_safe_num) {
		int result = 0;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select count(*) from user_table where user_safe_num=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, user_safe_num);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getInt(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
		
		
	}
	//把图片保存到数据库
	public static int setBlob(String token ,InputStream picture) {
		int result=0;
		Connection conn = getConn();
        String sql = "update user_table set user_picture=? where user_token=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setBlob(1, picture);
            pstmt.setString(2, token);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
		
	}
	//获取数据库中的图片,成功返回图片数据,失败返回null
	public static InputStream getBlob(String token){
		InputStream result = null;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_picture from user_table where user_token=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, token);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        Blob temp = rs.getBlob(1);
	        	if(temp != null)		
	        	result = temp.getBinaryStream();//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		return result;
		
		
	}
	
	//获取用户安全手机号,成功返回手机号,失败返回null
	public static String getSafeNumFromToken(String token) {
		String result = null;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_safe_num from user_table where user_token=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, token);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getString(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
		
		
	}
	
	//设置用户安全手机号,成功返回1,失败返回0
	public static int setSafeNumFromToken(String token,String safeNum) {
		int result = 0;
		Connection conn = getConn();
        String sql = "update user_table set user_safe_num=? where user_token=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, safeNum);
            pstmt.setString(2, token);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
		
	}
	//获取用户位置
	public static String getPositionFromToken(String token) {
		String result = null;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_position from user_table where user_token=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, token);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getString(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
		
	}
	//设置用户位置
	public static int setPositionFromToken(String token,String position) {
		int result = 0;
		Connection conn = getConn();
        String sql = "update user_table set user_position=?,user_position_time=? where user_token=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, position);
            pstmt.setTimestamp(2,new Timestamp(new Date().getTime()));
            pstmt.setString(3, token);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
		
	}
	//获取用户位置时间
	public static long getPositionTimeFromToken(String token) {
		long result = 0l;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_position_time from user_table where user_token=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, token);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	Timestamp temp = rs.getTimestamp(1);
	        	if(temp != null) {
	        		result = temp.getTime();
	        	}
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
	}
	
	/*
	 * 删除一篇文章
	 * @param tid(文章id)
	*/
	public static int delArticle(int tid) {
		int result = 0;
		Connection conn = getConn();
        String sql = "delete from article_table where artid=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, tid);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
	}
	
	/*添加文章
	 * @param art(文章数据bean)
	
	*/
	public static int addArticle(Article art) {
		int result = 0;
		Connection conn = getConn();
        String sql = "insert into article_table(title,content,date) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, art.getTitle());
            pstmt.setString(2, art.getContent());
            pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return result;
		
	}
	
	/*添加文章
	 * @param art(文章数据bean)
	*/
	public static int editArticle(Article art) {
		int result = 0;
		Connection conn = getConn();
        String sql = "update article_table set title=?,content=?,date=? where artid=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, art.getTitle());
            pstmt.setString(2,art.getContent());
            pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            pstmt.setInt(4, art.getId());
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
	}
	
	/*管理员登录
	 * @param username(String)
	 * @param password(String)
	*/
	public static int adminLogin(String num,String password) {
		int result = 0;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select count(*) from admin_table where username=? and password=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, num);//设置对位标志符号
	        pstmt.setString(2, password);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getInt(1);//1成功,0失败
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	
	//获取所有用户数据
	public static List<User> getALLUser(){
		List<User> result = new ArrayList<User>();//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select * from user_table";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	User user = new User();
	        	user.setUser_num(rs.getString(1));
	        	user.setUser_safe_num(rs.getString(3));
	        	
	        	user.setToken(rs.getString(7));
	        	
	        	result.add(user);
	        	
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	/*
	 * 删除一个用户
	 * @param tid(文章id)
	*/
	public static int delUser(String num) {
		int result = 0;
		Connection conn = getConn();
        String sql = "delete from user_table where user_num=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, num);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
	}
	
	//获取用户token
	public static String getTokenFromSafeNum(String SafeNum) {
		String result = null;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_token from user_table where user_safe_num=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, SafeNum);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getString(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
		
	}
	
	
	//获取用户通讯录
	public static String getmlistFromToken(String token) {
		String result = null;//返回结果
	    try {
	    	Connection conn = getConn(); 
		    String sql = "select user_mlist from user_table where user_token=?";
		    PreparedStatement pstmt;
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.setString(1, token);//设置对位标志符号
	        ResultSet rs = pstmt.executeQuery(); 
	        while(rs.next()) {
	        	result = rs.getString(1);//0没有用户,需要插入,1为存在用户放入验证码
	        }
	        conn.close();
	            }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }	
		
		
		return result;
		
		
	}
	
	//设置用户安全手机号,成功返回1,失败返回0
	public static int setmlistFromToken(String token,String safeNum) {
		int result = 0;
		Connection conn = getConn();
        String sql = "update user_table set user_mlist=? where user_token=?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, safeNum);
            pstmt.setString(2, token);
            result = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    return result;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

			//System.out.println(setPositionFromToken("897812", "11.2,897.9"));
/*		Article art = new Article();
		art.setId(20015);
		art.setTitle("你的手机不安全");
		art.setContent("你的手机太不安全了");*/
		
		System.out.println(setmlistFromToken("f33289b1aa03758cf29d6cf1371ec6fc","晓求不得"));
		
	
	}
	
	
	
	
	
	
	
}

