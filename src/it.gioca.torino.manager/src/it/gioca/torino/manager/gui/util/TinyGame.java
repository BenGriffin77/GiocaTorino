package it.gioca.torino.manager.gui.util;

public class TinyGame {

	private int gameId;
	
	private String name;
	
	private byte[] thumbnail;
	
	private GAMESTATUS status = GAMESTATUS.INSERT;
	
	public TinyGame(int gameId, String name, byte[] thumbnail) {
		this.gameId = gameId;
		this.name = name;
		this.thumbnail = thumbnail;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public GAMESTATUS getStatus() {
		return status;
	}

	public void setStatus(GAMESTATUS status) {
		this.status = status;
	}
}
