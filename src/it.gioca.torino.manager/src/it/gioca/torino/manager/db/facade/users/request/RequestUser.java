package it.gioca.torino.manager.db.facade.users.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestUser implements IRequestDTO {

	private String userName;
	private boolean checkGame;
	private int userId;
	private UserStatus userStatus;
	
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
