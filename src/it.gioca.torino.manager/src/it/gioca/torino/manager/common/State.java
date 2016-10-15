package it.gioca.torino.manager.common;

import java.util.ArrayList;
import java.util.List;

public class State{
	
	private String id;
	
	private String className;
	
	private String title;
	
	private String classObjectModel;
	
	private List<Transition> transitions = new ArrayList<Transition>();
	
	public State(String id, String className, String title) {
		this.id = id;
		this.className = className;
		this.title = title;
	}
	
	public void addTransition(String id, String to){
		transitions.add(new Transition(id, to));
	}
	
	public String getNextFromTransition(String id){
		
		for(Transition t: transitions){
			if(t.getId().equalsIgnoreCase(id))
				return t.getTo();
		}
		return null;
	}
	
	public String getId(){
		return id;
	}
	
	public String getClassName(){
		return className;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getClassObjectModel() {
		return classObjectModel;
	}

	public void setClassObjectModel(String classObjectModel) {
		this.classObjectModel = classObjectModel;
	}

	/*
	 * Classe di supporto che definisce l'oggetto Transition
	 */
	private class Transition{
		
		private String id;
		
		private String to;
		
		public Transition(String id, String to) {
			this.id = id;
			this.to = to;
		}
		
		public String getId(){
			return id;
		}
		
		public String getTo(){
			return to;
		}
	}
}



