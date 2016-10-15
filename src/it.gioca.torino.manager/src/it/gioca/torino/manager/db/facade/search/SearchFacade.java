package it.gioca.torino.manager.db.facade.search;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class SearchFacade extends ConnectionManager{

	public SearchFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		
		SearchRequest req = (SearchRequest)request;
		String query = SingletonQuery.getInstance().getQuery("SEARCH",0);
		
		String tmp ="";
		
		if(req.getItems()!=null && req.getItems().size()>0){
			for(SearchItem si: req.getItems()){
				tmp = tmp.concat(getCondition(si));
			}
		}
		try{
			pstmt = conn.prepareStatement(query.replace(key_condition, tmp));
			rset = pstmt.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private static final String key_condition ="%CONDITIONS%";
	
	private static final String TXT = ", AND giocatorino.boardgames.";
	
	private static String prepareCondition(SEARCHTYPE type) {
		
		String condition = "";
		switch(type){
		case AGE: return TXT+"AGE"+type.toString();
		case MINPLAYTIME: return TXT+"MINTIME"+type.toString();
		case MAXPLAYTIME: return TXT+"MAXTIME"+type.toString();
		case MINPLAY: return TXT+"MINPL"+type.toString();
		case MAXPLAY: return TXT+"MAXPL"+type.toString();
		default: condition = ""; break;
		}
		return condition;
	}

	private static String getCondition(SearchItem item){
		
		String condition = prepareCondition(item.getType());
		
		switch(item.getCondition()){
		case LIKE: return condition+item.getSVal()+"%";
		case MINOR: return condition+"<"+item.getIVal1();
		case EQUAL: return condition+"="+item.getIVal1();
		case MAJOR: return condition+">"+item.getIVal1();
		case BETWEEN: return condition+"<"+item.getIVals()[0]+" "+condition+">"+item.getIVals()[1];
		}
		return "";
	}
}
