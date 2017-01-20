package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  

import di.IoCContainer;
import servlet.interceptor.LogWriter;
import util.LogHandler;
import util.XmlFriend;
import bean.LogContent;
import bean.UserBean;

public class LoginController extends HttpServlet {
	/*
	 * 实现controller功能，对前段发送来的action进行解析匹配model
	 * 配置ioc和interceptor功能
	 */
	XmlFriend controllerXml;
	IoCContainer ioc;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		//读取xml配置文件只需要读取一次，所以放在init（）方法里，在工程初始化时读取一次，可以提高效率。
		controllerXml = new XmlFriend("E:/wodespace/SimpleController/src/controller.xml");
		//实例化ioc容器
		ioc = new IoCContainer();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  
		
		int num = request.getRequestURI().split("/").length;
		//取出action name
		String myActionName = request.getRequestURI().split("/")[num-1].split("\\.")[0];
		//在XML中查找相应action映射的class和method
		Map<String,String> classAndMethod = controllerXml.findAction(myActionName);
		String result = null;
		Map<String, String[]> requestMap = request.getParameterMap();
		HashMap interceptorMap;
		try{
		if(classAndMethod!=null){
			//按照classAndMethod使用反射
			Class<?>  actionClass = Class.forName(classAndMethod.get("class"));
			System.out.println(classAndMethod.get("class")+" "+classAndMethod.get("method"));
			Method actionMethod = actionClass.getDeclaredMethod(classAndMethod.get("method"));
			
			Object actionObj;
			//若需要进行依赖注入
			if(ioc.isDependent(myActionName)){
				 
				ioc.setRequestMap(requestMap);
				actionObj = ioc.inject(myActionName);
			}
			//不需要进行注入
			else{
				//将参数传入
				 
				actionObj = actionClass.newInstance();
			}
			result =(String) actionMethod.invoke(actionObj);
			/*
			 if((interceptorMap=controllerXml.findInterceptor())!=null){
				 //实例化写对数据对象，并将action存入
				 LogContent content = new LogContent();
				 content.setAction(myActionName);
				//实例化代理类，并将其与action对象绑定，
				LogHandler handler = new LogHandler(actionObj);
				//将interceptorMap传入handler
				handler.setInterceptorMap(interceptorMap);
				content.setS_time(new java.util.Date());
				//将写入数据LogContent传入handler
				handler.setContent(content);
				//生成代理
				Object proxy = Proxy.newProxyInstance(actionClass.getClassLoader(),actionClass.getInterfaces(), handler);
				//调用要执行的action方法，并将代理类传入
				result = (String) actionMethod.invoke(actionObj);
				
				content.setResult(result);
				content.setE_time(new java.util.Date());
				
				Class<?> interceptor = Class.forName((String) interceptorMap.get("class"));
				Object interObj = interceptor.newInstance();
				String interMethodName = (String)interceptorMap.get("method");
				Method interMethod = interceptor.getDeclaredMethod(interMethodName, LogContent.class);
				interMethod.invoke(interObj, content);
				
				 System.out.println(result);
			 }
			 else{
				 result =(String) actionMethod.invoke(actionObj);
			 }
			 */
		}
		else{
			//response a unfinded request
			System.out.println("unknown action");
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String[] resultElements = new String[2];
		//find a result to match
		if(result != null){
			resultElements = controllerXml.findResult(result);
		}
		else{
			//no result match..
			System.out.println("no result match");
		}
		//根据resultElements 数组返回的值进行response
		if(resultElements[0]!=null){
			RequestDispatcher dispatcher;
			//如果结果是forward，分发结果
			if(resultElements[0].equals("forward")){
				dispatcher = request.getRequestDispatcher(resultElements[1]);
				dispatcher.forward(request, response);
			}
			//如果result是redirect，进行redirect
			else if(resultElements[0].equals("redirect")){
				response.sendRedirect(resultElements[1]);
			}
		}
		
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		doGet(request,response);
	} 
    
	
	
	
	
	
}
