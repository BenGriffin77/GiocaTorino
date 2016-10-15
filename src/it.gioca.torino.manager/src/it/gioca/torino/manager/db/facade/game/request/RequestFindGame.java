package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestFindGame implements IRequestDTO {

	private int gameId;
	
	private String gameName;
	
	private String designer;
	
	private boolean expansions;

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public boolean isExpansions() {
		return expansions;
	}

	public void setExpansions(boolean expansions) {
		this.expansions = expansions;
	}
}
