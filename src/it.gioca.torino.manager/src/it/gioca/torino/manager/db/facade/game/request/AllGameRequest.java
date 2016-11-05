package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class AllGameRequest implements IRequestDTO {

	private RequestTYPE type;
	private String filterName;
	
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


	public String getFilterName() {
		return filterName;
	}


	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
}
