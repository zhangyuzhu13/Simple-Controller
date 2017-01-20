package DAO;

import java.sql.Connection;

import bean.UserBean;

public interface UserDAO {

	//取得用户信息
	public UserBean queryUser(String userName);
	//插入新用户
	public boolean insertUser(UserBean user);
	//更新用户信息
	public boolean updateUser(UserBean user);
	//删除用户
	public boolean deleteUser(UserBean user);
}
