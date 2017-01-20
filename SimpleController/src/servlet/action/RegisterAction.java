package servlet.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RegisterAction {
	
	
	
	public String register(Map<String, String[]> requestMap){
		
		String userName = requestMap.get("userName")[0];
		String password = requestMap.get("password")[0];
		String rePassword = requestMap.get("repassword")[0];
		String email = requestMap.get("email")[0];
		
		if(userName.equals("a") || !password.equals(rePassword) || email.indexOf("@")==-1)
			return "fail";
		else
			return "success";
	}

	
	 
}