package it.gioca.torino.manager.db.facade.history;

public class HistoryItem {

	private String gameName;
	
	private String userName;
	
	private int times;
	
	private int maxPlayTime;
	
	private int minPlayTime;
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getMaxPlayTime() {
		return maxPlayTime;
	}

	public void setMaxPlayTime(int maxPlayTime) {
		this.maxPlayTime = maxPlayTime;
	}

	public int getMinPlayTime() {
		return minPlayTime;
	}

	public void setMinPlayTime(int minPlayTime) {
		this.minPlayTime = minPlayTime;
	}
}
