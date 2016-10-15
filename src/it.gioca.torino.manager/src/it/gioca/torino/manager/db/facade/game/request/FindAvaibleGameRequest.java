package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class FindAvaibleGameRequest implements IRequestDTO {

	private int gameId;
	
	private String gameName;
	
	private String designer;
	
	private String language;
	
	private TYPE requestType;
	
	public enum TYPE{
		ID,
		NAME,
		DESIGNER,
		LANGUAGE,
		FULL
	}

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public TYPE getRequestType() {
		return requestType;
	}

	public void setRequestType(TYPE requestType) {
		this.requestType = requestType;
	}
}
