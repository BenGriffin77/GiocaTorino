package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.gui.util.BoardGame;

import java.sql.Blob;
import java.sql.SQLException;

public class ShowTheExitGameFacade extends ConnectionManager {

	private BoardGame game;
	
	public ShowTheExitGameFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 10);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, req.getIdExit());
			rset = pstmt.executeQuery();
			if(rset!=null){
				int idGame;
				String name;
				byte[] thumbnail;
				BoardGame bg; 
				while(rset.next()){
					idGame = rset.getInt("ID_GAME");
					name = rset.getString("NAME");
					Blob image = rset.getBlob("thumbnail");
					if(image!=null){
			    		thumbnail = image.getBytes(1, (int)image.length());
			    		bg = new BoardGame(idGame, name, thumbnail);
			    		bg.setOwnerID(rset.getInt("OWNERID"));
			    		game = bg;
			    		image.free();
			    	}
			    	else{
			    		bg = new BoardGame(idGame, name, null);
			    		bg.setOwnerID(rset.getInt("OWNERID"));
			    		game = bg;
			    	}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public BoardGame getGame(){
		return game;
	}
}
