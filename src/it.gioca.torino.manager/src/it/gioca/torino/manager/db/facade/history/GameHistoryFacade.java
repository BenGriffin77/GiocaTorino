package it.gioca.torino.manager.db.facade.history;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameHistoryFacade extends ConnectionManager {

//	private int idHistory;
	
	public GameHistoryFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		int demonstrator = req.getDemostratorId();
		int gameId = req.getIdGame();
		
		if(!Config.ALTERNATIVE_HISTORY){
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
		else{
			String query = SingletonQuery.getInstance().getQuery("HISTORY",4);
			try{
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, gameId);
				pstmt.setInt(2, demonstrator);
				SimpleDateFormat spData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = spData.format(new Date());
				pstmt.setString(3, currentTime);
				pstmt.execute();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

//	public int getIdHistory(){
//		return this.idHistory;
//	}
}
