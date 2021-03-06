package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.IRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class RequestFind implements IRequestDTO {

	private List<Integer> gamesIds = new ArrayList<Integer>();
	
	private List<SearchType> type = new ArrayList<SearchType>();

	public List<Integer> getGamesIds() {
		return gamesIds;
	}

	public void setGamesIds(List<Integer> gamesIds) {
		this.gamesIds = gamesIds;
	}
	
	public void addGameId(int id){
		this.gamesIds.add(id);
	}

	public List<SearchType> getType() {
		return type;
	}
	
	public void addType(SearchType type){
		this.type.add(type);
	}

	public void setType(List<SearchType> type) {
		this.type = type;
	}
	
}
