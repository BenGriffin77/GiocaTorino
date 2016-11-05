package it.gioca.torino.manager.db.facade.game.remove;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.RequestLeaveWithSameGames;

public class LeaveWithSomeGames extends ConnectionManager {

	public LeaveWithSomeGames(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestLeaveWithSameGames req = (RequestLeaveWithSameGames)request;
		
		String query = SingletonQuery.getInstance().getQuery("LEAVEWITHOUT", 0);
		try{
			for(int idGame: req.getIds()){
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, req.getOwnerId());
				pstmt.setInt(2, idGame);
				pstmt.execute();
				if(rset!=null)
					while(rset.next()){
						int status = rset.getInt("STATUS");
						if(status!=0){
							exit = false;
							return;
						}
					}
			}
			
			query = SingletonQuery.getInstance().getQuery("LEAVEWITHOUT", 1);
			for(int idGame: req.getIds()){
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, req.getOwnerId());
				pstmt.setInt(2, idGame);
				pstmt.executeUpdate();
			}
			
			query  = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 6);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, 2);
			pstmt.setInt(2, req.getOwnerId());
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
