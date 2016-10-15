package it.gioca.torino.manager.db.facade.game.request;

import it.gioca.torino.manager.db.IRequestDTO;

public class GameRequest implements IRequestDTO {

	private String gameName;
	
	private int idGame;
	
	private int ownerId;
	
	private boolean withExpansion;
	
	private GAMESTATUS status;
	
	private int idExit;
	
	private int demostratorId = 1; //Gioca torino standard User
	
	public enum GAMESTATUS{
		
		FREE,
		BLOCK,
		OUT
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isWithExpansion() {
		return withExpansion;
	}

	public void setWithExpansion(boolean withExpansion) {
		this.withExpansion = withExpansion;
	}

	public GAMESTATUS getStatus() {
		return status;
	}

	public void setStatus(GAMESTATUS status) {
		this.status = status;
	}

	public int getIdExit() {
		return idExit;
	}

	public void setIdExit(int idExit) {
		this.idExit = idExit;
	}

	public int getDemostratorId() {
		return demostratorId;
	}

	public void setDemostratorId(int demostratorId) {
		this.demostratorId = demostratorId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
}
