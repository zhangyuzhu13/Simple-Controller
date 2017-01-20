package DAO;

import java.sql.Connection;

import bean.UserBean;

public interface UserDAO {

	//ȡ���û���Ϣ
	public UserBean queryUser(String userName);
	//�������û�
	public boolean insertUser(UserBean user);
	//�����û���Ϣ
	public boolean updateUser(UserBean user);
	//ɾ���û�
	public boolean deleteUser(UserBean user);
}
