package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindCategory extends ConnectionManager {

	private List<String> elements;
	private HashMap<Integer, List<String>> correctElements;
	
	public FindCategory(IRequestDTO request) {
		super(request);
	}
	
	public String[] getElements(){
		String []dsf = new String[elements.size()];
		return elements.toArray(dsf);
	}
	
	public List<String> getElementById(int idgame){
		
		return correctElements.get(idgame);
	}
	
	@Override
	protected void execute(IRequestDTO request) {
		
		elements = new ArrayList<String>();
		correctElements = new HashMap<Integer, List<String>>();
		RequestFind req = (RequestFind)request;
		for(Integer idgame: req.getGamesIds()){
			switch(req.getType()){
			case CATEGORY: addElements(getRsetByCategory(idgame)); break;
			}
		}
	}
	
	private void addElements(List<String> downloaded){
		
		for(String down: downloaded){
			boolean isPresent = false;
			if(elements.size()>0)
				for(String cat: elements){
					if(down.equalsIgnoreCase(cat)){
						isPresent = true;
						continue;
					}
				}
			if(!isPresent)
				elements.add(down);
		}
	}

	private List<String> getRsetByCategory(int gameId){
		
		List<String> ret = new ArrayList<String>();
		String query = SingletonQuery.getInstance().getQuery("SEARCH", 0);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gameId);
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
					ret.add(rset.getString("CATEGORY_NAME"));
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(!correctElements.containsKey(gameId))
			correctElements.put(gameId, ret);
		return ret;
	}
}
