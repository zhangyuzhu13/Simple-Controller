package DAOImpl;
import java.sql.*;

import ormapping.Conversation;
import DAO.UserDAO;
import bean.UserBean;

public class UserDAOImpl implements UserDAO{
	
	Conversation<UserBean> conver;
	//构造函数
	public UserDAOImpl(){
		conver = new Conversation<UserBean>(UserBean.class,"UserBean");
	}
	public UserBean queryUser(String userName){
	    //使用封装好的操作数据库对象进行模型操作
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
