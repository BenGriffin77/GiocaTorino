package it.gioca.torino.manager.db.facade.game;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.BoardGameRequest;
import it.gioca.torino.manager.db.facade.users.AddUserFacade;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.TinyGame;

public class AddABoardGameListFacade extends ConnectionManager {

	public AddABoardGameListFacade(IRequestDTO request) {
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
		if(ret==0){
			AddUserFacade auf = new AddUserFacade(rUser);
			ret = auf.getId();
		}
		try{
			for(BoardGame bg: req.getBoardgames()){
				switch(bg.getStatus()){
				case INSERT: insert(bg, ret); break;
				case DELETE: delete(bg, ret); cleanOld(bg, ret);break;
				case UPDATE: update(bg, ret); cleanOld(bg, ret); addExpansions(bg, ret); break;
				case NO_ACTION: continue;
				}
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
			exit = false;
		}
	}
	
	private void update(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",4);
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, bg.getLanguage());
		pstmt.setInt(2, bg.getGameId());
		pstmt.setInt(3, userId);
		pstmt.execute();
	}

	private void insert(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",0);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, bg.getGameId());
		pstmt.setInt(3, 1);
		pstmt.setString(4, bg.getLanguage()==null?"ITALIANO":bg.getLanguage());
		pstmt.setInt(5, 0);
		pstmt.execute();
		exit=true;
		addExpansions(bg, userId);
	}
	
	private void addExpansions(BoardGame bg, int userId)throws SQLException{
		
		if(bg.getExpansions()!=null){
			String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",0);
			for(TinyGame tg: bg.getExpansions()){
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, tg.getGameId());
				pstmt.setInt(3, 1);
				pstmt.setString(4, bg.getLanguage());
				pstmt.setInt(5, bg.getGameId());
				pstmt.execute();
			}
		}
	}
	
	private void delete(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",3);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, bg.getGameId());
		pstmt.setInt(2, userId);
		pstmt.execute();
	}

	private void cleanOld(BoardGame bg, int userId) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("PREPARED_GAMES",2);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, bg.getGameId());
		pstmt.setInt(2, userId);
		pstmt.execute();
	}
	
}
