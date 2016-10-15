package it.gioca.torino.manager;

import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ObjectModel;
import it.gioca.torino.manager.common.State;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Workflow {

	private boolean debug = true;
	
	private HashMap<String, State> states = new HashMap<String, State>();
	
	private HashMap<String, Object> instances = new HashMap<String, Object>();
	
	private HashMap<String, Object> models = new HashMap<String, Object>();
	
	private static final String start = "start"; //$NON-NLS-1$
	
	private Workflow() {
		parseXml();
	}
	
	public Object getFirstInstance(){
		
		Object instanceClass = classLoader(start);
		instances.put(start, instanceClass);
		return instanceClass;
	}
	
	public ObjectModel getObj(String key){
		
		return (ObjectModel)models.get(key);
	}
	
	private void secondDraw(Object instanceClass, String previusState, Composite centrale, Composite menuLaterale){
		
		if(!(instanceClass instanceof MainForm))
			return;
		
		MainForm form = (MainForm)instanceClass;
//		Display display = Display.getCurrent();
//		Shell shell = display.getActiveShell();
//		shell.setText(Messages.getString("Workflow.1")); //$NON-NLS-1$
//		for(Control control: form.getMainComposite().getChildren())
//			control.dispose();
		form.setMainComposite(centrale);
		form.setMenuLaterale(menuLaterale);
		form.clean();
		form.setDataModel();
		form.setPreviusForm(previusState);
		form.createFrom();
		form.layout();
//		form.createFrom(shell);
//		shell.layout();
		form.afterCreate();
	}
	
	public void next(String stateName, String transition, Composite centrale, Composite menuLaterale){
		String to = states.get(stateName).getNextFromTransition(transition);
		if(to == null){
			System.out.println(Messages.getString("Workflow.2")+stateName+Messages.getString("Workflow.3")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		
		State state = states.get(to);
		if(state == null){
			System.out.println(Messages.getString("Workflow.4")); //$NON-NLS-1$
			return;
		}
		for(String stateId: instances.keySet()){
			if(stateId.equals(state.getId())){
				if(debug){
					System.out.println(Messages.getString("Workflow.5").concat(" "+state.getTitle())); //$NON-NLS-1$
				}
				secondDraw(instances.get(stateId), stateName, centrale, menuLaterale);
				loadObjectModel(state);
				return;
			}
		}
		Object instanceClass = classLoader(state.getId());
		instances.put(state.getId(), instanceClass);
		loadObjectModel(state);
		secondDraw(instances.get(state.getId()),stateName, centrale, menuLaterale);	
	}
	
	private void loadObjectModel(State state){
		
		if(state.getClassObjectModel()==null)
			return;
		if(models.containsKey(state.getId()))
			return;
		Object instanceClass = classObjLoader(state.getClassObjectModel());
		models.put(state.getId(), instanceClass);
	}
	
	public String getnextName(String stateName, String transition){
		
		String to = states.get(stateName).getNextFromTransition(transition);
		if(to == null){
			System.out.println(Messages.getString("Workflow.6")); //$NON-NLS-1$
			return null;
		}
		
		State state = states.get(to);
		if(state == null){
			System.out.println(Messages.getString("Workflow.7")); //$NON-NLS-1$
			return null;
		}
		
		return state.getId();
	}
	
	/*
	 * Questo motodo carica XML con le transizioni
	 */
	private void parseXml(){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("Config/workflow.xml"); //$NON-NLS-1$
			for(Node n =document.getFirstChild(); n!=null; n = n.getNextSibling()){
				if("list".equalsIgnoreCase(n.getNodeName())){ //$NON-NLS-1$
					for(Node a = n.getFirstChild(); a != null; a = a.getNextSibling()){
						State state;
						String idState; 
						String className;
						String title;
						if("state".equalsIgnoreCase(a.getNodeName())){ //$NON-NLS-1$
							NamedNodeMap attrMap = a.getAttributes();
							idState = attrMap.getNamedItem("id").getNodeValue(); //$NON-NLS-1$
							className = attrMap.getNamedItem("class").getNodeValue(); //$NON-NLS-1$
							title = attrMap.getNamedItem("title").getNodeValue(); //$NON-NLS-1$
							state = new State(idState, className, title);
							
							String idTransition;
							String toTransition;
							String classObjectModel;
							for(Node b = a.getFirstChild(); b != null; b = b.getNextSibling()){
								if("transition".equalsIgnoreCase(b.getNodeName())){ //$NON-NLS-1$
									attrMap = b.getAttributes();
									idTransition = attrMap.getNamedItem("id").getNodeValue(); //$NON-NLS-1$
									toTransition = attrMap.getNamedItem("to").getNodeValue(); //$NON-NLS-1$
									state.addTransition(idTransition, toTransition);
								}
								if("objectModel".equalsIgnoreCase(b.getNodeName())){ //$NON-NLS-1$
									attrMap = b.getAttributes();
									classObjectModel = attrMap.getNamedItem("class").getNodeValue(); //$NON-NLS-1$
									state.setClassObjectModel(classObjectModel);
								}
							}
							states.put(state.getId(), state);
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
	
	
	private Object classObjLoader(String param){
		
		try {
			Class<?> clazzImpl = Class.forName(param);
			Constructor<?> ctor = clazzImpl.getDeclaredConstructor();
			final Object instance = ctor.newInstance();
			return instance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Questo metodo carica automaticamente le classi definite nel xml.
	 */
	private Object classLoader(String param){
		
		State state = states.get(param);
		try {
			Class<?> clazzImpl = Class.forName(state.getClassName());
			Constructor<?> ctor = clazzImpl.getDeclaredConstructor(String.class, String.class);
			if(debug)
				System.out.println(Messages.getString("Workflow.19")+state.getTitle()); //$NON-NLS-1$
			final Object instance = ctor.newInstance(state.getId(), state.getTitle());
			return instance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static final Workflow getInstace(){
		
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder{
		
		protected static final Workflow _instance = new Workflow();
	}
}
