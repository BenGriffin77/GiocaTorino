package it.gioca.torino.manager.db.facade.game.request;

import java.util.List;

import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.gui.util.BoardGame;

public class BoardGameRequest implements IRequestDTO {

	private List<BoardGame> boardgames;
	
	private String userName;

	public List<BoardGame> getBoardgames() {
		return boardgames;
	}

	public void setBoardgames(List<BoardGame> boardgames) {
		this.boardgames = boardgames;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
