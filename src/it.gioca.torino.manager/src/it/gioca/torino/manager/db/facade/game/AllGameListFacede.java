package it.gioca.torino.manager.db.facade.game;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.AllGameRequest;
import it.gioca.torino.manager.gui.util.BoardGame;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllGameListFacede extends ConnectionManager {

	private List<BoardGame> games;
	
	public AllGameListFacede(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		AllGameRequest req = (AllGameRequest)request;
		int status = 0;
		switch(req.getType()){
		case IN: status = 0; break;
		case OUT: status = 2; break;
		case ALL: status = -1; break;
		}
		
		games = new ArrayList<BoardGame>();
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", status==-1? 11: 1);
		try{
			pstmt = conn.prepareStatement(query);
			if(status!=-1)
				pstmt.setInt(1, status);
			rset = pstmt.executeQuery();
			if(rset!=null){
				int gameId;
				String name;
				String language;
				byte[] thumbnail;
				String user;
				BoardGame bg;
				int exitId;
				int ownerId;
				int id_document;
				int statusGame;
				while(rset.next()){
					gameId = rset.getInt("ID_GAME");
					name = rset.getString("NAME");
					language = rset.getString("LANGUAGE");
					Blob image = rset.getBlob("thumbnail");
					user = rset.getString("USERNAME");
					exitId = rset.getInt("ID_EXIT");
					ownerId = rset.getInt("OWNERID");
					id_document = rset.getInt("ID_EXIT");
					statusGame = rset.getInt("STATUS");
					if(image!=null){
			    		thumbnail = image.getBytes(1, (int)image.length());
			    		bg = new BoardGame(gameId, name, thumbnail);
			    		bg.setDimostrator(user);
			    		bg.setExitId(exitId);
			    		bg.setOwnerID(ownerId);
			    		bg.setId_document(id_document);
			    		bg.setStatusGame(statusGame);
			    		//bg.setLanguage(language);
			    		games.add(bg);
			    		image.free();
			    	}
			    	else{
			    		bg = new BoardGame(gameId, name, null);
			    		bg.setDimostrator(user);
			    		bg.setExitId(exitId);
			    		bg.setOwnerID(ownerId);
			    		//bg.setLanguage(language);
			    		games.add(bg);
			    	}
				}
			}
			
			if(games.size()>0)
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
					query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 7);
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, bg.getOwnerID());
					rset = pstmt.executeQuery();
					if(rset!=null)
						while(rset.next()){
							String ownername = rset.getString("USERNAME");
							bg.setOwnerName(ownername);
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
