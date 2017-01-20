package di;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class IoCContainer {

	Element root;
	Map<String, String[]> requestMap;
	
	
	public IoCContainer(){
		SAXReader reader = new SAXReader();  
	    Document document = null;
	    File file = new File("E:/wodespace/SimpleController/src/di.xml");
	    try {
			document = reader.read(file);
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		}  
		root = document.getRootElement(); 
	}
	public boolean isDependent(String beanName){
		//�ж�action�Ƿ��������ע��
		for(Iterator i = root.elements("bean").iterator();i.hasNext();){
			Element bean = (Element) i.next();
			if(bean.element("name").getText().equals(beanName))
				return true;
		}
		return false;
	}
	
	public Object inject(String beanName ) 
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, IntrospectionException{
		List properties = null;
		Object obj = null;
		//Ѱ�ҵ�Ҫע���bean����ʵ����һ��bean����
		for(Iterator i = root.elements("bean").iterator();i.hasNext();){
			Element bean = (Element) i.next();
			if(bean.element("name").getText().equals(beanName)){
				 Class<?> actionClass = Class.forName(bean.element("class").getText());
				 obj = actionClass.newInstance();
				 properties = bean.elements("property");
				 break;
			}
		}
		//����ע��bean�еĸ������ԣ�ʹ�õݹ���ã���bean��������bean���еݹ�ע��
		Iterator p = properties.iterator();
		while(p.hasNext()){
			Element property = (Element) p.next();
			//����ǰԪ������ֵע�룬��ֱ��ʹ��Introspector����ע��
			if(property.element("ref-class")==null){
				String propertyName = property.element("name").getText();
				Object value = requestMap.get(propertyName)[0];
				setProperty(obj,propertyName,value);
			}
			//��������bean��ݹ����inject�����ٴι���ʵ������ע��
			else{
				String clazz = property.element("ref-class").getText();
				String propertyName = property.element("name").getText();
				 
				Object value = inject(clazz);
				setProperty(obj,propertyName,value);
			}
		}
	
		return obj;
	}
	private	static	void setProperty(Object clazz, String propertyName, Object value) 
							throws IntrospectionException,IllegalAccessException, InvocationTargetException{
	//����һ
		PropertyDescriptor pd=new PropertyDescriptor(propertyName, clazz.getClass());
		Method methodSet=pd.getWriteMethod();
		methodSet.invoke(clazz, value);
	/*
	 //������
		BeanInfo beanInfo=Introspector.getBeanInfo(clazz.getClass());
		PropertyDescriptor[] pds=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd:pds){
			if(propertyName.equals(pd.getName())){
				Method methodSet=pd.getWriteMethod();
				methodSet.invoke(clazz, value);
				break;
			}
		}
	}
	*/
	}
	public Map<String, String[]> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<String, String[]> requestMap) {
		this.requestMap = requestMap;
	}
}
