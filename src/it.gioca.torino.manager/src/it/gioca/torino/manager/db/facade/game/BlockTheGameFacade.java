package it.gioca.torino.manager.db.facade.game;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.db.facade.history.GameHistoryFacade;

import java.sql.SQLException;

public class BlockTheGameFacade extends ConnectionManager {

	public BlockTheGameFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 4);
		int status=0;
		int idExit = 0;
		switch(req.getStatus()){
		case FREE: status=0; break;
		case BLOCK: status=1; break;
		case OUT: status=2; idExit=req.getIdExit(); break;
		}
		try{
			run(req, query, status, idExit);
			exit=true;
			if(req.isWithExpansion()){
				query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 5);
				run(req, query, status, idExit);
				exit=true;
				
				if(status==2){
					GameHistoryFacade ghf = new GameHistoryFacade(req);
					exit = ghf.isCorrectExit();
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
			exit=false;
		}
	}

	private void run(GameRequest req, String query, int status, int id_exit) throws SQLException{
		
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, status);
		pstmt.setInt(2, id_exit);
		pstmt.setInt(3, req.getDemostratorId());
		pstmt.setInt(4, req.getIdGame());
		pstmt.setInt(5, req.getOwnerId());
		pstmt.execute();
	}
	
}
