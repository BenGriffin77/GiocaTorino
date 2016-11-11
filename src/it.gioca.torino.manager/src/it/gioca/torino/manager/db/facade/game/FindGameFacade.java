package it.gioca.torino.manager.db.facade.game;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.RequestFindGame;
import it.gioca.torino.manager.gui.util.BoardGame;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindGameFacade extends ConnectionManager {

	private List<BoardGame> list;
	
	public FindGameFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestFindGame req = (RequestFindGame)request;
		int idGame = req.getGameId();
		String gameName = req.getGameName();
		String designer = req.getDesigner();
		boolean expansion = req.isExpansions();
		
		list = new ArrayList<BoardGame>();
		if(!gameName.equalsIgnoreCase("")){
			try{
				String query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", expansion? 2:3);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, gameName+"%");
				addGame();
				query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", 8);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, gameName+"%");
				List<Integer> ids_games = new ArrayList<Integer>();
				rset = pstmt.executeQuery();
				if(rset!=null)
					while(rset.next()){
						ids_games.add(rset.getInt("ID_GAME"));
					}
				if(ids_games.size()>0){
					Iterator<Integer> i = ids_games.iterator();
					while (i.hasNext()) {
						int bgToRemove = i.next();
						for(BoardGame bg: list){
							if(bg.getGameId()==bgToRemove)
								i.remove();
						}
					}
				}
				if(ids_games.size()>0){
					for(int value: ids_games){
						query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", expansion? 0:1);
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, value);
						addGame();
					}
				}
			}	catch (SQLException e) {
					e.printStackTrace();
			}
			return;
		}
		if(!designer.equalsIgnoreCase("")){
			try{
				String query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", expansion? 4:5);
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+designer+"%");
				addGame();
			}	catch (SQLException e) {
					e.printStackTrace();
			}
			return;
		}
		if(idGame!=0){
			try{
				String query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", expansion? 0:1);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, idGame);
				addGame();
			}	catch (SQLException e) {
					e.printStackTrace();
			}
			return;
		}
	}
	
	private void addGame() throws SQLException{
		
		rset = pstmt.executeQuery();
		if(rset!=null){
			int gameId;
			String name;
			byte[] thumbnail;
		    while(rset.next()){
		    	gameId = rset.getInt("ID");
		    	name = rset.getString("NAME");
		    	Blob image = rset.getBlob("thumbnail");
		    	if(image!=null){
		    		thumbnail = image.getBytes(1, (int)image.length());
		    		list.add(new BoardGame(gameId, name, thumbnail));
		    		image.free();
		    	}
		    	else
		    		list.add(new BoardGame(gameId, name, null));
		    }
		}
	}

	public List<BoardGame> getList() {
		return list;
	}

}
