package it.gioca.torino.manager.db.facade.game;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.FindAvaibleGameRequest;
import it.gioca.torino.manager.gui.util.BoardGame;

public class FindAvabileGameFacade extends ConnectionManager {

	private List<BoardGame> games;
	
	public FindAvabileGameFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		games = new ArrayList<BoardGame>();
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 2);
		FindAvaibleGameRequest req = (FindAvaibleGameRequest)request;
		switch(req.getRequestType()){
			case FULL: break;
			case ID: query = query.concat(" AND boardgame_status.ID_GAME=?"); break;
			case NAME: query = query.concat(" AND giocatorino.boardgames.NAME LIKE ?"); break;
			case DESIGNER: query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 12); break;
			case LANGUAGE: query = query.concat(" AND giocatorino.boardgame_status.LANGUAGE=?"); break;
		}
		try{
			pstmt = conn.prepareStatement(query);
			switch(req.getRequestType()){
				case ID: pstmt.setInt(1, req.getGameId()); break;
				case NAME: pstmt.setString(1, req.getGameName()+"%"); break;
				case DESIGNER: pstmt.setString(1, req.getDesigner()+"%"); break;
				case LANGUAGE: pstmt.setString(1, req.getLanguage()); break;
				case FULL: break;
			}
			rset = pstmt.executeQuery();
			if(rset!=null){
				int idGame;
				String name;
				String language;
				byte[] thumbnail;
				BoardGame bg;
				while(rset.next()){
					idGame = rset.getInt("ID_GAME");
					name = rset.getString("NAME");
					language = rset.getString("LANGUAGE");
					Blob image = rset.getBlob("thumbnail");
					if(image!=null){
			    		thumbnail = image.getBytes(1, (int)image.length());
			    		bg = new BoardGame(idGame, name, thumbnail);
			    		bg.setLanguage(rset.getString("LANGUAGE"));
			    		bg.setOwnerID(rset.getInt("OWNERID"));
			    		bg.setOwnerName(rset.getString("USERNAME"));
			    		games.add(bg);
			    		image.free();
			    	}
			    	else{
			    		bg = new BoardGame(idGame, name, null);
			    		bg.setLanguage(rset.getString("LANGUAGE"));
			    		bg.setOwnerID(rset.getInt("OWNERID"));
			    		bg.setOwnerName(rset.getString("USERNAME"));
			    		games.add(bg);
			    	}
				}
			}
			
			if(games!=null && games.size()>0)
				for(BoardGame bg: games){
					query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 3);
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, bg.getGameId());
					pstmt.setInt(2, bg.getOwnerID());
					rset = pstmt.executeQuery();
					if(rset!=null)
						while(rset.next()){
							int exps = rset.getInt("EXPANSIONS");
							bg.setWithExpansions(exps>0);
							continue;
						}
				}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	public List<BoardGame> getGames(){
		return games;
	}
}
