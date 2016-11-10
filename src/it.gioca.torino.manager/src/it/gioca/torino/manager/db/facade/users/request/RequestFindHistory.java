package it.gioca.torino.manager.db.facade.users.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestFindHistory implements IRequestDTO {

	private String[] users;
	private int gameId;
	private String owner;
	
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
}
