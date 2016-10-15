package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class AllGameRequest implements IRequestDTO {

	private RequestTYPE type;
	
	
	public enum RequestTYPE{
		OUT,
		IN,
		ALL;
	}


	public RequestTYPE getType() {
		return type;
	}


	public void setType(RequestTYPE type) {
		this.type = type;
	}
}
