package DAOImpl;
import java.sql.*;

import ormapping.Conversation;
import DAO.UserDAO;
import bean.UserBean;

public class UserDAOImpl implements UserDAO{
	
	Conversation<UserBean> conver;
	//���캯��
	public UserDAOImpl(){
		conver = new Conversation<UserBean>(UserBean.class,"UserBean");
	}
	public UserBean queryUser(String userName){
	    //ʹ�÷�װ�õĲ������ݿ�������ģ�Ͳ���
		UserBean user = conver.getUser("userName", userName);
		
		return user;
	}
	public boolean insertUser(UserBean user){
		return false;
	}
	public boolean updateUser(UserBean user){
		return false;
	}
	public boolean deleteUser(UserBean user){
		return false;
	}
}
