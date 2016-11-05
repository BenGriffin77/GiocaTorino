package it.gioca.torino.manager.db.facade.game.request;

import java.util.List;

import it.gioca.torino.manager.db.IRequestDTO;

public class RequestLeaveWithSameGames implements IRequestDTO {

	private List<Integer> ids;
	
	private int ownerId;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
}
