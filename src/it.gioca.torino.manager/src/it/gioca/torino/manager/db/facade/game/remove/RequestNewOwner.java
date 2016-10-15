package it.gioca.torino.manager.db.facade.game.remove;

import java.util.List;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestNewOwner implements IRequestDTO {

		
	private String newOwner;
	
	private List<Integer> games;

	public String getNewOwner() {
		return newOwner;
	}

	public void setNewOwner(String newOwner) {
		this.newOwner = newOwner;
	}

	public List<Integer> getGames() {
		return games;
	}

	public void setGames(List<Integer> games) {
		this.games = games;
	}
}
