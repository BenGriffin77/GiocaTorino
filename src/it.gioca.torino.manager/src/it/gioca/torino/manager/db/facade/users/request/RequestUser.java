package it.gioca.torino.manager.db.facade.users.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestUser implements IRequestDTO {

	private String userName;
	
	private boolean checkGame;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isCheckGame() {
		return checkGame;
	}

	public void setCheckGame(boolean checkGame) {
		this.checkGame = checkGame;
	}
}
