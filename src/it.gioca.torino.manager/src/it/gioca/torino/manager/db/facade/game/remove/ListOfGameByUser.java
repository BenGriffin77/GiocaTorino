package it.gioca.torino.manager.db.facade.game.remove;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;

public class ListOfGameByUser extends ConnectionManager {

	private List<BoardGame> boardGames;
	
	public ListOfGameByUser(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestUser req = (RequestUser)request;
		FindIDUserFacade fiuf = new FindIDUserFacade(req);
		int ret = fiuf.getId();
		boardGames = new ArrayList<BoardGame>();
		try {
			String query = SingletonQuery.getInstance().getQuery("LEAVE", req.isCheckGame()? 2:0);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, ret);
			rset = pstmt.executeQuery();
			BoardGame bg;
			if(rset!=null)
				while(rset.next()){
					int gameId = rset.getInt("ID_GAME");
					String name = rset.getString("NAME");
					int status = rset.getInt("STATUS");
					byte[] thumbnail = null;
//					if(req.isCheckGame()){
						Blob image = rset.getBlob("thumbnail");
						if(image!=null){
							thumbnail = image.getBytes(1, (int)image.length());
							image.free();
						}
//					}
					bg = new BoardGame(gameId, name, thumbnail);
					bg.setStatusGame(status);
					if(req.isCheckGame()){
						String dimostrator = rset.getString("USERNAME");
						bg.setDimostrator(dimostrator);
					}
					boardGames.add(bg);
				}
		}catch (SQLException e){
			e.printStackTrace();
		}
			
	}

	public List<BoardGame> getGames(){
		
		return boardGames;
	}
}
