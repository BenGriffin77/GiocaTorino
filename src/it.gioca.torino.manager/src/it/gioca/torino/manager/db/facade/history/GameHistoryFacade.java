package it.gioca.torino.manager.db.facade.history;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;

public class GameHistoryFacade extends ConnectionManager {

	public GameHistoryFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		int demonstrator = req.getDemostratorId();
		int gameId = req.getIdGame();
		
		String query = SingletonQuery.getInstance().getQuery("HISTORY", 0);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gameId);
			pstmt.setInt(2, demonstrator);
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
					int count = rset.getInt("COUNT");
					query = SingletonQuery.getInstance().getQuery("HISTORY", count==0? 1:2);
					pstmt = conn.prepareStatement(query);
					if(count==0){
						pstmt.setInt(1, gameId);
						pstmt.setInt(2, demonstrator);
					}
					else{
						pstmt.setInt(1, count++);
						pstmt.setInt(2, gameId);
						pstmt.setInt(3, demonstrator);
					}
					pstmt.execute();
					exit = true;
				}
		}catch(SQLException e){
			e.printStackTrace();
			exit = false;
		}
	}

}
