package it.gioca.torino.manager.db.facade.toylibrary;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.BoardGameRequest;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.TinyGame;

import java.sql.SQLException;

public class AddBoardGamesToLibraryFacade extends ConnectionManager {

	public AddBoardGamesToLibraryFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		BoardGameRequest req = (BoardGameRequest)request;
		String name = req.getUserName();
		
		RequestUser rUser = new RequestUser();
		rUser.setUserName(name);
		FindIDUserFacade fiuf = new FindIDUserFacade(rUser);
		int ret = fiuf.getId();
		
		try{
			for(BoardGame bg: req.getBoardgames()){
				insert(bg, ret);
				//delete(bg, ret);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insert(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS",0);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, bg.getGameId());
		pstmt.setInt(2, userId);
		pstmt.setInt(3, 0);
		pstmt.setString(4, bg.getLanguage());
		pstmt.setInt(5, 0);
		pstmt.execute();
		exit=true;
		addExpansions(bg, userId);
	}
	
	private void addExpansions(BoardGame bg, int userId)throws SQLException{
		
		if(bg.getExpansions()!=null){
			String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS",0);
			for(TinyGame tg: bg.getExpansions()){
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, tg.getGameId());
				pstmt.setInt(2, userId);
				pstmt.setInt(3, 0);
				pstmt.setString(4, bg.getLanguage());
				pstmt.setInt(5, bg.getGameId());
				pstmt.execute();
			}
		}
	}

	private void delete(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",5);
		pstmt = conn.prepareStatement(query);
//		pstmt.setInt(1, bg.getGameId());
		pstmt.setInt(1, userId);
		pstmt.execute();
//		cleanOld(bg, userId);
	}

//	private void cleanOld(BoardGame bg, int userId) throws SQLException{
//		
//		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",2);
//		pstmt = conn.prepareStatement(query);
//		pstmt.setInt(1, bg.getGameId());
//		pstmt.setInt(2, userId);
//		pstmt.execute();
//	}
}
