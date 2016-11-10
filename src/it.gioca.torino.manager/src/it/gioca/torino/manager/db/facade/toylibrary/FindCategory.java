package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

import java.sql.SQLException;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collections;
=======
>>>>>>> master
import java.util.HashMap;
import java.util.List;

public class FindCategory extends ConnectionManager {

<<<<<<< HEAD
	private List<String> categories;
	private List<String> designers;
	private HashMap<Integer, List<String>> catById;
	private HashMap<Integer, List<String>> desById;
	private HashMap<Integer, GameTime> gameTimeById;
	private HashMap<Integer, GamePlayers> gamePlayerById;
=======
	private List<String> elements;
	private HashMap<Integer, List<String>> correctElements;
>>>>>>> master
	
	public FindCategory(IRequestDTO request) {
		super(request);
	}
	
<<<<<<< HEAD
	public String[] getCategories(){
		String []dsf = new String[categories.size()];
		return categories.toArray(dsf);
	}
	
	public String[] getDesigners(){
		String []dsf = new String[designers.size()];
		return designers.toArray(dsf);
	}
	
	public List<String> getCatById(int idgame){
		return catById.get(idgame);
	}
	
	public List<String> getDesById(int idgame){
		return desById.get(idgame);
	}
	
	public GameTime getTimeById(int idgame){
		return gameTimeById.get(idgame);
	}
	
	public GamePlayers getPlayerById(int idgame){
		return gamePlayerById.get(idgame);
	}
	
	public int[] getMinMaxPlayer(){
		int min=1000, max=0;
		for(GamePlayers gp: gamePlayerById.values()){
			if(gp.getMinPlayers()<min)
				min = gp.getMinPlayers();
			if(gp.getMaxPlayers()>max)
				max = gp.getMaxPlayers();
		}
		return new int[]{min, max};
=======
	public String[] getElements(){
		String []dsf = new String[elements.size()];
		return elements.toArray(dsf);
	}
	
	public List<String> getElementById(int idgame){
		
		return correctElements.get(idgame);
>>>>>>> master
	}
	
	@Override
	protected void execute(IRequestDTO request) {
		
<<<<<<< HEAD
		categories = new ArrayList<String>();
		designers = new ArrayList<String>();
		catById = new HashMap<Integer, List<String>>();
		desById = new HashMap<Integer, List<String>>();
		gameTimeById = new HashMap<Integer, GameTime>();
		gamePlayerById = new HashMap<Integer, GamePlayers>();
		
		RequestFind req = (RequestFind)request;
		for(Integer idgame: req.getGamesIds()){
			for(SearchType st: req.getType())
			switch(st){
			case DESINER: 
			case CATEGORY: addElements(getRsetByCategory(idgame, st), st); break;
			case TIME: getRsetByCategory(idgame, st); break;
			case PLAYERS: getRsetByCategory(idgame, st); break;
			}
		}
		if(categories.size()>0)
			Collections.sort(categories.subList(1, categories.size()));
		if(designers.size()>0)
			Collections.sort(designers.subList(1, designers.size()));
	}
	
	private void addElements(List<String> downloaded, SearchType type){
		
		for(String down: downloaded){
			boolean isPresent = false;
			switch(type){
			case CATEGORY:{
				if(categories.size()>0)
					for(String cat: categories){
						if(down.equalsIgnoreCase(cat)){
							isPresent = true;
							continue;
						}
					}
				break;
			}
			case DESINER:{
				if(designers.size()>0)
					for(String cat: designers){
						if(down.equalsIgnoreCase(cat)){
							isPresent = true;
							continue;
						}
					}
				break;
			}
			default:break;
			}
			if(!isPresent){
				switch(type){
				case CATEGORY: categories.add(down); break;
				case DESINER: designers.add(down); break;
				default: break;
				}
			}
		}
	}

	private List<String> getRsetByCategory(int gameId, SearchType type){
		
		List<String> ret = new ArrayList<String>();
		int val = 0;
		String columnName = "";
		String columnNameBis = "";
		switch(type){
		case CATEGORY: val = 0; columnName = "CATEGORY_NAME"; break;
		case DESINER:  val = 1; columnName = "DESIGNER_NAME"; break;
		case TIME: val = 2; columnName = "MAXTIME"; columnNameBis="MINTIME"; break;
		case PLAYERS: val = 3; columnName = "MAXPL"; columnNameBis="MINPL"; break;
		}
		String query = SingletonQuery.getInstance().getQuery("SEARCH", val);
=======
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
>>>>>>> master
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gameId);
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
<<<<<<< HEAD
					switch(type){
					case TIME: {
						GameTime gt = new GameTime();
						gt.setMaxTime(rset.getInt(columnName));
						gt.setMinTime(rset.getInt(columnNameBis));
						gameTimeById.put(gameId, gt);
						break;
						}
					case PLAYERS:{
						GamePlayers gp = new GamePlayers();
						gp.setMaxPlayers(rset.getInt(columnName));
						gp.setMinPlayers(rset.getInt(columnNameBis));
						gamePlayerById.put(gameId, gp);
					}
					default: ret.add(rset.getString(columnName)); break; 
					}
=======
					ret.add(rset.getString("CATEGORY_NAME"));
>>>>>>> master
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
<<<<<<< HEAD
		switch(type){
		case CATEGORY:{
			if(!catById.containsKey(gameId))
				catById.put(gameId, ret);
			break;
		}
		case DESINER:{
			if(!desById.containsKey(gameId))
				desById.put(gameId, ret);
			break;
		}
		default: break;
		}
=======
		if(!correctElements.containsKey(gameId))
			correctElements.put(gameId, ret);
>>>>>>> master
		return ret;
	}
}
