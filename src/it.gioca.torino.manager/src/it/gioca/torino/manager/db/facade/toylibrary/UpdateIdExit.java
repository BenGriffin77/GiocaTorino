package it.gioca.torino.manager.db.facade.toylibrary;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;

public class UpdateIdExit extends ConnectionManager {

	public UpdateIdExit(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 7);
		try{
			run(req.getIdGame(), req.getOwnerId(), query, req.getIdExit());
			exit=true;
			if(req.isWithExpansion()){
				query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 5);
				run(req.getIdGame(), req.getOwnerId(), query, req.getIdExit());
				exit=true;
			}
		}catch (SQLException e){
			e.printStackTrace();
			exit=false;
		}
	}
	
	private void run(int idGame, int owner, String query, int idExit) throws SQLException{
		
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, idExit);
		pstmt.setInt(2, idGame);
		pstmt.setInt(3, owner);
		pstmt.execute();
	}

}
