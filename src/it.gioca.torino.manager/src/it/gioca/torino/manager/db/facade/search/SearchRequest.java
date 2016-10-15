package it.gioca.torino.manager.db.facade.search;

import java.util.List;

import it.gioca.torino.manager.db.IRequestDTO;

public class SearchRequest implements IRequestDTO {

	private List<SearchItem> items;

	public List<SearchItem> getItems() {
		return items;
	}

	public void setItems(List<SearchItem> items) {
		this.items = items;
	}
}
