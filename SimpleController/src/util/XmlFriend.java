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
	 * ��ȡcontroller��xml��ƥ��action���ֺ�action�����Լ�result����
	 */
	private Element root;//�ļ���Ԫ��
	private Element myAction;
	
	public XmlFriend(String path){
		//��ʼ��ʱ����xml�ļ���ֻ����һ�ξ͹������Ч�ʡ�
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
		 * ����XML�����Ҷ�Ӧ��action��class ��method�����ַ���������ʽ���أ���û�з��ؿ��ַ�������.index 0 Ϊclass name��1Ϊ method name
		 */
		//String[] classAndMethod = new String[2];
		Map<String,String> classAndMethod = new HashMap<String,String>();	     
	    //������������allresource�µ�resourceitem����list��  
	    List list  = root.elements("action");
	    for(Iterator  i = list.iterator();i.hasNext();){
	    	Element action = (Element) i.next();
	    	//ȡ��action��name
	    	String actionName = action.element("name").getText();
	    	//�봫����name�Ƚϣ����Ƿ�����Ҫ��action
	    	if(myActionName.equals(actionName)){
	    		//�ҵ�action��class����class name��method����
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
		//��action��ƥ��result
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
				//���ҵ���ref��������ÿһ��interceptor��class��method��map��ʽ�洢
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

