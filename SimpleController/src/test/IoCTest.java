package test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import servlet.action.LoginAction;
import di.IoCContainer;

public class IoCTest {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		// TODO Auto-generated method stub
		IoCContainer ioc = new IoCContainer();
		//测试isDenendent方法
		if(ioc.isDependent("login")){
			System.out.println("fuck");
		}
		//构造需要的requestMap参数，测试inject方法
		Map<String, String[]> requestMap = new HashMap<String, String[]>();
		String[] a = {"java8","java7"};
		requestMap.put("userName",a);
		requestMap.put("password",a);
		ioc.setRequestMap(requestMap);
		LoginAction test = (LoginAction) ioc.inject("login");
		System.out.println(test.getUserBean().getPassword());
	}

}
