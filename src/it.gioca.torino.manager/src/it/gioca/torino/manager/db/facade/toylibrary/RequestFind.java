package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.IRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class RequestFind implements IRequestDTO {

	private List<Integer> gamesIds = new ArrayList<Integer>();
	
	private SearchType type;

	public List<Integer> getGamesIds() {
		return gamesIds;
	}

	public void setGamesIds(List<Integer> gamesIds) {
		this.gamesIds = gamesIds;
	}
	
	public void addGameId(int id){
		this.gamesIds.add(id);
	}

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}
	
}
