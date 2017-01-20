package util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlFriend {
	/*
	 * 读取controller。xml，匹配action名字和action的类以及result类型
	 */
	private Element root;//文件根元素
	private Element myAction;
	
	public XmlFriend(String path){
		//初始化时载入xml文件，只载入一次就够，提高效率。
		SAXReader reader = new SAXReader();  
	    Document document = null;
	    File file = new File(path);
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		}  
		root = document.getRootElement(); 
		
	}
	
	public Map findAction(String myActionName ){
		/*
		 * 解析XML，查找对应的action的class 和method，以字符串数组形式返回，若没有返回空字符串数组.index 0 为class name，1为 method name
		 */
		//String[] classAndMethod = new String[2];
		Map<String,String> classAndMethod = new HashMap<String,String>();	     
	    //将解析出来的allresource下的resourceitem放在list中  
	    List list  = root.elements("action");
	    for(Iterator  i = list.iterator();i.hasNext();){
	    	Element action = (Element) i.next();
	    	//取得action的name
	    	String actionName = action.element("name").getText();
	    	//与传来的name比较，看是否是需要的action
	    	if(myActionName.equals(actionName)){
	    		//找到action的class，将class name和method传回
	    		myAction = action;
	    		Element myClass = action.element("class");
	    		classAndMethod.put("class", myClass.element("name").getText());
	    		classAndMethod.put("method", myClass.element("method").getText());
	    		break;
	    	}
	    }
		return classAndMethod ;
	}
	public String[] findResult(String myResult){
		//从action中匹配result
		String[] resultElements = new String[2];
		if(myAction == null){
			System.out.println("you should find action before!!");
			return null;
		}
		for(Iterator  i = myAction.elements("result").iterator();i.hasNext();){
			Element result = (Element) i.next();
			String name = result.element("name").getText();
			if(name.equals(myResult)){
				resultElements[0] = result.element("type").getText();
				resultElements[1] = result.element("value").getText();
				break;
			}
			
		}
		
		
		return resultElements;
	}
	public HashMap findInterceptor(){
		HashMap map = new HashMap();
		Element interceptorName = myAction.element("interceptor-ref");
		List interceptors = root.elements("interceptor");
		
		if(interceptorName == null){
			return null;
		}
		String name = interceptorName.element("name").getText();
			
			for(Iterator j = interceptors.iterator();j.hasNext();){
				//将找到的ref遍历，把每一个interceptor的class和method以map形式存储
				Element interceptor = (Element) j.next();
				if(interceptor.element("name").getText().equals(name)){
					Element iclass = interceptor.element("class");
					map.put("class",iclass.element("name").getText());
					map.put("method", iclass.element("method").getText());
					return map;
				}
			}
		
		return null;
	}
	
	
	

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public Element getMyAction() {
		return myAction;
	}

	public void setMyAction(Element myAction) {
		this.myAction = myAction;
	}
	
	
}

