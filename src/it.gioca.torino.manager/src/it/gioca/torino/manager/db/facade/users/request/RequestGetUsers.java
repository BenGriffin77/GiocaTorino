package it.gioca.torino.manager.db.facade.users.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestGetUsers implements IRequestDTO {

	private boolean all;
	
	private boolean exit;

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
}
