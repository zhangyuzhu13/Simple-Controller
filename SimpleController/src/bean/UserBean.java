package bean;


import DAOImpl.UserDAOImpl;

public class UserBean {
	
	private String userName;//�û���
	private String password;//����
	
	public boolean login(){
		/*
		 * ��֤�û���¼��Ϣ�Ƿ���Ч
		 */
	 
		//�������ݿ�������
		UserDAOImpl dao = new UserDAOImpl();
		//�������ݿ�
		
		//ȡ�����ݿ��ѯ���������bean����ʽ����
		UserBean bean = dao.queryUser(userName);
		//�ر����ݿ�����
		
		if(bean == null)
			return false;
		//�Ƚ���������������ݿ⴫�������Ƿ�һ��
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
