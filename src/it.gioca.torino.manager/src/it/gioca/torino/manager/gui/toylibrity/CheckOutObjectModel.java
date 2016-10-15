package it.gioca.torino.manager.gui.toylibrity;

import java.util.List;

import it.gioca.torino.manager.common.ObjectModel;
import it.gioca.torino.manager.gui.util.BoardGame;

public class CheckOutObjectModel extends ObjectModel{

	private static final long serialVersionUID = 1L;
	
	private BoardGame boardGameSelected;
	
	private int idExit;
	
	private List<BoardGame> list;

	public BoardGame getBoardGameSelected() {
		return boardGameSelected;
	}

	public void setBoardGameSelected(BoardGame bg) {
		this.boardGameSelected = bg;
	}
	
	public void setBoardGameSelected(int gameId) {
		for(BoardGame bg: list){
			if(bg.getGameId()==gameId){
				boardGameSelected=bg;
				return;
			}
		}
	}

	public List<BoardGame> getList() {
		return list;
	}

	public void setList(List<BoardGame> list) {
		this.list = list;
	}

	public int getIdExit() {
		return idExit;
	}

	public void setIdExit(int idExit) {
		this.idExit = idExit;
	}

}
