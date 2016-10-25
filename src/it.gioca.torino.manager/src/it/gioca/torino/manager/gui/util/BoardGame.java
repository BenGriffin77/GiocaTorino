package it.gioca.torino.manager.gui.util;

import java.util.ArrayList;
import java.util.List;

public class BoardGame extends TinyGame {

	private List<TinyGame> expansions;
	
	private String language;
	
	private boolean loaded = false;
	
	private String dimostrator;
	
	private int ownerID;
	
	private int exitId;
	
	private boolean withExpansions;
	
	private String ownerName;
	
	private int statusGame;
	
	private int id_document;
	
	private List<String> categories;
	
	public BoardGame(TinyGame game) {
		super(game.getGameId(), game.getName(), null);
	}
	
	public BoardGame(int gameId, String name, byte[] thumbnail) {
		super(gameId, name, thumbnail);
	}

	public List<TinyGame> getExpansions() {
		return expansions;
	}

	public void setExpansions(List<TinyGame> expansions) {
		this.expansions = expansions;
	}

	public void resetExpansion(){
		expansions = null;
		if(loaded)
			setStatus(GAMESTATUS.UPDATE);
	}
	public void addExpansion(TinyGame expansion){
	
		if(expansions==null)
			expansions = new ArrayList<TinyGame>();
		for(TinyGame tg: expansions)
			if(tg.getGameId() == expansion.getGameId())
				return;
		expansions.add(expansion);
	}

	public String getLanguage() {
		return (language==null || language.equalsIgnoreCase(""))?"ITALIANO":language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
		if(loaded)
			setStatus(GAMESTATUS.NO_ACTION);
	}

	public String getDimostrator() {
		return dimostrator;
	}

	public void setDimostrator(String dimostrator) {
		this.dimostrator = dimostrator;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public boolean isWithExpansions() {
		return withExpansions;
	}

	public void setWithExpansions(boolean withExpansions) {
		this.withExpansions = withExpansions;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getExitId() {
		return exitId;
	}

	public void setExitId(int exitId) {
		this.exitId = exitId;
	}
	
	public int getStatusGame() {
		return statusGame;
	}

	public void setStatusGame(int statusGame) {
		this.statusGame = statusGame;
	}

	public int getId_document() {
		return id_document;
	}

	public void setId_document(int id_document) {
		this.id_document = id_document;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public boolean containsCategory(String value){
		
		if(categories==null || categories.size()==0)
			return false;
		for(String cat: categories)
			if(cat.equalsIgnoreCase(value))
				return true;
		return false;
	}

}
