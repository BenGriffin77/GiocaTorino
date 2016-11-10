package it.gioca.torino.manager.db.facade.history;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameFreeUpdateHistoryFacade extends ConnectionManager {

	public GameFreeUpdateHistoryFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		GameRequest req = (GameRequest)request;
		int idHistory =0;
		if(Config.ALTERNATIVE_HISTORY){
			String query = SingletonQuery.getInstance().getQuery("HISTORY",6);
			try{
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, req.getIdGame());
				pstmt.setString(2, req.getDimonstratorName());
				rset = pstmt.executeQuery();
				if(rset!=null)
					while(rset.next()){
						idHistory = rset.getInt("ID");
					}
			}catch(SQLException e){
				e.printStackTrace();
			}
			if(idHistory==0){
				exit=false;
				return;
			}
			query = SingletonQuery.getInstance().getQuery("HISTORY",5);
			try{
				pstmt = conn.prepareStatement(query);
				SimpleDateFormat spData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = spData.format(new Date()); 
				pstmt.setString(1, currentTime);
				pstmt.setInt(2, idHistory);
				pstmt.executeUpdate();
				exit = true;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

}
