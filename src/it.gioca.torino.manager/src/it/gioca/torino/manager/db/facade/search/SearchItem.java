package it.gioca.torino.manager.db.facade.search;

public class SearchItem {

	private SEARCHTYPE type;
	
	private int int_val1;
	
	private int int_val2;
	
	private String string_val;
	
	private CONDITIONTYPE condition;
	
	public SearchItem(SEARCHTYPE type, int int_val1, CONDITIONTYPE condition) {
		this.type = type;
		this.int_val1 = int_val1;
		this.condition = condition;
	}
	
	public SearchItem(SEARCHTYPE type, int int_val1, int int_val2, CONDITIONTYPE condition) {
		this.type = type;
		this.int_val2 = int_val2;
		this.condition = condition;
	}
	
	public SearchItem(SEARCHTYPE type, String string_val) {
		this.type = type;
		this.string_val = string_val;
		this.condition = CONDITIONTYPE.LIKE;
	}
	
	public int[] getIVals(){
		return new int[] {int_val1,int_val2};
	}
	
	public int getIVal1(){
		return int_val1;
	}
	
	public String getSVal(){
		return string_val;
	}
	
	public SEARCHTYPE getType(){
		return type;
	}
	
	public CONDITIONTYPE getCondition(){
		return condition;
	}
}
