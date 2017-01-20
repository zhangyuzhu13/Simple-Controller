package bean;


import DAOImpl.UserDAOImpl;

public class UserBean {
	
	private String userName;//用户名
	private String password;//密码
	
	public boolean login(){
		/*
		 * 验证用户登录信息是否有效
		 */
	 
		//创建数据库连接类
		UserDAOImpl dao = new UserDAOImpl();
		//连接数据库
		
		//取得数据库查询结果，并以bean的形式返回
		UserBean bean = dao.queryUser(userName);
		//关闭数据库连接
		
		if(bean == null)
			return false;
		//比较输入的密码与数据库传回密码是否一致
		if(bean.getPassword().equals(password)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
