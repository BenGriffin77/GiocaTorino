package it.gioca.torino.manager.gui.util;

public class ColumnType {

	private String name;
	
	private CTYPE type;
	
	private int size;
	
	public ColumnType(String name, CTYPE type, int size) {
		
		this.name = name;
		this.type = type;
		this.size = size;
	}
	
	public ColumnType(String name, CTYPE type) {
		
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	
	public CTYPE getType(){
		return type;
	}
	
	public int getSize(){
		return size;
	}
	
	public enum CTYPE{
		
		TEXT,
		INT,
		IMAGE
	}
}
