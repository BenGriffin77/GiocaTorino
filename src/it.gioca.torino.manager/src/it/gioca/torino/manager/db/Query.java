package it.gioca.torino.manager.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Query implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> values = new ArrayList<String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addValue(int val, String element){
		
		values.add(val, element);
	}
	
	public String getQuery(int val){
		
		return values.get(val);
	}
}
