package servlet.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import bean.UserBean;

public class LoginAction {
	
	UserBean userBean;
	
	public  String login(){
		/*、
		 * 进行dependency injection而省略的代码
		String userName = requestMap.get("userName")[0];
		String password = requestMap.get("password")[0];
		
		userBean = new UserBean();
		userBean.setUserName(userName);
		userBean.setPassword(password);
		*/
		if(userBean.login())
			return "success";
		else
			return "fail";
	}

	public   UserBean getUserBean() {
		return userBean;
	}

	public  void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	 

	

	
	
}
