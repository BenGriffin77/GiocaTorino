package it.gioca.torino.manager.db.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GameElement {

	private int gameId;
	
	private List<String> names;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setNames(String names) {
		
		List<String> list = new ArrayList<String>();
		
		if(names!=null && !names.equalsIgnoreCase("")){
			StringTokenizer st = new StringTokenizer(names, ";");
			while (st.hasMoreTokens()){
				list.add(st.nextToken());
			}
		}
		
		this.names = list;
	}
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
