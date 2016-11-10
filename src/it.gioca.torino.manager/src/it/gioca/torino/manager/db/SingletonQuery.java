package it.gioca.torino.manager.db;

import it.gioca.torino.manager.Config;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SingletonQuery {

	private static SingletonQuery instance;
	
	private boolean debug = Config.DEBUG;
	
	private HashMap<String, Query> queries = new HashMap<String, Query>();
	
	private SingletonQuery() {
		load();
	}
	
	public static SingletonQuery getInstance(){
		
		if(instance == null)
			instance = new SingletonQuery();
		return instance;
	}
	
	public String getQuery(String name, int id){
		
		return queries.get(name).getQuery(id);
	}
	
	public String getQuery(String name){
		
		return getQuery(name, 0);
	}
	
	private void load(){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("Config/query.xml");
			for(Node n =document.getFirstChild(); n!=null; n = n.getNextSibling()){
				if("list".equalsIgnoreCase(n.getNodeName())){
					Query q = null;
					for(Node a = n.getFirstChild(); a != null; a = a.getNextSibling()){
						if("query".equalsIgnoreCase(a.getNodeName())){
							NamedNodeMap attrMap = a.getAttributes();
							q = new Query();
							q.setName(attrMap.getNamedItem("name").getNodeValue());
							for(Node b = a.getFirstChild(); b != null; b = b.getNextSibling()){
								if("value".equalsIgnoreCase(b.getNodeName())){
									attrMap = b.getAttributes();
									q.addValue(Integer.parseInt(attrMap.getNamedItem("id").getNodeValue()), b.getTextContent());
								}
							}
							if(q.getName() != null){
								if(debug){
									System.out.println("Nome: "+q.getName());
									System.out.println("Driver: "+q.getQuery(0));
								}
								queries.put(q.getName(), q);
							}
						}
					}
				}
			}
		}catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
