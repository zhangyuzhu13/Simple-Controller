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
	 * ��xml�е���Ϣӳ��Ϊ���ݿ���Ϣ�������ݿ������װΪ�Զ���Ĳ���
	 * ʹ��ģ���࣬������ֶ���
	 */
	Class<T> c;  //���������
	String className;
	Configuration config;  //���ڽ���or_mapping.xml�Ķ���
	Connection con ;//���ݿ�����
	PreparedStatement stmt ;//���ݿ����
	ResultSet rs ;//���ݿ�������
	
	public Conversation(Class<T> c,String className){
		this.c = c;
		this.className = className;
		config = new Configuration();
	}
	//�������ݿ�
	public void getConnect(){
		
		HashMap<String, String> jdbc = config.getJDBC();
		 
		try {  
            Class.forName(jdbc.get("drive_class"));// ����Mysql��������  
              
            con = DriverManager.getConnection(  
            		jdbc.get("url_path"), jdbc.get("db_username"), jdbc.get("db_password"));// ������������  
              
        } catch (Exception e) {  
            System.out.println("���ݿ�����ʧ��" + e.getMessage());  
        }  
          
	}
	//��ȡuser����Ĳ���
	public <T> T getUser(String paramName,String paramValue){
		if(con==null)
			getConnect();
		HashMap<String, String> tableMap = config.getTable(className);
		//�����ѯ��䣬������������Ϊ��ֻ���û������û�����ģ���Ҫʹ�ô��������й��ˣ���ֹsqli����
		String sql = "select * from "+tableMap.get("table")+" where "+tableMap.get(paramName)+" = ?";
		try {
			stmt = con.prepareStatement(sql);
			//��Ҫ��ѯ���û�������
			stmt.setString(1,paramValue);
			rs = stmt.executeQuery();
			if(rs.next()){
				T t = (T) c.newInstance();
				Field[] fields = c.getDeclaredFields();
				for(Field field:fields){
					//���ÿ���д��
					field.setAccessible(true);
					//��������ֵ��
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
	//�ر����ݿ�����
	public boolean closeConnect(){
		try {
			//�رս����
			if(rs != null){
				rs.close();
			}
			//�ر�״̬
			if(stmt != null){
				stmt.close();
			}
			//�ر�����
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
