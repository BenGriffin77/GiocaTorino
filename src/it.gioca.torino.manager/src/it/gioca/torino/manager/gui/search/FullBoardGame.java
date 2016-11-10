package it.gioca.torino.manager.gui.search;


import it.gioca.torino.manager.gui.util.BoardGame;

public class FullBoardGame extends BoardGame {

	private int minPlayers;
	
	private int maxPlayers;
	
	private int minTime;
	
	private int maxTime;
	
	private int age;
	
	
	public FullBoardGame(int gameId, String name, byte[] thumbnail) {
		super(gameId, name, thumbnail);
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

//	public List<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}

}
