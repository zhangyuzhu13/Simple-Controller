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
	 * ʵ��controller���ܣ���ǰ�η�������action���н���ƥ��model
	 * ����ioc��interceptor����
	 */
	XmlFriend controllerXml;
	IoCContainer ioc;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		//��ȡxml�����ļ�ֻ��Ҫ��ȡһ�Σ����Է���init����������ڹ��̳�ʼ��ʱ��ȡһ�Σ��������Ч�ʡ�
		controllerXml = new XmlFriend("E:/wodespace/SimpleController/src/controller.xml");
		//ʵ����ioc����
		ioc = new IoCContainer();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  
		
		int num = request.getRequestURI().split("/").length;
		//ȡ��action name
		String myActionName = request.getRequestURI().split("/")[num-1].split("\\.")[0];
		//��XML�в�����Ӧactionӳ���class��method
		Map<String,String> classAndMethod = controllerXml.findAction(myActionName);
		String result = null;
		Map<String, String[]> requestMap = request.getParameterMap();
		HashMap interceptorMap;
		try{
		if(classAndMethod!=null){
			//����classAndMethodʹ�÷���
			Class<?>  actionClass = Class.forName(classAndMethod.get("class"));
			System.out.println(classAndMethod.get("class")+" "+classAndMethod.get("method"));
			Method actionMethod = actionClass.getDeclaredMethod(classAndMethod.get("method"));
			
			Object actionObj;
			//����Ҫ��������ע��
			if(ioc.isDependent(myActionName)){
				 
				ioc.setRequestMap(requestMap);
				actionObj = ioc.inject(myActionName);
			}
			//����Ҫ����ע��
			else{
				//����������
				 
				actionObj = actionClass.newInstance();
			}
			result =(String) actionMethod.invoke(actionObj);
			/*
			 if((interceptorMap=controllerXml.findInterceptor())!=null){
				 //ʵ����д�����ݶ��󣬲���action����
				 LogContent content = new LogContent();
				 content.setAction(myActionName);
				//ʵ���������࣬��������action����󶨣�
				LogHandler handler = new LogHandler(actionObj);
				//��interceptorMap����handler
				handler.setInterceptorMap(interceptorMap);
				content.setS_time(new java.util.Date());
				//��д������LogContent����handler
				handler.setContent(content);
				//���ɴ���
				Object proxy = Proxy.newProxyInstance(actionClass.getClassLoader(),actionClass.getInterfaces(), handler);
				//����Ҫִ�е�action���������������ഫ��
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
		//����resultElements ���鷵�ص�ֵ����response
		if(resultElements[0]!=null){
			RequestDispatcher dispatcher;
			//��������forward���ַ����
			if(resultElements[0].equals("forward")){
				dispatcher = request.getRequestDispatcher(resultElements[1]);
				dispatcher.forward(request, response);
			}
			//���result��redirect������redirect
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
