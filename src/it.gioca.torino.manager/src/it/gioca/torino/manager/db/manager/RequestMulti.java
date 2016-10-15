package it.gioca.torino.manager.db.manager;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestMulti implements IRequestDTO {

	private boolean category;

	public boolean isCategory() {
		return category;
	}

	public void setCategory(boolean category) {
		this.category = category;
	}
}
