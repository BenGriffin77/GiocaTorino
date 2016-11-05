package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;

public class ManageUserRequest implements IRequestDTO{

	private UserStatus userStatus; 
	
	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
