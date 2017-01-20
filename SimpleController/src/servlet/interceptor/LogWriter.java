package servlet.interceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import bean.LogContent;
import util.XmlFriend;

public class LogWriter {
	
	 
	Document document;
	static SimpleDateFormat time;
	boolean isNewline = true;
	
	public void log(LogContent content){
		SAXReader reader = new SAXReader();  
	    Document document = null;
	    File file = new File("E:/wodespace/SimpleController/src/servlet/interceptor/log.xml");
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		}  
		Element  root = document.getRootElement(); 
		time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		document.setXMLEncoding("UTF-8");
		Element log = document.getRootElement();
		Element action=log.addElement("action");
		action.addElement("name").addText(content.getAction());
		action.addElement("s-time").addText(""+time.format(content.getS_time()));
		action.addElement("e-time").addText(""+time.format(content.getE_time()));
		action.addElement("result").addText(content.getResult());
		
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream("E:/wodespace/SimpleController/src/servlet/interceptor/log.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    OutputStreamWriter osw=null;
		try {
			osw = new OutputStreamWriter(fos,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    OutputFormat of = new OutputFormat();  
	    of.setEncoding("GBK");  
	    of.setIndent(true);  
	    //of.setIndent("    ");  
	    if(isNewline){
	    	of.setNewlines(isNewline);
	    	isNewline = false;
	    }
	    XMLWriter writer = new XMLWriter(osw, of);  
	    try {
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
}
