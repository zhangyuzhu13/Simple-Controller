package ormapping;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import bean.UserBean;

public class Conversation<T> {
	/*
	 * 将xml中的信息映射为数据库信息，将数据库操作封装为对对象的操作
	 * 使用模板类，处理多种对象
	 */
	Class<T> c;  //待处理对象
	String className;
	Configuration config;  //用于解析or_mapping.xml的对象
	Connection con ;//数据库连接
	PreparedStatement stmt ;//数据库操作
	ResultSet rs ;//数据库操作结果
	
	public Conversation(Class<T> c,String className){
		this.c = c;
		this.className = className;
		config = new Configuration();
	}
	//连接数据库
	public void getConnect(){
		
		HashMap<String, String> jdbc = config.getJDBC();
		 
		try {  
            Class.forName(jdbc.get("drive_class"));// 加载Mysql数据驱动  
              
            con = DriverManager.getConnection(  
            		jdbc.get("url_path"), jdbc.get("db_username"), jdbc.get("db_password"));// 创建数据连接  
              
        } catch (Exception e) {  
            System.out.println("数据库连接失败" + e.getMessage());  
        }  
          
	}
	//获取user对象的操作
	public <T> T getUser(String paramName,String paramValue){
		if(con==null)
			getConnect();
		HashMap<String, String> tableMap = config.getTable(className);
		//构造查询语句，这样构造是因为，只有用户名是用户传入的，需要使用传参语句进行过滤，防止sqli攻击
		String sql = "select * from "+tableMap.get("table")+" where "+tableMap.get(paramName)+" = ?";
		try {
			stmt = con.prepareStatement(sql);
			//将要查询的用户名传入
			stmt.setString(1,paramValue);
			rs = stmt.executeQuery();
			if(rs.next()){
				T t = (T) c.newInstance();
				Field[] fields = c.getDeclaredFields();
				for(Field field:fields){
					//设置可以写入
					field.setAccessible(true);
					//遍历进赋值。
					field.set(t, rs.getString(tableMap.get(field.getName())));
				}
				return t;
			}
			
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnect();
		}
		return null;
	}
	//关闭数据库连接
	public boolean closeConnect(){
		try {
			//关闭结果集
			if(rs != null){
				rs.close();
			}
			//关闭状态
			if(stmt != null){
				stmt.close();
			}
			//关闭连接
			if(con != null){
				con.close();
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
