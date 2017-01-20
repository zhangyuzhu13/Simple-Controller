package util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bean.LogContent;

public class LogHandler implements InvocationHandler {

	/*
	 * 在action执行前后进行处理，实现拦截器的功能
	 * 记录action执行前的时间，执行后的时间
	 */
	private Object object;
	private String actionName;
	private HashMap interceptorMap;
	private LogContent content ;
	
	public LogHandler(Object object){
		this.object = object;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//content为记录信息的对象，将时间和actionName记录
		content.setS_time(new java.util.Date());
		System.out.println("interceptoring!!!!!!!!");
		//执行真正的action
		String result = (String) method.invoke(object, args);
		
		content.setResult(result);
		content.setE_time(new java.util.Date());
		
		
		System.out.println("interceptor success!!!!!!!!");
		
		return null;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public HashMap getInterceptorMap() {
		return interceptorMap;
	}

	public void setInterceptorMap(HashMap interceptroMap) {
		this.interceptorMap = interceptroMap;
	}

	public LogContent getContent() {
		return content;
	}

	public void setContent(LogContent content) {
		this.content = content;
	}
	

}
