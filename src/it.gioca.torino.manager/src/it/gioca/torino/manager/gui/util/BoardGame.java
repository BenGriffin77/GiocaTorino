package it.gioca.torino.manager.gui.util;

import it.gioca.torino.manager.db.facade.toylibrary.GamePlayers;
import it.gioca.torino.manager.db.facade.toylibrary.GameTime;

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
	private List<String> designers;
	private int minTime;
	private int maxTime;
	private int minPlayers;
	private int maxPlayers;
	private List<String> alternativeNames;
	
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
<<<<<<< HEAD
		return this.language;
//		return (language==null || language.equalsIgnoreCase(""))?"ITALIANO":language;
=======
		return language;
>>>>>>> master
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

<<<<<<< HEAD
	public List<String> getDesigners() {
		return designers;
	}

	public void setDesigners(List<String> designers) {
		this.designers = designers;
	}
	
	public boolean containsDesigner(String value){
		
		if(designers==null || designers.size()==0)
			return false;
		for(String cat: designers)
			if(cat.equalsIgnoreCase(value))
				return true;
		return false;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	
	public void setTimes(GameTime gt){
		this.maxTime = gt.getMaxTime();
		this.minTime = gt.getMinTime();
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setPlayers(GamePlayers gp){
		this.minPlayers = gp.getMinPlayers();
		this.maxPlayers = gp.getMaxPlayers();
	}

	public List<String> getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(List<String> alternativeNames) {
		this.alternativeNames = alternativeNames;
	}
=======
>>>>>>> master
}
