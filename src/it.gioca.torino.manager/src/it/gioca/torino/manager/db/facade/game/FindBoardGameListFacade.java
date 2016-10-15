package it.gioca.torino.manager.db.facade.game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.TinyGame;

public class FindBoardGameListFacade extends ConnectionManager {

	private List<BoardGame> boardGames;
	
	private HashMap<Integer, List<TinyGame>> expansions;
	
	public FindBoardGameListFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		boardGames = new ArrayList<BoardGame>();
		expansions = new HashMap<Integer, List<TinyGame>>();
		RequestUser req = (RequestUser)request;
		FindIDUserFacade fiuf = new FindIDUserFacade(req);
		int ret = fiuf.getId();
		try {
			String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES", 1);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, ret);
			rset = pstmt.executeQuery();
			BoardGame bg;
			if(rset!=null){
				while(rset.next()){
					int gameId = rset.getInt("ID_GAME");
					String name = rset.getString("NAME");
					String language = rset.getString("LANGUAGE");
					int id_mainGame = rset.getInt("ID_MAINGAME");
					if(id_mainGame==0){
						bg = new BoardGame(gameId, name, null);
						bg.setLanguage(language);
						bg.setLoaded(true);
						boardGames.add(bg);
					}
					else{
						List<TinyGame> list;
						if(expansions.containsKey(id_mainGame)){
							list = expansions.get(id_mainGame);
							list.add(new TinyGame(gameId, name, null));
						}
						else{
							list = new ArrayList<TinyGame>();
							list.add(new TinyGame(gameId, name, null));
							expansions.put(id_mainGame,list);
						}
					}
				}
			}
		} catch (SQLException e) {
		}
	}
	
	public List<BoardGame> getGames(){
		
		for(BoardGame bg: boardGames){
			if(expansions.containsKey(bg.getGameId()))
				bg.setExpansions(expansions.get(bg.getGameId()));
		}
		return boardGames;
	}

}
