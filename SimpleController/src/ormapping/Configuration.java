package ormapping;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class Configuration {
	
	Element root;
	
	public Configuration(){
		SAXReader reader = new SAXReader();  
	    Document document = null;
	    File file = new File("E:/wodespace/SimpleController/src/or_mapping.xml");
	    try {
			document = reader.read(file);
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		}  
		root = document.getRootElement(); 
	}
	public HashMap<String,String> getJDBC(){
		/*
		 * ��ȡjdbc������Ϣ����Map��ʽ�洢
		 */
		HashMap<String,String> jdbcMap = new HashMap<String,String>();
		Element jdbc = root.element("jdbc");
		for(Iterator  i = jdbc.elements("property").iterator();i.hasNext();){
			Element property = (Element) i.next();
			jdbcMap.put(property.element("name").getText(),property.element("value").getText());
		}
		return jdbcMap;
	}
	public HashMap<String,String> getTable(String className){
		HashMap<String,String> tableMap = new HashMap<String,String>();
		for(Iterator  i = root.elements("class").iterator();i.hasNext();){
			//�����Ƿ�����࣬�������ҳ���Ӧ����
			Element classInfo = (Element) i.next();
			if(!classInfo.element("name").getText().equals(className))
				continue;
			else{
				//����������
				tableMap.put("table",classInfo.element("table").getText());
				List columns = classInfo.elements("property");
				for(Iterator  j = classInfo.elements("property").iterator();j.hasNext();){
					Element property = (Element) j.next();
					//��¼��������Ӧ����
					tableMap.put(property.element("name").getText(), property.element("column").getText());
				}
				return tableMap;
			}
		}
		return null;
	}
}
