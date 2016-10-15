package it.gioca.torino.manager.gui.toylibrity;

import java.util.List;

import it.gioca.torino.manager.common.ObjectModel;
import it.gioca.torino.manager.gui.util.BoardGame;

public class FinishTheCheckOutObjectModel extends ObjectModel {

	private static final long serialVersionUID = 1L;
	
	private BoardGame boardGameSelected;
	
	private List<BoardGame> list;

	public BoardGame getBoardGameSelected() {
		return boardGameSelected;
	}

	public void setBoardGameSelected(BoardGame boardGameSelected) {
		this.boardGameSelected = boardGameSelected;
	}

	public List<BoardGame> getList() {
		return list;
	}

	public void setList(List<BoardGame> list) {
		this.list = list;
	}

}
